package org.talterapeut_app.appview;

import java.util.ArrayList;

import org.talterapeut_app.AppView;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

import fi.jasoft.dragdroplayouts.DDPanel;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;

public class DragDropLayoutBottom extends CssLayout {
    ArrayList<DDPanel> bottomPanels;
    int add_index;

    // CssLayout dragDropAreaBottom;

    public DragDropLayoutBottom() {
        bottomPanels = new ArrayList<DDPanel>();
        for (int i = 0; i < 3; i++) {
            DDPanel bottom = new DDPanel();
            bottom.addStyleName("noscroll");
            bottom.setWidth("140px");
            bottom.setHeight("140px");
            bottom.setDragMode(LayoutDragMode.CLONE);
            bottom.setDropHandler(new SwapPanelDropHandler());
            bottomPanels.add(bottom);
            addComponent(bottomPanels.get(i));
        }
    }

    public void resetDragDropArea() {
        int len = AppView.getPhraseLength();
        for (int i = 0; i < bottomPanels.size(); i++) {
            bottomPanels.get(i).setContent(null);

            if (i >= len) {
                bottomPanels.get(i).setVisible(false);
            } else {
                bottomPanels.get(i).setVisible(true);
            }

        }
        add_index = 0;
    }

    public void addPicture(Component n) {
        if (add_index < AppView.getPhraseLength()) {
            bottomPanels.get(add_index).setContent(n);
            add_index++;
        }
    }
}