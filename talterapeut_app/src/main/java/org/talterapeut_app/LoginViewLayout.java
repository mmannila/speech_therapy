package org.talterapeut_app;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

/**
 * Loginview layout
 */

@DesignRoot
public class LoginViewLayout extends VerticalLayout implements View {

    protected PasswordField passwordField;
    protected TextField emailField; //Field for e-mail ONLY att registration
    protected TextField usernameField; //User specified username
    protected TextField emailUsernameField; //Field for username OR e-mail in login
    protected Button loginButton; //Logs in with username OR e-mail AND password
    protected Button registerButton; //THE register button for finalizing the registration process.
    protected Button createUserButton; //"register" button at login. Changes viewstate only.
    protected Button cancelButton; //Cancels register process.

    public LoginViewLayout() {
        Design.read(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
