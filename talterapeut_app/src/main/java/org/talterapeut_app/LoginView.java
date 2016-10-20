package org.talterapeut_app;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.talterapeut_app.loginview.LoginDAO;

import java.sql.SQLException;

/**
 * Loginview
 */

public class LoginView extends VerticalLayout implements View {

    private PasswordField passwordField;
    private TextField emailField;
    private TextField usernameField;
    private LoginDAO login;

    public LoginView() {

        VerticalLayout mainLayout = new VerticalLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();

        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);

        buttonLayout.setSpacing(true);
        buttonLayout.setMargin(true);

        emailField = new TextField("E-mail or username:");
        emailField.setInputPrompt("name@email.com");
        emailField.setRequired(true);
        emailField.setWidth("300");

        usernameField = new TextField("Username:");
        usernameField.setRequired(true);
        usernameField.setWidth("300");
        usernameField.setVisible(false);

        passwordField = new PasswordField("Password:");
        passwordField.setRequired(true);
        passwordField.setWidth("300");

        Button loginButton = new Button("Login");
        loginButton.addClickListener(login -> {
            try {
                Login();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        Button createUserButton = new Button("Create user");

        Button registerButton = new Button("Register");
        registerButton.setVisible(false);
        registerButton.addClickListener(register -> {
            try {
                Register();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        createUserButton.addClickListener(create -> {
            createUserButton.setVisible(false);
            registerButton.setVisible(true);
            usernameField.setVisible(true);
            emailField.setCaption("E-mail:");
        });

        buttonLayout.addComponents(loginButton, createUserButton, registerButton);
        mainLayout.addComponents(emailField, usernameField, passwordField, buttonLayout);

        mainLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(emailField, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(usernameField, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(passwordField, Alignment.MIDDLE_CENTER);

        addComponent(mainLayout);
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

