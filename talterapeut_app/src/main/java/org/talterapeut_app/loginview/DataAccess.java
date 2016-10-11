package org.talterapeut_app.loginview;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

/**
 * Methods used for accessing the login database by the LoginDAO class.
 * Created by daniel on 10/6/16.
 */

public abstract class DataAccess {

    private Connection conn = null;
    private PreparedStatement statement = null;

    protected DataAccess() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString() + "/src/main/java/org/talterapeut_app/data/login_users.db";
        String path = "jdbc:sqlite:" + s;
        conn = DriverManager.getConnection(path);
    }

    ResultSet GetUserData(String username, String password) throws SQLException {
        ResultSet rs = null;

        try {

            statement = conn.prepareStatement("SELECT * FROM login_users WHERE userName = ? AND userPass = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            rs = statement.executeQuery();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return rs;
    }

    boolean RegisterUserData(String username, String password) throws SQLException {

        boolean updateCheck = false;
        ResultSet rs = GetUserData(username, password);

        if (rs == null) {

            try {

                statement = conn.prepareStatement("INSERT INTO login_users (userName, userPass) values (?, ?)");
                statement.setString(1, username);
                statement.setString(2, password);
                statement.executeUpdate();

                updateCheck = true;

            } catch (SQLException e) {

                updateCheck = false;
                e.printStackTrace();

            }
        }

        return updateCheck;
    }

    void CloseConnection() throws SQLException {
        statement.close();
        conn.close();
    }
}
