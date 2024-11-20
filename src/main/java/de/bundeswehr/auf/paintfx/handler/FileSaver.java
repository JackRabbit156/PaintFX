package de.bundeswehr.auf.paintfx.handler;

import de.bundeswehr.auf.paintfx.Setup;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

@Component
public class FileSaver {

    @Resource
    private Setup setup;
    @Resource
    private Title title;

    public void save() {
        saveTo(setup.getCurrentFile());
    }

    public void saveTo(String filename) {
        saveTo(new File(filename));
    }

    private void saveTo(File file) {
        // TODO
        setup.setCurrentFile(file);
        title.update();
    }

}
