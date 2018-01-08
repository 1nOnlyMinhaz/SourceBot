package bid.ApixTeam.bot.utils.api;

import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.entites.Incident;
import bid.ApixTeam.bot.utils.vars.entites.enums.IncidentType;
import net.dv8tion.jda.core.entities.User;

import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;
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

            ps = connection.prepareStatement("SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'incidents'");
            rs = ps.executeQuery();

            while (rs.next()) Lists.setLastIncident(rs.getInt(1));

            closeConnection(connection, ps, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int createIncident(User author, User user, IncidentType type, String reason, int delay, TimeUnit timeUnit){
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `incidents` (`u1id`, `u2id`, `type`, `reason`, `delay`, `systime`) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setLong(1, author.getIdLong());
            ps.setLong(2, user.getIdLong());
            ps.setString(3, type.getString());
            ps.setString(4, reason);
            ps.setLong(5, timeUnit.toMillis(delay));
            ps.setLong(6, System.currentTimeMillis());

            ps.executeUpdate();
            closeConnection(connection, ps, null);

            Incident incident = new Incident();
            incident.setId(Lists.getLastIncident());
            incident.setU1id(author.getIdLong());
            incident.setU2id(user.getIdLong());
            incident.setType(type.toString());
            incident.setReason(reason);
            incident.setDelay(timeUnit.toMillis(delay));
            incident.setSystime(System.currentTimeMillis());
            incident.setTimestamp(new Timestamp(System.currentTimeMillis()));
            incident.setRunning(true);

            Lists.setLastIncident(Lists.getLastIncident() + 1);
            Lists.getIncidentLog().put(incident.getId(), incident);
            return incident.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void removeIncident(int id){
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM `incidents` WHERE `ID` = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            closeConnection(connection, ps, null);

            Lists.getIncidentLog().remove(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateIncident(Incident incident){
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE `incidents` SET `u1id` = ?, `u2id` = ?, `type` = ?, `reason` = ?, `systime` = ?, `delay` = ?, `running` = ?, `message` = ? WHERE `ID` = ?");
            ps.setLong(1, incident.getU1());
            ps.setLong(2, incident.getU2());
            ps.setString(3, incident.getType());
            ps.setString(4, incident.getReason());
            ps.setLong(5, incident.getSystime());
            ps.setLong(6, incident.getDelay());
            ps.setBoolean(7, incident.isRunning());
            ps.setLong(8, incident.getMessageID());
            ps.setInt(9, incident.getId());
            ps.executeUpdate();
            closeConnection(connection, ps, null);

            Lists.getIncidentLog().put(incident.getId(), incident);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void runIncident(int id, boolean b){
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE `incidents` SET `running` = ? WHERE `ID` = ?");
            ps.setBoolean(1, b);
            ps.setInt(2, id);
            ps.executeUpdate();
            closeConnection(connection, ps, null);

            Incident incident = Lists.getIncidentLog().get(id);
            incident.setRunning(b);

            Lists.getIncidentLog().put(incident.getId(), incident);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public void silentUpdate(int id, Incident incident){
        Lists.getIncidentLog().put(id, incident);
    }

    public ConcurrentHashMap<Integer, Incident> getIncidents(){
        return Lists.getIncidentLog();
    }

    public Incident getIncident(int id){
        return Lists.getIncidentLog().get(id);
    }
}
