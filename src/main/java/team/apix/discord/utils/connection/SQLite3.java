package team.apix.discord.utils.connection;

import java.sql.*;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class SQLite3 {
    public void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = String.format("jdbc:sqlite:%s/db/logger.db", System.getProperty("user.dir"));
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public Connection getConnection() throws SQLException {
        // SQLite connection string
        String url = String.format("jdbc:sqlite:%s/db/logger.db", System.getProperty("user.dir"));

        Connection conn = null;
        conn = DriverManager.getConnection(url);
        return conn;
    }

    public void createTables() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS messages (\n"
                + "	id BIGINT PRIMARY KEY,\n"
                + "	userid BIGINT NOT NULL,\n"
                + "	sentOn TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n"
                + "	originalMessage LONGTEXT NOT NULL,\n"
                + "	updatedMessage1 LONGTEXT,\n"
                + "	updatedMessage2 LONGTEXT,\n"
                + "	lastUpdate TIMESTAMP,\n"
                + "	isDeleted BOOLEAN DEFAULT FALSE,\n"
                + "	deletedBy BIGINT\n"
                + ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAll(){
        String sql = "SELECT * FROM messages";

        try (Connection conn = this.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getLong("id") +  "\t" +
                        rs.getLong("userid") + "\t" +
                        rs.getTimestamp("sentOn") + "\t" +
                        rs.getString("originalMessage") + "\t" +
                        rs.getString("updatedMessage1") + "\t" +
                        rs.getString("updatedMessage2") + "\t" +
                        rs.getTimestamp("lastUpdate") + "\t" +
                        rs.getBoolean("isDeleted") + "\t" +
                        rs.getLong("deletedBy"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getMessage(Long id) {
        String sql = "SELECT * from messages WHERE id = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return String.format("%d//-apixsource-//%s//-apixsource-//" +
                        "%s//-apixsource-//%s//-apixsource-//%s//-apixsource-//" +
                        "%s//-apixsource-//%b//-apixsource-//%d",
                        rs.getLong("userid"), rs.getString("sentOn"),
                        rs.getString("originalMessage"), rs.getString("updatedMessage1"),
                        rs.getString("updatedMessage2"), rs.getString("lastUpdate"),
                        rs.getBoolean("isDeleted"), rs.getLong("deletedBy"));

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void logMessage(Long id, Long userid, String message) {
        String sql = "INSERT INTO messages (id, userid, originalMessage) VALUES(?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.setLong(2, userid);
            ps.setString(3, message);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateMessage1(Long id, String edit) {
        String sql = "UPDATE messages SET updatedMessage1 = ?, lastUpdate = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, edit);
            ps.setLong(2, id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateMessage2(Long id, String edit) {
        String sql = "UPDATE messages SET updatedMessage2 = ?, lastUpdate = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, edit);
            ps.setLong(2, id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteMessage(Long id, Long userid) {
        String sql = "UPDATE messages SET isDeleted = ?, deletedBy = ?, lastUpdate = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, true);
            ps.setLong(2, userid);
            ps.setLong(3, id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
