package de.bundeswehr.auf.paintfx.handler;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Component
public class ErrorHandlingExecutorService extends ScheduledThreadPoolExecutor {

    private static int counter;

    @Resource
    private ErrorHandler errorHandler;

    ErrorHandlingExecutorService() {
        super(8);
        setThreadFactory(r -> {
            final Thread thread = new Thread(r, "pool-thread-" + counter++);
            thread.setUncaughtExceptionHandler(errorHandler);
            return thread;
        });
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && r instanceof Future) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException e) {
                t = e;
            } catch (ExecutionException e) {
                t = e.getCause();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (t != null) {
            errorHandler.uncaughtException(Thread.currentThread(), t);
        }
    }

}
