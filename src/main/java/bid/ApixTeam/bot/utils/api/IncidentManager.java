package bid.ApixTeam.bot.utils.api;

import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.entites.Incident;
import bid.ApixTeam.bot.utils.vars.entites.enums.IncidentType;
import net.dv8tion.jda.core.entities.User;

import java.sql.*;
import java.util.concurrent.TimeUnit;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class IncidentManager {
    private static IncidentManager incidentManager = new IncidentManager();

    public static IncidentManager getIncidentManager() {
        return incidentManager;
    }

    private Connection getConnection() throws SQLException {
        return DatabaseManager.getDatabaseManager().getConnection();
    }

    private void closeConnection(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        DatabaseManager.getDatabaseManager().closeConnection(connection, statement, resultSet);
    }

    public void setup(){
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM `incidents`");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Incident incident = new Incident();
                incident.setRunning(rs.getBoolean("running"));
                incident.setTimestamp(rs.getTimestamp("time_stamp"));
                incident.setDelay(rs.getInt("delay"));
                incident.setSystime(rs.getLong("systime"));
                incident.setType(rs.getString("type"));
                incident.setId(rs.getInt("ID"));
                incident.setReason(rs.getString("reason"));
                incident.setU1id(rs.getLong("u1id"));
                incident.setU2id(rs.getLong("u2id"));

                Lists.getIncidentLog().put(incident.getId(), incident);
            }

            ps = connection.prepareStatement("SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'tsc_discord' AND TABLE_NAME = 'incidents'");
            rs = ps.executeQuery();

            while (rs.next()) Lists.setLastIncident(rs.getInt(1));

            closeConnection(connection, ps, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createIncident(User Author, User user, IncidentType type, String reason, int delay, TimeUnit timeUnit){

    }
}
