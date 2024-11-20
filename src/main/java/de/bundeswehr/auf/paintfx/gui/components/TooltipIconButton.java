package de.bundeswehr.auf.paintfx.gui.components;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;

public class TooltipIconButton extends Button {

    public static TooltipIconButton big(String text, Node graphic) {
        TooltipIconButton button = new TooltipIconButton(text, graphic);
//        button.setContentDisplay(ContentDisplay.TOP);
        return button;
    }

    public static TooltipIconButton small(String text, Node graphic) {
        TooltipIconButton button = new TooltipIconButton(text, graphic);
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    public TooltipIconButton(String text, Node graphic) {
        super("", graphic);
        setTooltip(new Tooltip(text));
    }

}
