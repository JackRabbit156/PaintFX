package de.bundeswehr.auf.paintfx.gui;

import de.bundeswehr.auf.paintfx.gui.components.ZoomableScrollPane;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Component
public class PaintingArea extends ZoomableScrollPane {

    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 500;
    private static final double HORIZONTAL_SCROLL_SPEED = 0.01;
    private static final double VERTICAL_SCROLL_SPEED = 0.01;

    private final StackPane backgroundPane = new StackPane();
    @Resource
    private Color background;
    private GraphicsContext ctx;
    @Resource
    private Border debugBorder;
    @Resource
    private Color shadow;
    @Resource
    private StatusBar statusBar;

    @PostConstruct
    private void initialize() {
        setBackground(new Background(new BackgroundFill(background, CornerRadii.EMPTY, Insets.EMPTY)));

        statusBar.bindZoom(scaleProperty());

        resetCanvas();
        backgroundPane.setBackground(new Background(new BackgroundImage(createPattern(), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        backgroundPane.setEffect(new DropShadow(7, 5, 5, shadow));
        FlowPane root = new FlowPane(backgroundPane);
        root.setPadding(new Insets(5.));
        root.setOnScroll(event -> {
            if (event.isAltDown()) {
                event.consume();
                double deltaY = event.getDeltaY() * HORIZONTAL_SCROLL_SPEED;
                setHvalue(getHvalue() - deltaY);
            }
            else if (!event.isControlDown()) {
                event.consume();
                double deltaY = event.getDeltaY() * VERTICAL_SCROLL_SPEED;
                setVvalue(getVvalue() - deltaY);
            }
        });
        setZoomableContent(root);
        // Test
        ctx.setStroke(Color.RED);
        ctx.setLineWidth(3);
        ctx.strokeLine(100, 100, 300, 200);
        ctx.setFill(Color.WHITE);
        ctx.fillRect(0, 300, 500, 100);
        ctx.fillRect(400, 0, 50, 500);
    }

    private void resetCanvas() {
        backgroundPane.getChildren().clear();
        backgroundPane.getChildren().add(createCanvas());
    }

    private WritableImage createPattern() {
        int width = 30;
        int height = 30;
        WritableImage img = new WritableImage(width, height);
        PixelWriter writer = img.getPixelWriter();
        // (alpha << 24) | (r << 16) | (g << 8) | b
        int gray = (255 << 24) | (230 << 16) | (230 << 8) | 230;
        int white = (255 << 24) | (255 << 16) | (255 << 8) | 255;
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        int[] buffer = new int[width * height];
        for (int i = 0, len = buffer.length; i < len; i++) {
            if (i / width < halfWidth) {
                if (i % height < halfHeight) {
                    buffer[i] = gray;
                }
                else {
                    buffer[i] = white;
                }
            }
            else {
                if (i % height < halfHeight) {
                    buffer[i] = white;
                }
                else {
                    buffer[i] = gray;
                }
            }
        }
        writer.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), buffer, 0, width);
        return img;
    }

    private Canvas createCanvas() {
        Canvas canvas = new Canvas();
        statusBar.bindCanvas(canvas);
        canvas.setWidth(DEFAULT_WIDTH);
        canvas.setHeight(DEFAULT_HEIGHT);
        ctx = canvas.getGraphicsContext2D();
        return canvas;
    }

}
