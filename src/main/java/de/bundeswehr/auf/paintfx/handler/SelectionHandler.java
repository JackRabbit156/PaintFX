package de.bundeswehr.auf.paintfx.handler;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class SelectionHandler {

    @Getter
    private BooleanProperty activeProperty = new SimpleBooleanProperty();

}
