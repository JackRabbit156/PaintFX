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
import javafx.stage.Stage;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class ErrorAlert extends Dialog<ButtonType> {

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
        if (isShowing()) {
            close();
        }
        else {
            if (getOwner() == null) {
                initOwner(setup.getStage());
            }
            show();
        }
    }

    @PostConstruct
    private void initialize() {
        setOnShowing(event -> {
            statusBar.setUnAlerted();
        });
        setTitle(language.get("errors"));
        initModality(Modality.NONE);

        TextArea stackTrace = new TextArea();
        ListView<Throwable> throwables = new ListView<>(this.throwables);
        throwables.setCellFactory(new ErrorCellFactory());
        throwables.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showStackTrace(newValue, stackTrace));

        BorderPane root = new BorderPane(stackTrace);
        root.setLeft(throwables);
        getDialogPane().setContent(root);

        ButtonType clearAll = new ButtonType(language.get("button.clear-all"), ButtonBar.ButtonData.FINISH);
        getDialogPane().getButtonTypes().addAll(clearAll, ButtonType.CLOSE);
        Button clearAllButton = (Button) getDialogPane().lookupButton(clearAll);
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
