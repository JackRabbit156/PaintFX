package de.bundeswehr.auf.paintfx.gui;

import javafx.scene.image.Image;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Icons {

    @Bean
    private String alert16() {
        return createIcon("alert", 16);
    }

    @Bean
    private String alerted16() {
        return createIcon("alerted", 16);
    }

    @Bean
    private String brush48() {
        return createIcon("brush", 48);
    }

    @Bean
    private String clipboard16() {
        return createIcon("clipboard", 16);
    }

    @Bean
    private String clipboard48() {
        return createIcon("clipboard", 48);
    }

    @Bean
    private String crop16() {
        return createIcon("crop", 16);
    }

    @Bean
    private String cut16() {
        return createIcon("cut", 16);
    }

    @Bean
    private String copy16() {
        return createIcon("copy", 16);
    }

    @Bean
    private String eraser16() {
        return createIcon("eraser", 16);
    }

    @Bean
    private String filler16() {
        return createIcon("filler", 16);
    }

    @Bean
    private String move16() {
        return createIcon("move", 16);
    }

    @Bean
    private String pencil16() {
        return createIcon("pencil", 16);
    }

    @Bean
    private String pipette16() {
        return createIcon("pipette", 16);
    }

    @Bean
    private String redo16() {
        return createIcon("redo", 16);
    }

    @Bean
    private String resize16() {
        return createIcon("resize", 16);
    }

    @Bean
    private String rotate16() {
        return createIcon("rotate", 16);
    }

    @Bean
    private Image paint100() {
        return createImage("paint", 100);
    }

    @Bean
    private String save16() {
        return createIcon("save", 16);
    }

    @Bean
    private String scale16() {
        return createIcon("scale", 16);
    }

    @Bean
    private String select48() {
        return createIcon("select", 48);
    }

    @Bean
    private String size16() {
        return createIcon("size", 16);
    }

    @Bean
    private String text16() {
        return createIcon("text", 16);
    }

    @Bean
    private String undo16() {
        return createIcon("undo", 16);
    }

    @Bean
    private String zoom16() {
        return createIcon("zoom", 16);
    }

    private String createIcon(String name) {
        return "/icons/icons8-" + name + "-100.png";
    }

    private String createIcon(String name, int size) {
        return "/icons/icons8-" + name + "-" + size + ".png";
    }

    private Image createImage(String name, int size) {
        return new Image(createIcon(name), size, size, true, true);
    }

}
