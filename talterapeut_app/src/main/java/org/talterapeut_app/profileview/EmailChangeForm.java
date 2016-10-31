package org.talterapeut_app.profileview;

import java.sql.SQLException;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class EmailChangeForm extends FormLayout {
    TextField newEmail = new TextField("New E-mail address");
    PasswordField password = new PasswordField("Password");
    Button confirmButton = new Button("Change E-mail");

    ProfileDAO profile_dao;

    public EmailChangeForm() {
        newEmail.addTextChangeListener(e -> {
            System.out.println("email: " + e.getText());
            if (!e.getText().equals("") && e.getText().length() < 50) {
                newEmail.setIcon(FontAwesome.CHECK_CIRCLE);
            } else {
                newEmail.setIcon(FontAwesome.WARNING);
            }
        });

        password.addTextChangeListener(e -> {
            if (e.getText().length() > 4 && e.getText().length() < 20) {
                password.setIcon(FontAwesome.CHECK_CIRCLE);
            } else {
                password.setIcon(FontAwesome.WARNING);
            }
        });

        newEmail.setRequired(true);
        newEmail.setWidth("300");
        password.setRequired(true);
        password.setWidth("300");

        confirmButton
                .addClickListener(e -> {
                    try {
                        String sessionUser = (String) UI.getCurrent()
                                .getSession().getAttribute("username");
                        profile_dao = new ProfileDAO((String) UI.getCurrent()
                                .getSession().getAttribute("email"),
                                sessionUser, password.getValue());

                        if (validateFields(newEmail.getValue())) {
                            profile_dao = new ProfileDAO(newEmail.getValue(),
                                    sessionUser, password.getValue());

                            newEmail.clear();
                            password.clear();

                            if (profile_dao.ChangeEmail()) {
                                Notification.show(
                                        "The e-mail address has been changed!",
                                        Type.HUMANIZED_MESSAGE);
                            } else {
                                Notification
                                        .show("A database error occurred while changing the password.",
                                                Type.ERROR_MESSAGE);
                            }
                        }
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    // send mail
                });

        addComponent(newEmail);
        addComponent(password);
        addComponent(confirmButton);
        setMargin(true);
        setSpacing(true);
    }

    private boolean validateFields(String newEmail)
            throws ClassNotFoundException, SQLException {

        System.out.println(newEmail);

        if (newEmail.equals("") || !(new EmailValidator("").isValid(newEmail))) {
            Notification.show("The given e-mail address is not valid.",
                    Type.WARNING_MESSAGE);
            return false;
        }
        if (!profile_dao.CheckUser()) {
            Notification.show("The password is incorrect.",
                    Type.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}