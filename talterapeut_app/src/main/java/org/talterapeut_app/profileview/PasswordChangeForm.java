package org.talterapeut_app.profileview;

import java.sql.SQLException;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.UI;

public class PasswordChangeForm extends FormLayout {
    PasswordField currentPass = new PasswordField("Password");
    PasswordField newPass = new PasswordField("New Password");
    PasswordField repeatPass = new PasswordField("Repeat New Password");
    Button confirmButton = new Button("Change Password");

    ProfileDAO profile_dao;
    String sessionEmail;

    public PasswordChangeForm() {

        TextChangeListener passwordListen = new TextChangeListener() {
            @Override
            public void textChange(TextChangeEvent event) {
                if (event.getText().length() > 4
                        && event.getText().length() < 20) {
                    event.getComponent().setIcon(FontAwesome.CHECK_CIRCLE);
                } else {
                    event.getComponent().setIcon(FontAwesome.WARNING);
                }
            }

        };

        currentPass.setRequired(true);
        currentPass.setWidth("300");
        currentPass.addTextChangeListener(passwordListen);
        newPass.setRequired(true);
        newPass.setWidth("300");
        newPass.addTextChangeListener(passwordListen);
        repeatPass.setRequired(true);
        repeatPass.setWidth("300");
        repeatPass.addTextChangeListener(passwordListen);

        confirmButton
                .addClickListener(e -> {
                    try {
                        String sessionEmail = (String) UI.getCurrent()
                                .getSession().getAttribute("email");
                        String sessionUser = (String) UI.getCurrent()
                                .getSession().getAttribute("username");
                        profile_dao = new ProfileDAO(sessionEmail, sessionUser,
                                currentPass.getValue());
                        if (validateFields()) {
                            profile_dao = new ProfileDAO(sessionEmail,
                                    sessionUser, newPass.getValue());

                            currentPass.clear();
                            newPass.clear();
                            repeatPass.clear();

                            if (profile_dao.ChangePassword()) {
                                Notification.show(
                                        "The password has been changed!",
                                        Type.HUMANIZED_MESSAGE);
                            } else {
                                Notification
                                        .show("A database error occurred while changing the password.",
                                                Type.ERROR_MESSAGE);
                            }
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                });

        addComponent(currentPass);
        addComponent(newPass);
        addComponent(repeatPass);
        addComponent(confirmButton);
        setMargin(true);
        setSpacing(true);
    }

    // checks for the current password and if the new password fields are valid
    private boolean validateFields() throws ClassNotFoundException,
            SQLException {
        String newPassword = newPass.getValue();

        if (!profile_dao.CheckUser()) {
            Notification.show("The current password is incorrect.",
                    Type.WARNING_MESSAGE);
            return false;
        }

        if (!(newPassword != null && newPassword.length() >= 5
                && newPassword.length() <= 20 && !newPassword.contains(" "))) {
            Notification
                    .show("The new password is invalid. It may not contains any spaces and must be between 5 to 20 characters long.",
                            Type.WARNING_MESSAGE);
            return false;
        }

        if (!newPassword.equals(repeatPass.getValue())) {
            Notification.show(
                    "The repeated password does not match the new one.",
                    Type.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

}