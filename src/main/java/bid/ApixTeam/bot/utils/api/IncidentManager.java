package bid.ApixTeam.bot.utils.api;

import bid.ApixTeam.bot.utils.vars.entites.Incident;
import bid.ApixTeam.bot.utils.vars.entites.enums.IncidentType;
import net.dv8tion.jda.core.entities.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class IncidentManager extends Incident {
    private static IncidentManager incidentManager = new IncidentManager();

    public static IncidentManager getIncidentManager() {
        return incidentManager;
    }

    private Connection getConnection() throws SQLException {
        return DatabaseManager.getDatabaseManager().getConnection();
    }

    public void createIncident(User Author, User user, IncidentType type, String reason, int delay, TimeUnit timeUnit){

    }
}
