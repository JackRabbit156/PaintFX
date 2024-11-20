package de.bundeswehr.auf.paintfx.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TooltipMenuButton extends MenuButton {

    private boolean hideArrow;

    private TooltipMenuButton(String text, Node graphic) {
        super(text, graphic);
        setTooltip(new Tooltip(text));
    }

    public static TooltipMenuButton big(String text, Node graphic) {
        TooltipMenuButton button = new TooltipMenuButton(text, graphic);
        button.setPopupSide(Side.BOTTOM);
        button.arrowCentered(text, graphic);
        return button;
    }

    public static TooltipMenuButton small(String text, Node graphic) {
        TooltipMenuButton button = new TooltipMenuButton(text, graphic);
        button.setPopupSide(Side.BOTTOM);
        button.setMaxWidth(Double.MAX_VALUE);
        button.inColumn(text, graphic);
        return button;
    }

    private void inColumn(String text, Node graphic) {
        hideArrow = true;
        StackPane arrow = new StackPane();
        arrow.setStyle("-fx-background-insets: 1 0 -1 0, 0;" +
                "-fx-background-color: -fx-mark-highlight-color, -fx-mark-color;" +
                "-fx-padding: 0.166667em 0.333333em 0.166667em 0.333333em;" +
                "-fx-shape: \"M 0 0 h 7 l -3.5 4 z\"");
        StackPane arrowPane = new StackPane(new Group(arrow));
        arrowPane.setPadding(new Insets(1, 0, -1, 0));
        HBox buttonGraphic = new HBox(new StackPane(graphic), new Label(text), arrowPane);
        buttonGraphic.setAlignment(Pos.CENTER_LEFT);
        buttonGraphic.setSpacing(5.);
        setGraphic(buttonGraphic);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setStyle("-fx-padding: 0.0em !important;");
    }

    private void arrowCentered(String text, Node graphic) {
        hideArrow = true;
        StackPane arrow = new StackPane();
        arrow.setStyle("-fx-background-color: -fx-mark-highlight-color, -fx-mark-color;" +
                "-fx-background-insets: 0 0 -1 0, 0;" +
                "-fx-padding: 2px 4px 2px 4px;" +
                "-fx-shape: \"M 0 0 h 7 l -3.5 4 z\"");
        StackPane arrowPane = new StackPane(new Group(arrow));
        arrowPane.setPadding(new Insets(0, 6, 4, 6));
        VBox buttonGraphic = new VBox(new StackPane(graphic), new Label(text), arrowPane);
        buttonGraphic.setAlignment(Pos.CENTER);
        buttonGraphic.setSpacing(5.);
        setGraphic(buttonGraphic);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setStyle("-fx-padding: 0.0em !important;");
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        if (hideArrow) {
            lookup(".arrow-button").setStyle("-fx-padding:0px;");
            lookup(".arrow").setStyle("-fx-padding:0px;");
        }
    }

}
