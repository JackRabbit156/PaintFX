package de.bundeswehr.auf.paintfx.gui.components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ZoomableScrollPane extends ScrollPane {

    private static final double ZOOM_INTENSITY = 0.1;

    private final DoubleProperty scale = new SimpleDoubleProperty(1.0);
    private Node target;
    private Node zoomNode;

    public double getScale() {
        return scale.get();
    }

    public void setScale(double scale) {
        Scene scene = zoomNode.getScene();
        BoundingBox sceneCenter = new BoundingBox(scene.getWidth() / 2, scene.getHeight() / 2, 0, 0);
        Bounds bounds = zoomNode.localToParent(zoomNode.sceneToLocal(sceneCenter));
        setScale(scale, new Point2D(bounds.getMinX(), bounds.getMinY()));
    }

    public void setZoomableContent(Node target) {
        this.target = target;
        this.zoomNode = new Group(target);
        setContent(outerNode(zoomNode));
        setPannable(true);
        target.scaleXProperty().bind(scale);
        target.scaleYProperty().bind(scale);
    }

    public DoubleProperty scaleProperty() {
        return scale;
    }

    private Node centeredNode(Node node) {
        VBox vBox = new VBox(node);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private void onScroll(double wheelDelta, Point2D mousePoint) {
        double zoomFactor = Math.exp(wheelDelta * ZOOM_INTENSITY);
        setScale(scale.get() * zoomFactor, mousePoint);
    }

    private Node outerNode(Node node) {
        Node outerNode = centeredNode(node);
        outerNode.setOnScroll(event -> {
            if (event.isControlDown()) {
                event.consume();
                onScroll(event.getTextDeltaY(), new Point2D(event.getX(), event.getY()));
            }
        });
        return outerNode;
    }

    private void setScale(double scale, Point2D point) {
        // 1% - 1_000_000%
        if (scale < 0.01 || scale > 10_000) {
            return;
        }
        Bounds innerBounds = zoomNode.getLayoutBounds();
        Bounds viewportBounds = getViewportBounds();
        // calculate pixel offsets from [0, 1] range
        double x = getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
        double y = getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());
        double zoomFactor = scale / getScale();
        this.scale.set(scale);
        // refresh ScrollPane scroll positions & target bounds
        layout();
        // convert mouse coordinates to target coordinates
        Point2D positionInTarget = target.parentToLocal(zoomNode.parentToLocal(point));
        // calculate adjustment of scroll position (pixels)
        Point2D adjustment = target.getLocalToParentTransform().deltaTransform(positionInTarget.multiply(zoomFactor - 1));
        // convert back to [0, 1] range
        // (too large/small values are automatically corrected by ScrollPane)
        Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
        this.setHvalue((x + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
        this.setVvalue((y + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));
    }

}
