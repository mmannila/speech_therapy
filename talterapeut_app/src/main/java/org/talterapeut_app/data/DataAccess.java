package org.talterapeut_app.data;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Methods for authentication and updating the login database.
 *
 */

public abstract class DataAccess {

    private Connection conn = null;
    private PreparedStatement statement = null;

    protected DataAccess() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString()
                + "/src/main/java/org/talterapeut_app/data/login.db";
        String path = "jdbc:sqlite:" + s;
        conn = DriverManager.getConnection(path);
    }

    protected ResultSet LoginValidator(String email, String password) {
        ResultSet rs = null;

        try {

            statement = conn
                    .prepareStatement("SELECT userEmail, userName, userPass FROM users "
                            + "WHERE userPass = ? AND (userEmail = ? OR userName = ?);");
            statement.setString(1, password);
            statement.setString(2, email);
            statement.setString(3, email);
            rs = statement.executeQuery();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return rs;
    }

    protected boolean UpdateUserData(String email, String username,
            String password) throws SQLException {

        try {
            statement = conn
                    .prepareStatement("INSERT INTO users (userEmail, userName, userPass) VALUES (?, ?, ?);");
            statement.setString(1, email);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    protected boolean ChangeUserPassword(String username, String password)
            throws SQLException {

        try {
            statement = conn
                    .prepareStatement("UPDATE table_name SET password= ? WHERE username= ?;");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    protected void CloseConnection() throws SQLException {
        statement.close();
        conn.close();
    }
}
