package de.bundeswehr.auf.paintfx.handler;

import de.bundeswehr.auf.paintfx.gui.components.ErrorAlert;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@Slf4j
public class ErrorHandler implements Thread.UncaughtExceptionHandler {

    @Resource
    private ErrorAlert errorAlert;

    public static void failSafe(Throwable e) {
        log.error("{} outside of Spring context: {}", e.getClass().getSimpleName(), e.getLocalizedMessage());
        log.debug("Error outside of Spring context", e);
    }

    public void handle(Throwable e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getLocalizedMessage());
        log.debug(e.getLocalizedMessage(), e);
        errorAlert.addError(e);
    }

    @PostConstruct
    private void initialize() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        handle(e);
    }

}
