package de.bundeswehr.auf.paintfx.gui.actions;

import de.bundeswehr.auf.paintfx.Setup;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Close implements EventHandler<ActionEvent> {

    @Resource
    private Setup setup;

    @Override
    public void handle(ActionEvent event) {
        setup.getStage().getOnCloseRequest().handle(null);
        setup.getStage().close();
    }

}
