package org.talterapeut_app.profileview;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.talterapeut_app.data.DataAccess;

public class ProfileDAO extends DataAccess {
    private String userName = null;
    private String userEmail = null;
    private String userPassword = null;
    private static ResultSet rs;

    public ProfileDAO(String email, String user, String pass)
            throws SQLException, ClassNotFoundException {
        super();
        this.userName = user;
        this.userEmail = email;
        this.userPassword = pass;
    }

    public ProfileDAO(String email, String pass) throws SQLException,
            ClassNotFoundException {
        super();
        this.userEmail = email;
        this.userPassword = pass;
    }

    public boolean CheckUser() throws SQLException {
        rs = LoginValidator(userEmail, userPassword);
        boolean userChecked = false;

        while (rs.next()) {
            if (userEmail.equals(rs.getString("userEmail"))
                    || userEmail.equals(rs.getString("userName"))
                    && userPassword.equals(rs.getString("userPass"))) {
                userChecked = true;
                break;
            }
        }

        CloseConnection();
        return userChecked;
    }

    public boolean ChangePassword() throws SQLException {
        boolean userCreated = ChangeUserPassword(userName, userPassword);
        CloseConnection();

        return userCreated;
    }
}