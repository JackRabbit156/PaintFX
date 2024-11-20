package de.bundeswehr.auf.paintfx.gui;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GuiProperties {

    @Bean
    private Color background() {
        return Color.rgb(209, 219, 233);
    }

    @Bean
    private Border debugBorder() {
        return new Border(new BorderStroke(Color.MAGENTA, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    }

    @Bean
    private Color shadow() {
        return Color.rgb(190, 190, 190);
    }


}
