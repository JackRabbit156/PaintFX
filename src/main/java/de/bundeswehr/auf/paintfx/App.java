package de.bundeswehr.auf.paintfx;

import de.bundeswehr.auf.paintfx.gui.PaintingArea;
import de.bundeswehr.auf.paintfx.gui.RibbonBar;
import de.bundeswehr.auf.paintfx.gui.StatusBar;
import de.bundeswehr.auf.paintfx.handler.ErrorHandler;
import de.bundeswehr.auf.paintfx.handler.FileLoader;
import de.bundeswehr.auf.paintfx.handler.Language;
import de.bundeswehr.auf.paintfx.handler.Title;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

//@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
public class App extends Application {

    private AnnotationConfigApplicationContext context;

    public static void main(String[] args) {
        log.debug("Starting PaintFX");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            context = new AnnotationConfigApplicationContext(Setup.class);
            Setup setup = context.getBean(Setup.class);
            setup.setStage(primaryStage);
            ExecutorService executorService = context.getBean(ExecutorService.class);
            primaryStage.setOnCloseRequest(event -> {
                setup.set("h", primaryStage.getHeight());
                setup.set("w", primaryStage.getWidth());
                setup.set("x", primaryStage.getX());
                setup.set("y", primaryStage.getY());
                setup.saveProperties();
                executorService.shutdown();
                context.close();
            });

            primaryStage.getIcons().add(context.getBean("paint100", Image.class));
            Title title = context.getBean(Title.class);
            primaryStage.titleProperty().bind(title.titleProperty());
            title.update();

            BorderPane root = new BorderPane(context.getBean(PaintingArea.class));
            RibbonBar ribbonBar = context.getBean(RibbonBar.class);
            root.setTop(ribbonBar);
            root.setBottom(context.getBean(StatusBar.class));

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            primaryStage.setScene(scene);

            primaryStage.setX(setup.get("x", 2500.)); // TODO 0
            primaryStage.setY(setup.get("y", 300)); // TODO 0
            primaryStage.setWidth(setup.get("w", 800.)); // TODO 0
            primaryStage.setHeight(setup.get("h", 600.)); // TODO 0
            primaryStage.show();

            ribbonBar.selectStartTab();
            handleArguments(context);
            executorService.execute(() -> {
                // TODO remove
                try {
                    TimeUnit.SECONDS.sleep(2L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new IllegalArgumentException("Test");
            });
        } catch (Throwable e) {
            if (context != null) {
                context.getBean(ErrorHandler.class).handle(e);
            }
            else {
                ErrorHandler.failSafe(e);
            }
        }
    }

    private void handleArguments(ApplicationContext context) {
        List<String> args = getParameters().getRaw();
        if (args.size() > 0 && !args.get(0).isEmpty()) {
            log.debug("Starting with arguments: {}", args);
            StringBuilder sb = new StringBuilder();
            for (String str : args) {
                sb.append(str);
                sb.append(" ");
            }
            sb.setLength(sb.length() - 1);
            context.getBean(FileLoader.class).load(sb.toString());
        }
    }

}
