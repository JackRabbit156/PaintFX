package de.bundeswehr.auf.paintfx.handler;

import de.bundeswehr.auf.paintfx.Setup;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

@Component
public class FileLoader {

    @Resource
    private Setup setup;

    public void load(String filename) {
        // TODO
        setup.setCurrentFile(new File(filename));
    }

}
