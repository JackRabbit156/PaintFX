package de.bundeswehr.auf.paintfx.gui.components;

import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;

public class CaptionMenuItem extends CustomMenuItem {

    private static final String DEFAULT_STYLE_CLASS = "caption-menu-item";

    public CaptionMenuItem(String title) {
        super(new Label(title), false);
        getStyleClass().add(DEFAULT_STYLE_CLASS);
    }

}
