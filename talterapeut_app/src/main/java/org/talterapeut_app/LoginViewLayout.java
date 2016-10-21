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
    protected TextField emailField;
    protected TextField usernameField;
    protected Button loginButton;
    protected Button registerButton;

    public LoginViewLayout() {
        Design.read(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        emailField.focus();
    }
}
