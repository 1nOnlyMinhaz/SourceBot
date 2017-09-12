package bid.ApixTeam.bot.utils.api;

import bid.ApixTeam.bot.utils.connection.Datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class DatabaseManager {
    private static DatabaseManager databaseManager = new DatabaseManager();

    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public Connection getConnection() throws SQLException {
        return Datasource.getConnection();
    }

    public void closeConnection(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if (resultSet != null)
            resultSet.close();
        if (statement != null)
            statement.close();
        if (connection != null)
            connection.close();
    }
}
