package org.talterapeut_app;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.talterapeut_app.loginview.LoginDAO;

import java.sql.SQLException;

/**
 * Created by daniel on 10/6/16.
 */
public class LoginView extends VerticalLayout implements View {

    private PasswordField passwordField;
    private TextField usernameField;
    private LoginDAO login;

    public LoginView() {

        VerticalLayout mainLayout = new VerticalLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();

        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);

        buttonLayout.setSpacing(true);
        buttonLayout.setMargin(true);

        usernameField = new TextField("Användarnamn:");
        usernameField.setInputPrompt("epost@adress.com");
        usernameField.setRequired(true);
        usernameField.setWidth("300");

        passwordField = new PasswordField("Lösenord:");
        passwordField.setRequired(true);
        passwordField.setWidth("300");

        Button loginButton = new Button("Logga in");
        loginButton.addClickListener(login -> {
            try {
                Login();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        Button registerButton = new Button("Registrera");
        registerButton.addClickListener(register -> {
            try {
                Register();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        buttonLayout.addComponents(loginButton, registerButton);
        mainLayout.addComponents(usernameField, passwordField, buttonLayout);

        mainLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(usernameField, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(passwordField, Alignment.MIDDLE_CENTER);

        addComponent(mainLayout);
    }

    private void Login() throws SQLException, ClassNotFoundException {
        login = new LoginDAO(usernameField.getValue(), passwordField.getValue());

        if (login.Validation() && login.CheckUser()) {
            TerapeutUI.navigator.navigateTo(TerapeutUI.APPVIEW);
        } else {
            Notification.show("Felaktigt användarnamn eller lösenord.");
        }
    }

    private void Register() throws SQLException, ClassNotFoundException {
        login = new LoginDAO(usernameField.getValue(), passwordField.getValue());

        if (login.Validation() && login.CreateUser()) {
            Notification.show("Registreringen lyckades.");

        } else {
            Notification.show("Registreringen misslyckades.");
            usernameField.clear();
            passwordField.clear();
            usernameField.focus();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        usernameField.focus();
    }
}

