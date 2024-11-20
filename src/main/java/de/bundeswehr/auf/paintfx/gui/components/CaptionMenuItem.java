package de.bundeswehr.auf.paintfx.gui.components;

import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;

/**
 * Known bugs:
 * <li>gets selected background, when separator next to this and mouse is over separator</li>
 * <li>gets selected background, when first item in {@link javafx.scene.control.MenuButton} and mouse moves back to the {@link javafx.scene.control.MenuButton}</li>
 */
public class CaptionMenuItem extends CustomMenuItem {

    private static final String DEFAULT_STYLE_CLASS = "caption-menu-item";

    public CaptionMenuItem(String title) {
        super(new Label(title), false);
        getStyleClass().add(DEFAULT_STYLE_CLASS);
    }

}
