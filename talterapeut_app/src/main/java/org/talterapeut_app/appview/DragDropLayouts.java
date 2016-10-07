package org.talterapeut_app.appview;

import java.util.ArrayList;

import com.vaadin.ui.*;

import fi.jasoft.dragdroplayouts.DDGridLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultGridLayoutDropHandler;

public class DragDropLayouts extends DDGridLayout {
    Label phraseLabel; // TODO currently doesn't do anything

    public DragDropLayouts() {
        setRows(2);
        setColumns(3);
        setDragMode(LayoutDragMode.CLONE);
        setDropHandler(new DefaultGridLayoutDropHandler());

        phraseLabel = new Label();

        setSizeFull();
    }

    public void setPhraseLabel(String str) {
        phraseLabel.setValue(str);
    }

    public void resetDragDropArea() {
        removeAllComponents();
        phraseLabel.setValue("");
    }

    public void addPicture(Component n) {
        for (int i = 0; i < 3; i++) {
            // tests if cell is empty
            if (getComponent(i, 1) == null ) {
                addComponent(n, i, 1);
                n.setWidth("70%");
                n.setHeight("70%");
                setComponentAlignment(n, Alignment.MIDDLE_CENTER);
                break;
            }
        }
    }

    public ArrayList<Component> getTopComponents() {
        ArrayList<Component> tmp = new ArrayList<Component>();
        int len = getColumns();

        for (int i = 0; i < len; i++) {
            tmp.add(getComponent(i, 0));
        }
        return tmp;
    }
}