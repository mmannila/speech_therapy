package org.talterapeut_app;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

public class ProfileView extends VerticalLayout implements View {

    public ProfileView() {
        VerticalLayout testLayout = new VerticalLayout();
        testLayout.addComponent(new Label("Profile goes here"));
        addComponent(testLayout);
        testLayout.setMargin(true);
        testLayout.setSpacing(true);
        testLayout.setSizeFull();

        MenuBar menu = TerapeutUI.getMenuBar();
        addComponent(menu);

        // ensures the MenuBar's height is always minimized
        // NOTICE: add these two lines for the other views using the menu bar
        setExpandRatio(testLayout, 1);
        setExpandRatio(menu, 0);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}