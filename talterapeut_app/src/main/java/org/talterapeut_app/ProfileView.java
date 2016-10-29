package org.talterapeut_app;

import org.talterapeut_app.profileview.EmailChangeForm;
import org.talterapeut_app.profileview.PasswordChangeForm;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ProfileView extends VerticalLayout implements View {
    VerticalLayout profileLayout;
    Label profileHeader = new Label();

    public ProfileView() {
        profileLayout = new VerticalLayout();
        profileLayout.setMargin(true);
        profileLayout.setSpacing(true);
        profileLayout.setSizeUndefined();

        profileHeader.setStyleName("h2");

        Panel changeEmail = new Panel("Change E-mail Address");
        changeEmail.setContent(new EmailChangeForm());
        changeEmail.setWidthUndefined();

        Panel changePassword = new Panel("Change Password");
        changePassword.setContent(new PasswordChangeForm());
        changePassword.setWidthUndefined();

        profileLayout.addComponent(profileHeader);
        profileLayout.addComponent(changeEmail);
        profileLayout.addComponent(changePassword);

        Panel profilePanel = new Panel();
        profilePanel.setContent(profileLayout);
        profilePanel.setSizeFull();
        profilePanel.getContent().setSizeUndefined();
        MenuBar menu = TerapeutUI.getMenuBar();

        addComponent(profilePanel);
        addComponent(menu);

        // ensures the MenuBar's height is always minimized
        // NOTICE: add these two lines for the other views using the menu bar
        setExpandRatio(profilePanel, 1);
        setExpandRatio(menu, 0);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        if (UI.getCurrent().getSession().getAttribute("email") == null) {
            TerapeutUI.navigator.navigateTo(TerapeutUI.LOGINVIEW);
        } else {
            profileHeader.setValue(UI.getCurrent().getSession()
                    .getAttribute("username").toString()
                    + "'s Profile");
        }
    }
}