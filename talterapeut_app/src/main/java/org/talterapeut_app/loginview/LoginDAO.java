package org.talterapeut_app.loginview;

import com.vaadin.data.validator.EmailValidator;

import java.sql.*;

/**
 * Login Data Access Object
 * Created by daniel on 10/6/16.
 */

//TODO add functionality for displaying a message when a username is taken.

public class LoginDAO extends DataAccess {
    private String username = null;
    private String password = null;

    public LoginDAO(String user, String pass) throws SQLException, ClassNotFoundException {
        super();
        this.username = user;
        this.password = pass;
    }

    public boolean CheckUser() throws SQLException {
        ResultSet rs = GetUserData(username, password);
        boolean userChecked = false;

        while (rs.next()) {
            if (username.equals(rs.getString("userName")) && password.equals(rs.getString("userPass"))) {
                userChecked = true;
                break;
            }
        }

        CloseConnection();
        return userChecked;
    }

    public boolean CreateUser() throws SQLException {
        boolean userCreated = RegisterUserData(username, password);
        CloseConnection();

        return userCreated;
    }

    private boolean ValidateUsername() throws SQLException, ClassNotFoundException {
        EmailValidator emailValidator = new EmailValidator("");
        return emailValidator.isValid(username) && username != null && username.length() <= 50;
    }

    private boolean ValidatePassword() throws SQLException, ClassNotFoundException {
        return password != null && password.length() >= 5 && password.length() <= 20 && !password.contains(" ");
    }

    public boolean Validation() throws SQLException, ClassNotFoundException {
        return ValidatePassword() && ValidateUsername();
    }

}
