package de.bundeswehr.auf.paintfx;

import de.bundeswehr.auf.paintfx.handler.ErrorHandler;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
@Configuration
@ComponentScan(basePackages = "de.bundeswehr.auf.paintfx")
@Getter
public class Setup {

    private static final String VERSION = "1.0-SNAPSHOT";

    private final Properties properties = new Properties();
    @Setter
    private File currentFile;
    @Setter
    private Stage stage;

    public String getVersion() {
        return VERSION;
    }

    public void saveProperties() {
        // TODO
    }

    public void set(String key, Object value) {
        properties.setProperty(key, String.valueOf(value));
    }

    public <T> T get(String key, T defaultValue) {
        return (T) properties.getOrDefault(key, defaultValue);
    }

    @PostConstruct
    private void initialize() {
        // TODO load properties
    }

}
