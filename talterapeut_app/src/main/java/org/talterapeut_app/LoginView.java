package org.talterapeut_app;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import org.talterapeut_app.loginview.LoginDAO;

import java.sql.SQLException;

/**
 * Loginview clickListeners
 */

public class LoginView extends LoginViewLayout{

    private LoginDAO login;

    public LoginView() {
        // Bind event handlers to declarative UI with static typing
        loginButton.addClickListener(this::loginButton);
        loginButton.setClickShortcut(KeyCode.ENTER);
        registerButton.addClickListener(this::registerButton);
        createUserButton.addClickListener(this::createUserButton);
        cancelButton.addClickListener(this::cancel);
        emailField.addTextChangeListener(this::emailListener); //email field for registration only
        emailUsernameField.addTextChangeListener(this::emailUsernameListener); //field for username or email at login
        passwordField.addTextChangeListener(this::passListener);
        usernameField.addTextChangeListener(this::userNameListener);
    }

    private void initialViewState() {
        emailUsernameField.setVisible(true);
        emailField.setVisible(false);
        cancelButton.setVisible(false);
        registerButton.setVisible(false);
        usernameField.setVisible(false);

        loginButton.setVisible(true);
        createUserButton.setVisible(true);
        usernameField.clear();
        passwordField.clear();
        emailUsernameField.clear();

        usernameField.setIcon(null);
        emailUsernameField.setIcon(null);
        passwordField.setIcon(null);
    }

    private void emailUsernameListener(FieldEvents.TextChangeEvent event) {
        if (!event.getText().equals("") && event.getText().length() < 50) {
            emailUsernameField.setIcon(FontAwesome.CHECK_CIRCLE);
        } else {
            emailUsernameField.setIcon(FontAwesome.WARNING);
        }
    }

    private void userNameListener(FieldEvents.TextChangeEvent event) {
        if (!event.getText().equals("") && event.getText().length() < 50) {
            usernameField.setIcon(FontAwesome.CHECK_CIRCLE);
        } else {
            usernameField.setIcon(FontAwesome.WARNING);
        }
    }

    private void emailListener(FieldEvents.TextChangeEvent event) {
        if (!event.getText().equals("") && new EmailValidator("").isValid(event.getText())
                && event.getText().length() < 50) {
            emailField.setIcon(FontAwesome.CHECK_CIRCLE);
        } else {
            emailField.setIcon(FontAwesome.WARNING);
        }
    }

    private void passListener(FieldEvents.TextChangeEvent event) {
        if (event.getText().length() > 4 && event.getText().length() < 20) {
            passwordField.setIcon(FontAwesome.CHECK_CIRCLE);
        } else {
            passwordField.setIcon(FontAwesome.WARNING);
        }
    }

    public void cancel(Button.ClickEvent event) {
        initialViewState();
    }

    public void loginButton(Button.ClickEvent event) {
        try {
            Login();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //this changes to the viewstate for registering
    public void createUserButton(Button.ClickEvent event) {
        loginButton.setVisible(false);
        createUserButton.setVisible(false);

        cancelButton.setVisible(true);
        usernameField.setVisible(true);
        registerButton.setVisible(true);

        emailUsernameField.setVisible(false);
        emailField.setVisible(true);
        emailField.clear();
        emailField.setCaption("E-mail");
        emailField.setIcon(null);
        passwordField.setIcon(null);

        usernameField.clear();
        passwordField.clear();
    }

    public void registerButton(Button.ClickEvent event) {
        try {
            Register();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void Login() throws SQLException, ClassNotFoundException {
        login = new LoginDAO(emailUsernameField.getValue(), passwordField.getValue());

        if (login.CheckUser()) {
            TerapeutUI.navigator.navigateTo(TerapeutUI.APPVIEW);
        } else {
            Notification.show("Wrong username or password.");
        }
    }

    private void Register() throws SQLException, ClassNotFoundException {
        login = new LoginDAO(emailField.getValue(), usernameField.getValue(), passwordField.getValue());

        if (login.Validation() && login.CreateUser()) {
            Notification.show("Successful registration.");
            initialViewState();

        } else {
            Notification.show("Registration failed.");
            emailField.clear();
            passwordField.clear();
            usernameField.clear();
            emailField.focus();

            emailField.setIcon(null);
            passwordField.setIcon(null);
            usernameField.setIcon(null);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        initialViewState();
    }
}
