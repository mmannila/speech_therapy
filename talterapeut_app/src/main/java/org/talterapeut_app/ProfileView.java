package org.talterapeut_app;

import org.talterapeut_app.profileview.PasswordChangeForm;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ProfileView extends VerticalLayout implements View {

    public ProfileView() {
        VerticalLayout profileLayout = new VerticalLayout();
        profileLayout.setHeight("100%");
        profileLayout.setMargin(true);
        profileLayout.setSpacing(true);
        profileLayout.setSizeFull();

        Panel changePassword = new Panel("Change Password");
        changePassword.setContent(new PasswordChangeForm());
        changePassword.setWidthUndefined();
        profileLayout.addComponent(changePassword);

        MenuBar menu = TerapeutUI.getMenuBar();

        addComponent(profileLayout);
        addComponent(menu);

        // ensures the MenuBar's height is always minimized
        // NOTICE: add these two lines for the other views using the menu bar
        setExpandRatio(profileLayout, 1);
        setExpandRatio(menu, 0);
    }

    @Override
    public void enter(ViewChangeEvent event) {

        if (UI.getCurrent().getSession().getAttribute("email") == null) {
            TerapeutUI.navigator.navigateTo(TerapeutUI.LOGINVIEW);
        }
    }
}