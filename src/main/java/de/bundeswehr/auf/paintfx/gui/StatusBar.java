package de.bundeswehr.auf.paintfx.gui;

import de.bundeswehr.auf.paintfx.gui.components.ErrorAlert;
import de.bundeswehr.auf.paintfx.handler.Language;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class StatusBar {

    private Label alert;
    @Resource
    private String alert16;
    @Resource
    private String alerted16;
    private final StringProperty canvasSize = new SimpleStringProperty();
    @Getter
    private final org.controlsfx.control.StatusBar component = new org.controlsfx.control.StatusBar();
    @Resource
    private ErrorAlert errorAlert;
    @Resource
    private Language language;
    private final StringProperty mousePosition = new SimpleStringProperty();
    @Resource
    private String move16;
    @Resource
    private String size16;
    private final DoubleProperty zoom = new SimpleDoubleProperty();

    public void bindCanvas(Canvas canvas) {
        if (canvasSize.isBound()) {
            canvasSize.unbind();
        }
        canvasSize.bind(Bindings.createStringBinding(() -> (int) canvas.getWidth() + " x " + (int) canvas.getHeight() + "px", canvas.widthProperty(), canvas.heightProperty()));
        canvas.setOnMouseEntered(this::setMousePosition);
        canvas.setOnMouseMoved(this::setMousePosition);
        canvas.setOnMouseExited(event -> mousePosition.set(""));
    }

    public void removeAlert() {
        alert.setGraphic(null);
        alert.setTooltip(null);
    }

    public void setAlerted() {
        alert.setGraphic(new ImageView(alerted16));
        alert.setTooltip(new Tooltip(language.get("tooltip.new-errors")));
    }

    public void setUnAlerted() {
        alert.setGraphic(new ImageView(alert16));
        alert.setTooltip(new Tooltip(language.get("tooltip.no-new-errors")));
    }

    private void setMousePosition(MouseEvent event) {
        mousePosition.set((int) event.getX() + ", " + (int) event.getY() + "px");
    }

    @PostConstruct
    private void initialize() {
        // left
        Label mousePosition = createLabel(this.mousePosition, move16);
        Label canvasSize = createLabel(this.canvasSize, size16);
        component.getLeftItems().addAll(mousePosition, separator(), canvasSize, separator());
        // label
        component.setText("");
        // progress
        component.setProgress(0.0);
        // right
        alert = createIconLabel("");
        alert.setOnMouseClicked(event -> errorAlert.toggleShowing());
        alert.setCursor(Cursor.HAND);

        Label zoom = new Label();
        zoom.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        zoom.textProperty().bind(Bindings.createStringBinding(() -> String.format("%.0f%%", this.zoom.get() * 100), this.zoom));
        zoom.styleProperty().bind(component.styleProperty());
        zoom.getStyleClass().add("status-label");

        component.getRightItems().addAll(separator(), alert, separator(), zoom);
    }

    private Label createIconLabel(String image) {
        Label label = new Label();
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        if (!image.isEmpty()) {
            label.setGraphic(new ImageView(image));
        }
        label.styleProperty().bind(component.styleProperty());
        label.getStyleClass().add("status-label");
        return label;
    }

    private Label createLabel(StringProperty text, String image) {
        Label label = new Label();
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        label.setMinWidth(150);
        label.textProperty().bind(text);
        if (!image.isEmpty()) {
            label.setGraphic(new ImageView(image));
        }
        label.styleProperty().bind(component.styleProperty());
        label.getStyleClass().add("status-label");
        return label;
    }

    private Node separator() {
        return new Separator(Orientation.VERTICAL);
    }

    public void bindZoom(DoubleProperty scaleProperty) {
        if (zoom.isBound()) {
            zoom.unbind();
        }
        zoom.bind(scaleProperty);
    }
}
