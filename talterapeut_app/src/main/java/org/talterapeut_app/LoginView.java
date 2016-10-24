package org.talterapeut_app;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
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
        registerButton.addClickListener(this::registerButton);
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
        login = new LoginDAO(emailField.getValue(), passwordField.getValue());

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

        } else {
            Notification.show("Registration failed.");
            emailField.clear();
            passwordField.clear();
            usernameField.clear();
            emailField.focus();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        emailField.focus();
    }
}
