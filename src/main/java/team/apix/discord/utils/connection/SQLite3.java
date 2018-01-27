package team.apix.discord.utils.connection;

import team.apix.discord.utils.vars.Lists;
import team.apix.discord.utils.vars.entites.Cooldown;

import java.sql.*;
import java.util.ArrayList;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class SQLite3 {
    public Connection getConnection() throws SQLException {
        // SQLite connection string
        String url = String.format("jdbc:sqlite:%s/db/logger.db", System.getProperty("user.dir"));

        Connection conn = null;
        conn = DriverManager.getConnection(url);
        return conn;
    }

    public void createTables() {
        try {
            Class.forName("org.sqlite.JDBC");

            // SQL statement for creating a new table
            String sql = "CREATE TABLE IF NOT EXISTS cooldowns(" +
                    "UserID BIGINT NOT NULL," +
                    "Command TEXT NOT NULL," +
                    "Systime BIGINT NOT NULL," +
                    "Delay INT(11) NOT NULL);\n" +
                    "CREATE TABLE IF NOT EXISTS rewards(" +
                    "UserID BIGINT NOT NULL," +
                    "Type VARCHAR(16) NOT NULL," +
                    "Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL," +
                    "Amount INT NOT NULL);";

            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            if (!Lists.sqlite) {
                System.out.println("[ERROR] Couldn't create a database.");
                Lists.sqlite = true;
            }
        }
    }

    public void setupCooldowns(){
        try (Connection connection = getConnection();
             PreparedStatement ps = connection
                     .prepareStatement("SELECT * FROM `cooldowns` WHERE `UserID` = ?")) {

            for(long id: Lists.getUsers()) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                ArrayList<Cooldown> cooldowns = new ArrayList<>();
                while (rs.next())
                    cooldowns.add(new Cooldown(id, rs.getInt("Delay"),
                            rs.getLong("Systime"),
                            rs.getString("Command")));

                if(!cooldowns.isEmpty())
                    Lists.getCommandCooldown().put(id, cooldowns);
            }
        } catch (SQLException e) {
            System.out.printf("[ERROR] %s%n", e.getMessage());
        }
    }

    private boolean hasCooldown(Cooldown cooldown) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection
                     .prepareStatement("SELECT * FROM `cooldowns` WHERE `UserID` = ? AND `Command` = ?")) {

            ps.setLong(1, cooldown.getUserid());
            ps.setString(2, cooldown.getCommand());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.printf("[ERROR] %s%n", e.getMessage());
        }

        return false;
    }

    public void throwCooldown(Cooldown cooldown) {
        if (hasCooldown(cooldown))
            deleteCooldown(cooldown);

        try (Connection connection = getConnection();
             PreparedStatement ps = connection
                     .prepareStatement("INSERT INTO `cooldowns` (`UserID`, `Command`, `Systime`, `Delay`) VALUES (?, ?, ?, ?)")) {

            ps.setLong(1, cooldown.getUserid());
            ps.setString(2, cooldown.getCommand());
            ps.setLong(3, cooldown.getSystime());
            ps.setInt(4, cooldown.getDelay());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.printf("[ERROR] %s%n", e.getMessage());
        }
    }

    private void deleteCooldown(Cooldown cooldown) {
        if (!hasCooldown(cooldown))
            return;

        try (Connection connection = getConnection();
             PreparedStatement ps = connection
                     .prepareStatement("DELETE FROM `cooldowns` WHERE `UserID` = ? AND `Command` = ?")) {

            ps.setLong(1, cooldown.getUserid());
            ps.setString(2, cooldown.getCommand());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.printf("[ERROR] %s%n", e.getMessage());
        }
    }
}
