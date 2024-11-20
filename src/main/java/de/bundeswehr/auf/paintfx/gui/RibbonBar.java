package de.bundeswehr.auf.paintfx.gui;

import com.pixelduke.control.Ribbon;
import com.pixelduke.control.ribbon.Column;
import com.pixelduke.control.ribbon.QuickAccessBar;
import com.pixelduke.control.ribbon.RibbonGroup;
import com.pixelduke.control.ribbon.RibbonTab;
import de.bundeswehr.auf.paintfx.gui.components.*;
import de.bundeswehr.auf.paintfx.handler.Language;
import de.bundeswehr.auf.paintfx.handler.SelectionHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Component
public class RibbonBar {

    @Getter
    private final Ribbon component = new Ribbon();
    @Resource
    private ApplicationContext context;
    @Resource
    private Language language;
    @Resource
    private PaintingArea paintArea;
    @Resource
    private SelectionHandler selectionHandler;
    private RibbonTab start;

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

        Button save = new TooltipButton(language.get("action.save"), image("save16"));
        Button undo = new TooltipButton(language.get("action.undo"), image("undo16"));
        Button redo = new TooltipButton(language.get("action.redo"), image("redo16"));
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
        createToolsGroup(start);

    }

    private void createClipboardGroup(RibbonTab tab) {
        RibbonGroup clipboard = new RibbonGroup();
        clipboard.setTitle(language.get("action.clipboard"));
        tab.getRibbonGroups().add(clipboard);

        Button paste = TooltipButton.big(language.get("action.paste"), image("clipboard48"));

        Button cut = TooltipButton.small(language.get("action.cut"), image("cut16"));
        cut.disableProperty().bind(selectionHandler.getActiveProperty().not());
        Button copy = TooltipButton.small(language.get("action.copy"), image("copy16"));
        copy.disableProperty().bind(selectionHandler.getActiveProperty().not());
        Column column = new Column();
        column.getChildren().addAll(cut, copy);

        clipboard.getNodes().addAll(paste, column);
    }

    private void createImageGroup(RibbonTab tab) {
        RibbonGroup image = new RibbonGroup();
        image.setTitle(language.get("menu.image"));
        tab.getRibbonGroups().add(image);

        MenuButton select = TooltipMenuButton.big(language.get("action.select"), image("select48"));
        MenuItem selectForm = new CaptionMenuItem(language.get("menu.caption.select-form"));
        RadioMenuItem formRectangle = new RadioMenuItem(language.get("menu.radio.form-rectangle"));
        RadioMenuItem formFree = new RadioMenuItem(language.get("menu.radio.free-form"));
        MenuItem selectOptions = new CaptionMenuItem(language.get("menu.caption.select-options"));
        MenuItem selectAll = new MenuItem(language.get("action.select-all"));
        MenuItem invertSelection = new MenuItem(language.get("action.invert-selection"));
        MenuItem removeSelection = new MenuItem(language.get("action.remove-selection"));
        select.getItems().addAll(selectForm, formRectangle, formFree, selectOptions, selectAll, invertSelection, removeSelection);

        Button crop = TooltipButton.small(language.get("action.crop"), image("crop16"));
        crop.disableProperty().bind(selectionHandler.getActiveProperty().not());
        Column column1 = new Column();
        column1.getChildren().addAll(crop);

        Button changeSize = TooltipButton.small(language.get("action.resize"), image("resize16"));
        Button scale = TooltipButton.small(language.get("action.scale"), image("scale16"));
        MenuButton rotate = TooltipMenuButton.small(language.get("action.rotate"), image("rotate16"));
        MenuItem rotate90Right = new MenuItem(language.get("action.rotate-90-right"));
        MenuItem rotate90Left = new MenuItem(language.get("action.rotate-90-left"));
        MenuItem rotate180 = new MenuItem(language.get("action.rotate-180"));
        MenuItem mirrorVertical = new MenuItem(language.get("action.mirror-vertically"));
        MenuItem mirrorHorizontal = new MenuItem(language.get("action.mirror-horizontally"));
        rotate.getItems().addAll(rotate90Right, rotate90Left, rotate180, mirrorVertical, mirrorHorizontal);
        Column column2 = new Column();
        column2.getChildren().addAll(changeSize, scale, rotate);

        image.getNodes().addAll(select, column1, column2);
    }

    private void createToolsGroup(RibbonTab tab) {
        RibbonGroup tools = new RibbonGroup();
        tools.setTitle(language.get("menu.tools"));
        tab.getRibbonGroups().add(tools);

        Button pencil = TooltipIconButton.small(language.get("action.choose-pen"), image("pencil16"));
        Button eraser = TooltipIconButton.small(language.get("action.choose-eraser"), image("eraser16"));
        Column column1 = new Column();
        column1.getChildren().addAll(pencil, eraser);

        Button filler = TooltipIconButton.small(language.get("action.choose-filler"), image("filler16"));
        Button pipette = TooltipIconButton.small(language.get("action.choose-pipette"), image("pipette16"));
        Column column2 = new Column();
        column2.getChildren().addAll(filler, pipette);

        Button text = TooltipIconButton.small(language.get("action.chose-text"), image("text16"));
        Button zoom = TooltipIconButton.small(language.get("action.choose-zoom"), image("zoom16"));
        Column column3 = new Column();
        column3.getChildren().addAll(text, zoom);

        tools.getNodes().addAll(column1, column2, column3);
    }

    private ImageView image(String name) {
        return new ImageView(context.getBean(name, String.class));
    }

}
