package de.bundeswehr.auf.paintfx.gui.components;

import de.bundeswehr.auf.paintfx.Setup;
import de.bundeswehr.auf.paintfx.gui.StatusBar;
import de.bundeswehr.auf.paintfx.handler.Language;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import lombok.Getter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class ErrorAlert {

    @Getter
    private final Dialog<ButtonType> dialog = new Dialog<>();
    @Resource
    private Language language;
    @Resource
    private Setup setup;
    @Lazy
    @Resource
    private StatusBar statusBar;
    private final ObservableList<Throwable> throwables = FXCollections.observableArrayList();

    public void addError(Throwable e) {
        throwables.add(e);
        Platform.runLater(() -> statusBar.setAlerted());
    }

    public void toggleShowing() {
        if (dialog.isShowing()) {
            dialog.close();
        }
        else {
            if (dialog.getOwner() == null) {
                dialog.initOwner(setup.getStage());
            }
            dialog.show();
        }
    }

    @PostConstruct
    private void initialize() {
        dialog.setOnShowing(event -> statusBar.setUnAlerted());
        dialog.setTitle(language.get("errors"));
        dialog.initModality(Modality.NONE);

        TextArea stackTrace = new TextArea();
        ListView<Throwable> throwables = new ListView<>(this.throwables);
        throwables.setCellFactory(new ErrorCellFactory());
        throwables.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showStackTrace(newValue, stackTrace));

        BorderPane root = new BorderPane(stackTrace);
        root.setLeft(throwables);
        dialog.getDialogPane().setContent(root);

        ButtonType clearAll = new ButtonType(language.get("button.clear-all"), ButtonBar.ButtonData.FINISH);
        dialog.getDialogPane().getButtonTypes().addAll(clearAll, ButtonType.CLOSE);
        Button clearAllButton = (Button) dialog.getDialogPane().lookupButton(clearAll);
        clearAllButton.setDefaultButton(false);
        clearAllButton.setOnAction(event -> {
            stackTrace.clear();
            this.throwables.clear();
            statusBar.removeAlert();
        });
    }

    private void showStackTrace(Throwable e, TextArea stackTrace) {
        stackTrace.clear();
        stackTrace.appendText(e.getClass().getCanonicalName());
        stackTrace.appendText(": ");
        stackTrace.appendText(e.getLocalizedMessage());
        stackTrace.appendText("\n");
        for (StackTraceElement element : e.getStackTrace()) {
            stackTrace.appendText("\tat ");
            stackTrace.appendText(element.getClassName());
            stackTrace.appendText(".");
            stackTrace.appendText(element.getMethodName());
            if (element.getLineNumber() < 1) {
                stackTrace.appendText("(Native Method)");
            }
            else {
                stackTrace.appendText("(");
                stackTrace.appendText(element.getFileName());
                stackTrace.appendText(":");
                stackTrace.appendText(Integer.toString(element.getLineNumber()));
                stackTrace.appendText(")");
            }
            stackTrace.appendText("\n");
        }
    }

}
