package de.bundeswehr.auf.paintfx.gui;

import com.pixelduke.control.Ribbon;
import com.pixelduke.control.ribbon.Column;
import com.pixelduke.control.ribbon.QuickAccessBar;
import com.pixelduke.control.ribbon.RibbonGroup;
import com.pixelduke.control.ribbon.RibbonTab;
import de.bundeswehr.auf.paintfx.gui.components.CaptionMenuItem;
import de.bundeswehr.auf.paintfx.gui.components.RibbonMenu;
import de.bundeswehr.auf.paintfx.gui.components.TooltipButton;
import de.bundeswehr.auf.paintfx.gui.components.TooltipMenuButton;
import de.bundeswehr.auf.paintfx.handler.Language;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Component
public class RibbonBar {

    @Getter
    private final Ribbon component = new Ribbon();
    @Resource
    private String clipboard16;
    @Resource
    private String clipboard48;
    @Resource
    private String crop16;
    @Resource
    private String copy16;
    @Resource
    private String cut16;
    @Resource
    private Language language;
    @Resource
    private PaintingArea paintArea;
    @Resource
    private String resize16;
    @Resource
    private String save16;
    @Resource
    private String scale16;
    @Resource
    private String select48;
    @Resource
    private String redo16;
    @Resource
    private String rotate16;
    private RibbonTab start;
    @Resource
    private String undo16;

    public void selectStartTab() {
        component.setSelectedRibbonTab(start);
    }

    @PostConstruct
    private void initialize() {
        component.setSelectedRibbonTab(null); // failsafe
        createQuickAccessBar();
        createFileMenu();
        createStartTab();
        createViewTab();
    }

    private void createQuickAccessBar() {
        QuickAccessBar quickAccessBar = new QuickAccessBar();
        component.setQuickAccessBar(quickAccessBar);

        Button save = new TooltipButton(language.get("action.save"), new ImageView(save16));
        Button undo = new TooltipButton(language.get("action.undo"), new ImageView(undo16));
        Button redo = new TooltipButton(language.get("action.redo"), new ImageView(redo16));
        quickAccessBar.getButtons().addAll(save, undo, redo);
    }

    private void createViewTab() {
        RibbonTab view = new RibbonTab(language.get("menu.view"));
        component.getTabs().add(view);

    }

    private void createFileMenu() {
        RibbonMenu file = new RibbonMenu(language.get("menu.file"));
        component.getTabs().add(file);

    }

    private void createStartTab() {
        start = new RibbonTab(language.get("menu.start"));
        component.getTabs().add(start);
        createClipboardGroup(start);
        createImageGroup(start);

    }

    private void createClipboardGroup(RibbonTab start) {
        RibbonGroup clipboard = new RibbonGroup();
        clipboard.setTitle(language.get("action.clipboard"));
        start.getRibbonGroups().add(clipboard);

        MenuButton paste = TooltipMenuButton.big(language.get("action.paste"), new ImageView(clipboard48));
        MenuItem pasteAction = new MenuItem(language.get("action.paste"), new ImageView(clipboard16));
        paste.getItems().addAll(pasteAction);

        Button cut = TooltipButton.small(language.get("action.cut"), new ImageView(cut16));
        Button copy = TooltipButton.small(language.get("action.copy"), new ImageView(copy16));
        Column column = new Column();
        column.getChildren().addAll(cut, copy);

        clipboard.getNodes().addAll(paste, column);
    }

    private void createImageGroup(RibbonTab start) {
        RibbonGroup image = new RibbonGroup();
        image.setTitle(language.get("menu.image"));
        start.getRibbonGroups().add(image);

        MenuButton select = TooltipMenuButton.big(language.get("action.select"), new ImageView(select48));
        MenuItem selectForm = new CaptionMenuItem(language.get("menu.caption.selectForm"));
        RadioMenuItem formRectangle = new RadioMenuItem(language.get("menu.radio.form_rectangle"));
        RadioMenuItem formFree = new RadioMenuItem(language.get("menu.radio.free_form"));
        MenuItem selectOptions = new CaptionMenuItem(language.get("menu.caption.selectOptions"));
        MenuItem selectAll = new MenuItem(language.get("action.select_all"));
        MenuItem invertSelection = new MenuItem(language.get("action.invert_selection"));
        MenuItem removeSelection = new MenuItem(language.get("action.remove_selection"));
        select.getItems().addAll(selectForm, formRectangle, formFree, selectOptions, selectAll, invertSelection, removeSelection);

        Button crop = TooltipButton.small(language.get("action.crop"), new ImageView(crop16));
        Button changeSize = TooltipButton.small(language.get("action.resize"), new ImageView(resize16));
        Button scale = TooltipButton.small(language.get("action.scale"), new ImageView(scale16));
        Column column1 = new Column();
        column1.getChildren().addAll(crop, changeSize, scale);

        MenuButton rotate = TooltipMenuButton.small(language.get("action.rotate"), new ImageView(rotate16));
        MenuItem rotateTest = new MenuItem("Test");
        rotate.getItems().addAll(rotateTest);
        Column column2 = new Column();
        column2.getChildren().addAll(rotate);

        image.getNodes().addAll(select, column1, column2);

    }

}
