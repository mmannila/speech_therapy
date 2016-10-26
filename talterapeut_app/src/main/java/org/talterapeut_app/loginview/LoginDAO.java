package org.talterapeut_app.loginview;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.UI;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.talterapeut_app.data.DataAccess;

/**
 * Login Data Access Object
 */
public class LoginDAO extends DataAccess {
    private String userName = null;
    private String userEmail = null;
    private String userPassword = null;
    private static ResultSet rs;

    public LoginDAO(String email, String user, String pass) throws SQLException, ClassNotFoundException {
        super();
        this.userName = user;
        this.userEmail = email;
        this.userPassword = pass;
    }

    public LoginDAO(String email, String pass) throws SQLException, ClassNotFoundException {
        super();
        this.userEmail = email;
        this.userPassword = pass;
    }

    public boolean CheckUser() throws SQLException {
        rs = LoginValidator(userEmail, userPassword);
        boolean userChecked = false;

        while (rs.next()) {
            if (userEmail.equals(rs.getString("userEmail")) || userEmail.equals(rs.getString("userName"))
                    && userPassword.equals(rs.getString("userPass"))) {
                UI.getCurrent().getSession().setAttribute("email", rs.getString("userEmail"));
                UI.getCurrent().getSession().setAttribute("username", rs.getString("userName"));
                userChecked = true;
                break;
            }
        }

        CloseConnection();
        return userChecked;
    }

    public boolean CreateUser() throws SQLException {
        boolean userCreated = UpdateUserData(userEmail, userName, userPassword);
        CloseConnection();

        return userCreated;
    }

    private boolean ValidateUser() throws SQLException, ClassNotFoundException {
        EmailValidator emailValidator = new EmailValidator("");
        boolean validUsername = userName != null || !userName.equals(" ");
        return emailValidator.isValid(userEmail) && userEmail != null && userEmail.length() <= 50 && validUsername;
    }

    private boolean ValidatePassword() throws SQLException, ClassNotFoundException {
        return userPassword != null && userPassword.length() >= 5 && userPassword.length() <= 20
                && !userPassword.contains(" ");
    }

    public boolean Validation() throws SQLException, ClassNotFoundException {
        return ValidatePassword() && ValidateUser();
    }

}
