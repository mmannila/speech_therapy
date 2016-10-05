package org.talterapeut_app.appview;

import java.util.ArrayList;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import fi.jasoft.dragdroplayouts.DDCssLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultCssLayoutDropHandler;

public class DragDropLayouts extends VerticalLayout {
    DDCssLayout dragDropAreaTop;
    DDCssLayout dragDropAreaBottom;
    Label phraseLabel;

    public DragDropLayouts() {
        dragDropAreaTop = new DDCssLayout();
        dragDropAreaTop.setDragMode(LayoutDragMode.CLONE);
        dragDropAreaTop.setDropHandler(new DefaultCssLayoutDropHandler());
        dragDropAreaTop.setSizeFull();

        dragDropAreaBottom = new DDCssLayout();
        dragDropAreaBottom.setDragMode(LayoutDragMode.CLONE);
        dragDropAreaBottom.setDropHandler(new DefaultCssLayoutDropHandler());
        dragDropAreaBottom.setSizeFull();

        phraseLabel = new Label();

        setSizeFull();
        addComponent(dragDropAreaTop);
        addComponent(phraseLabel);
        addComponent(dragDropAreaBottom);

        setExpandRatio(dragDropAreaTop, 1);
        setExpandRatio(dragDropAreaBottom, 1);
        setExpandRatio(phraseLabel, 0);
    }

    public void setPhraseLabel(String str) {
        phraseLabel.setValue(str);
    }

    public void resetDragDropArea() {
        dragDropAreaTop.removeAllComponents();
        dragDropAreaBottom.removeAllComponents();
        phraseLabel.setValue("");
    }

    public void addPicture(Component n) {
        dragDropAreaBottom.addComponent(n);
    }

    public ArrayList<Component> getTopComponents() {
        ArrayList<Component> tmp = new ArrayList<Component>();
        int len = dragDropAreaTop.getComponentCount();

        for (int i = 0; i < len; i++) {
            tmp.add(dragDropAreaTop.getComponent(i));
        }
        return tmp;
    }
}