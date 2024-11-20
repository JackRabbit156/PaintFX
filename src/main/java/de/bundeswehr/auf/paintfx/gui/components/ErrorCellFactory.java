package de.bundeswehr.auf.paintfx.gui.components;

import javafx.scene.Cursor;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ErrorCellFactory implements Callback<ListView<Throwable>, ListCell<Throwable>> {

    @Override
    public ListCell<Throwable> call(ListView<Throwable> param) {
        return new ListCell<Throwable>() {

            @Override
            protected void updateItem(Throwable e, boolean empty) {
                super.updateItem(e, empty);
                if (empty || e == null) {
                    setText(null);
                    setCursor(Cursor.DEFAULT);
                }
                else {
                    setText(e.getClass().getSimpleName() + " (" + e.getStackTrace()[0].getFileName() + ")");
                    setCursor(Cursor.HAND);
                }
            }

        };
    }

}
