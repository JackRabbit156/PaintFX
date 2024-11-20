package de.bundeswehr.auf.paintfx.gui;

import com.pixelduke.control.Ribbon;
import com.pixelduke.control.ribbon.Column;
import com.pixelduke.control.ribbon.QuickAccessBar;
import com.pixelduke.control.ribbon.RibbonGroup;
import com.pixelduke.control.ribbon.RibbonTab;
import de.bundeswehr.auf.paintfx.gui.components.RibbonMenu;
import de.bundeswehr.auf.paintfx.gui.components.TooltipButton;
import de.bundeswehr.auf.paintfx.gui.components.TooltipMenuButton;
import de.bundeswehr.auf.paintfx.handler.Language;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Component
public class RibbonBar extends Ribbon {

    @Resource
    private String clipboard16;
    @Resource
    private String clipboard48;
    @Resource
    private String copy16;
    @Resource
    private String cut16;
    @Resource
    private Language language;
    @Resource
    private PaintingArea paintArea;
    @Resource
    private String save16;
    @Resource
    private String redo16;
    @Resource
    private String rotate16;
    private RibbonTab start;
    @Resource
    private String undo16;

    public void selectStartTab() {
        setSelectedRibbonTab(start);
    }

    @PostConstruct
    private void initialize() {
        setSelectedRibbonTab(null); // failsafe
        createQuickAccessBar();
        createFileMenu();
        createStartTab();
        createViewTab();
    }

    private void createQuickAccessBar() {
        QuickAccessBar quickAccessBar = new QuickAccessBar();
        setQuickAccessBar(quickAccessBar);

        Button save = new TooltipButton(language.get("action.save"), new ImageView(save16));
        Button undo = new TooltipButton(language.get("action.undo"), new ImageView(undo16));
        Button redo = new TooltipButton(language.get("action.redo"), new ImageView(redo16));
        quickAccessBar.getButtons().addAll(save, undo, redo);
    }

    private void createViewTab() {
        RibbonTab view = new RibbonTab(language.get("menu.tab.view"));
        getTabs().add(view);

    }

    private void createFileMenu() {
        RibbonMenu file = new RibbonMenu(language.get("menu.tab.file"));
        getTabs().add(file);

    }

    private void createStartTab() {
        start = new RibbonTab(language.get("menu.tab.start"));
        getTabs().add(start);
        createClipboardGroup(start);
        createImageGroup(start);

    }

    private void createClipboardGroup(RibbonTab start) {
        RibbonGroup clipboard = new RibbonGroup();
        clipboard.setTitle(language.get("menu.group.clipboard"));
        start.getRibbonGroups().add(clipboard);

        MenuButton paste = TooltipMenuButton.big(language.get("menu.clipboard.paste"), new ImageView(clipboard48));
        MenuItem pasteAction = new MenuItem(language.get("menu.clipboard.paste"), new ImageView(clipboard16));
        paste.getItems().addAll(pasteAction);

        Button cut = TooltipButton.small(language.get("menu.clipboard.cut"), new ImageView(cut16));
        Button copy = TooltipButton.small(language.get("menu.clipboard.copy"), new ImageView(copy16));
        Column column = new Column();
        column.getChildren().addAll(cut, copy);

        clipboard.getNodes().addAll(paste, column);
    }

    private void createImageGroup(RibbonTab start) {
        RibbonGroup image = new RibbonGroup();
        image.setTitle(language.get("menu.group.image"));
        start.getRibbonGroups().add(image);

        MenuButton select = TooltipMenuButton.big(language.get("menu.image.select"), new ImageView(clipboard48));

        Button crop = TooltipButton.small(language.get("menu.image.crop"), new ImageView(cut16));
        Button changeSize = TooltipButton.small(language.get("menu.image.change_size"), new ImageView(copy16));
        MenuButton rotate = TooltipMenuButton.small(language.get("menu.image.rotate"), new ImageView(rotate16));
        rotate.getItems().addAll(new MenuItem("Test"));

        Column column = new Column();
        column.getChildren().addAll(crop, changeSize, rotate);

        image.getNodes().addAll(select, column);
        
    }

}
