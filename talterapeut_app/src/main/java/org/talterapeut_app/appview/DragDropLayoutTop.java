package org.talterapeut_app.appview;

import java.util.ArrayList;

import org.talterapeut_app.AppView;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import fi.jasoft.dragdroplayouts.DDPanel;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;

public class DragDropLayoutTop extends VerticalLayout {
    ArrayList<DDPanel> topPanels;
    CssLayout dragDropAreaTop;
    Label phraseLabel;

    public DragDropLayoutTop() {
        topPanels = new ArrayList<DDPanel>();
        dragDropAreaTop = new CssLayout();
        phraseLabel = new Label();

        for (int i = 0; i < 3; i++) {
            DDPanel top = new DDPanel();
            top.setWidth("125px");
            top.setHeight("125px");
            top.setDragMode(LayoutDragMode.CLONE);
            top.setDropHandler(new SwapPanelDropHandler());
            topPanels.add(top);
            dragDropAreaTop.addComponent(topPanels.get(i));
        }

        setSizeFull();
        dragDropAreaTop.setSizeFull();
        phraseLabel.setSizeFull();
        addComponent(dragDropAreaTop);
        addComponent(phraseLabel);
        setExpandRatio(dragDropAreaTop, 1);
        setExpandRatio(phraseLabel, 1);
    }

    public void setPhraseLabel(String str) {
        phraseLabel.setValue(str);
    }

    public void resetDragDropArea() {
        int len = AppView.getPhraseLength();
        for (int i = 0; i < topPanels.size(); i++) {
            topPanels.get(i).setContent(null);

            if (i >= len) {
                topPanels.get(i).setVisible(false);
            } else {
                topPanels.get(i).setVisible(true);
            }

        }
        phraseLabel.setValue("HOWDY");
    }

    public ArrayList<Component> getComponents() {
        ArrayList<Component> tmp = new ArrayList<Component>();
        int len = dragDropAreaTop.getComponentCount();

        for (int i = 0; i < len; i++) {
            tmp.add(((DDPanel) dragDropAreaTop.getComponent(i)).getContent());
        }
        return tmp;
    }
}