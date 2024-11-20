package de.bundeswehr.auf.paintfx.gui.components;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;

public class TooltipButton extends Button {

    public static TooltipButton big(String text, Node graphic) {
        TooltipButton button = new TooltipButton(text, graphic);
        button.setContentDisplay(ContentDisplay.TOP);
        return button;
    }

    public static TooltipButton small(String text, Node graphic) {
        TooltipButton button = new TooltipButton(text, graphic);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        return button;
    }

    public TooltipButton(String text, Node graphic) {
        super(text, graphic);
        setTooltip(new Tooltip(text));
    }

}
