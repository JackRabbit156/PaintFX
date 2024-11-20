package de.bundeswehr.auf.paintfx.handler;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Component
public class Language {

    private final Properties properties = new Properties();

    @PostConstruct
    private void initialize() throws IOException {
        load("de");
    }

    public void load(String language) throws IOException {
        String name = "/lang/" + language +  ".lang";
        try (InputStreamReader input = new InputStreamReader(new FileInputStream(getClass().getResource(name).getFile()), StandardCharsets.UTF_8)) {
            properties.clear();
            properties.load(input);
        }
    }

    public String get(String key) {
        String property = properties.getProperty(key);
        if (property == null || property.isEmpty()) {
            return key;
        }
        return property;
    }

    /**
     * {@code {}} in {@code key} will be consecutively replaced by {@code args}.
     *
     * @param key
     * @param args
     * @return
     */
    public String get(String key, String...args) {
        String property = get(key);
        for (String arg : args) {
            property = property.replaceFirst("\\{\\}", arg);
        }
        return property;
    }

}
