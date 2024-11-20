package de.bundeswehr.auf.paintfx.handler;

import de.bundeswehr.auf.paintfx.Setup;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class ExportImage {

    @Resource
    private Setup setup;

    public File exportToFile(Image image, String format, File f) {
        try {
            WritableImage writableImage = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
            BufferedImage imageRGB = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.OPAQUE);
            Graphics2D graphics = imageRGB.createGraphics();
            graphics.drawImage(bufferedImage, 0, 0, null);
            ImageIO.write(imageRGB, format, f);
            return f;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File exportToFile(Image image) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"), new FileChooser.ExtensionFilter("JPG", "*.jpg", "*.jpeg", "*.jpe", "*.jfif")
        );
        File file = fc.showSaveDialog(setup.getStage());
        if (file != null) {
            return exportToFile(image, fc.getSelectedExtensionFilter().getDescription(), file);
        }
        return null;
    }

    public Object[] importFromFile() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg", "*.jpe", "*.jfif"), new FileChooser.ExtensionFilter("All", "*.*"));
        File file = fc.showOpenDialog(setup.getStage());
        if (file != null) {
            BufferedImage bufferedImage;
            Image image;
            try {
                bufferedImage = ImageIO.read(file);
                image = SwingFXUtils.toFXImage(bufferedImage, null);
            } catch (Exception e) {
                return null;
            }
            return new Object[]{image, file};
        }
        return null;
    }

}
