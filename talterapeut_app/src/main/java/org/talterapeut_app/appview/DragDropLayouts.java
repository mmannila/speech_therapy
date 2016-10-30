package org.talterapeut_app.appview;

import java.util.ArrayList;

import org.talterapeut_app.AppView;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import fi.jasoft.dragdroplayouts.DDPanel;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;

public class DragDropLayouts extends VerticalLayout {
    ArrayList<DDPanel> topPanels;
    ArrayList<DDPanel> bottomPanels;
    HorizontalLayout dragDropAreaTop;
    HorizontalLayout dragDropAreaBottom;
    // CssLayout dragDropAreaTop;
    // CssLayout dragDropAreaBottom;
    Label phraseLabel;
    int add_index;

    public DragDropLayouts() {
        topPanels = new ArrayList<DDPanel>();
        bottomPanels = new ArrayList<DDPanel>();

        dragDropAreaTop = new HorizontalLayout();
        // dragDropAreaTop = new CssLayout();
        // dragDropAreaTop.setSizeFull();

        dragDropAreaBottom = new HorizontalLayout();
        // dragDropAreaBottom = new CssLayout();
        // dragDropAreaBottom.setSizeFull();

        for (int i = 0; i < 3; i++) {
            DDPanel top = new DDPanel();
            top.setWidth("140px");
            top.setHeight("140px");
            top.setDragMode(LayoutDragMode.CLONE);
            top.setDropHandler(new SwapPanelDropHandler());
            topPanels.add(top);
            dragDropAreaTop.addComponent(topPanels.get(i));

            DDPanel bottom = new DDPanel();
            bottom.setWidth("140px");
            bottom.setHeight("140px");
            bottom.setDragMode(LayoutDragMode.CLONE);
            bottom.setDropHandler(new SwapPanelDropHandler());
            bottomPanels.add(bottom);
            dragDropAreaTop.addComponent(bottomPanels.get(i));
        }

        phraseLabel = new Label();

        setSizeFull();
        addComponent(dragDropAreaTop);
        addComponent(phraseLabel);
        addComponent(dragDropAreaBottom);

        setExpandRatio(dragDropAreaTop, 1);
        setExpandRatio(dragDropAreaBottom, 1);
        setExpandRatio(phraseLabel, 1);
    }

    public void setPhraseLabel(String str) {
        phraseLabel.setValue(str);
    }

    public void resetDragDropArea() {
        int len = AppView.getPhraseLength();
        for (int i = 0; i < topPanels.size(); i++) {
            topPanels.get(i).setContent(null);
            bottomPanels.get(i).setContent(null);

            if (i >= len) {
                topPanels.get(i).setVisible(false);
                bottomPanels.get(i).setVisible(false);
            } else {
                topPanels.get(i).setVisible(true);
                bottomPanels.get(i).setVisible(true);
            }

        }
        phraseLabel.setValue("");
        add_index = 0;
    }

    public void addPicture(Component n) {
        if (add_index < AppView.getPhraseLength()) {
            bottomPanels.get(add_index).setContent(n);
            add_index++;
        }
    }

    public ArrayList<Component> getTopComponents() {
        ArrayList<Component> tmp = new ArrayList<Component>();
        int len = dragDropAreaTop.getComponentCount();

        for (int i = 0; i < len; i++) {
            tmp.add(((DDPanel) dragDropAreaTop.getComponent(i)).getContent());
        }
        return tmp;
    }

}