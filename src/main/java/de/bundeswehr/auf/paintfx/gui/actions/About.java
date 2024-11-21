package de.bundeswehr.auf.paintfx.gui.actions;

import de.bundeswehr.auf.paintfx.Setup;
import de.bundeswehr.auf.paintfx.handler.Language;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class About implements EventHandler<ActionEvent> {

    private Dialog<ButtonType> dialog;
    @Resource
    private Language language;
    @Resource
    private Image paint100;
    @Resource
    private Setup setup;

    @PostConstruct
    private void initialize() {
        dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle(language.get("info.title"));
        dialog.setGraphic(new ImageView(paint100));
        dialog.setHeaderText(language.get("info.header", setup.getVersion()));
        dialog.setContentText(language.get("info.content"));
    }

    @Override
    public void handle(ActionEvent event) {
        if (dialog.getOwner() == null) {
            dialog.initOwner(setup.getStage());
        }
        dialog.show();
    }

}
