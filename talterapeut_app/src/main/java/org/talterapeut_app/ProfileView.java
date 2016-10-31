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
import com.vaadin.ui.themes.ValoTheme;

public class ProfileView extends VerticalLayout implements View {
    VerticalLayout profileLayout;
    Label profileHeader = new Label();

    public ProfileView() {
        profileHeader.addStyleName(ValoTheme.LABEL_H2);

        Panel changeEmail = new Panel("Change E-mail Address");
        changeEmail.setContent(new EmailChangeForm());
        changeEmail.setWidthUndefined();

        Panel changePassword = new Panel("Change Password");
        changePassword.setContent(new PasswordChangeForm());
        changePassword.setWidthUndefined();

        profileLayout = new VerticalLayout();
        profileLayout.setMargin(true);
        profileLayout.setSpacing(true);
        profileLayout.setSizeUndefined();

        profileLayout.addComponent(profileHeader);
        profileLayout.addComponent(changeEmail);
        profileLayout.addComponent(changePassword);

        Panel profilePanel = new Panel();
        profilePanel.addStyleName("profilepanel");
        profilePanel.setContent(profileLayout);
        profilePanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        profilePanel.setSizeFull();
        MenuBar menu = TerapeutUI.getMenuBar();

        setSizeFull();
        addComponent(profilePanel);
        setExpandRatio(profilePanel, 1);
        addComponent(menu);
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