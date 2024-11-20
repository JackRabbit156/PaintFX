package de.bundeswehr.auf.paintfx.handler;

import de.bundeswehr.auf.paintfx.Setup;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

@Component
public class Title {

    @Resource
    private Language language;
    @Resource
    private Setup setup;
    private final StringProperty titleProperty = new SimpleStringProperty();


    public StringProperty titleProperty() {
        return titleProperty;
    }

    public void update() {
        Platform.runLater(() -> {
            titleProperty.set(get());
        });
    }

    private String currentFilename() {
        File file = setup.getCurrentFile();
        if (file != null) {
            return file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(File.separator) + 1);
        }
        return language.get("unknown");
    }

    private String get() {
        return currentFilename() + " - PaintFX v" + setup.getVersion();
    }

}
