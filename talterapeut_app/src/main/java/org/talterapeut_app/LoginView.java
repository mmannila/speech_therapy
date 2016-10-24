package org.talterapeut_app;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.talterapeut_app.loginview.LoginDAO;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * Loginview
 */

public class LoginView extends VerticalLayout implements View {

    private PasswordField passwordField;
    private TextField emailField;
    private TextField usernameField;
    private LoginDAO login;

    public LoginView() {

        Label lb = new Label("Sign in");

        VerticalLayout mainLayout = new VerticalLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();

        mainLayout.setWidth("400");

        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        buttonLayout.setSpacing(true);

        emailField = new TextField("E-mail or username:");
        emailField.setInputPrompt("name@email.com");
        emailField.setRequired(true);
        emailField.setWidth("250");

        usernameField = new TextField("Username:");
        usernameField.setRequired(true);
        usernameField.setWidth("250");
        usernameField.setVisible(false);

        passwordField = new PasswordField("Password:");
        passwordField.setRequired(true);
        passwordField.setWidth("250");

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
        Button cancelButton = new Button("Cancel");
        cancelButton.setVisible(false);

        Button registerButton = new Button("Register");
        registerButton.setVisible(false);

        registerButton.addClickListener(register -> {
            try {
                Register();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        createUserButton.addClickListener(create -> {
            createUserButton.setVisible(false);
            registerButton.setVisible(true);
            loginButton.setVisible(false);
            cancelButton.setVisible(true);
            usernameField.setVisible(true);
            emailField.setCaption("E-mail:");
        });

        cancelButton.addClickListener(cancel -> Page.getCurrent().setLocation("/"));

        buttonLayout.addComponents(loginButton, createUserButton, registerButton, cancelButton);
        mainLayout.addComponents(lb, emailField, usernameField, passwordField, buttonLayout);

        addComponent(mainLayout);
        setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);
        setResponsive(true);
    }

    private void Login() throws SQLException, ClassNotFoundException {
        login = new LoginDAO(emailField.getValue(), passwordField.getValue());

        if (login.CheckUser()) {
            TerapeutUI.navigator.navigateTo(TerapeutUI.APPVIEW);
        } else {
            Notification.show("Wrong username or password.");
        }
    }

    private void Register() throws SQLException, ClassNotFoundException, InterruptedException {
        login = new LoginDAO(emailField.getValue(), usernameField.getValue(), passwordField.getValue());

        if (login.Validation() && login.CreateUser()) {
            TimeUnit.SECONDS.sleep(1);
            Page.getCurrent().setLocation("/");
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

