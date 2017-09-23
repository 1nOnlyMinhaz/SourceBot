package bid.ApixTeam.bot.utils.api;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.enums.Settings;
import bid.ApixTeam.bot.utils.vars.enums.SimpleRank;
import net.dv8tion.jda.core.JDA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class SettingsManager extends DatabaseManager {
    protected static JDA jda;

    protected static void setup(JDA jda) {
        setJda(jda);
        try {
            Connection connection = getDatabaseManager().getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM `settings`");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Settings setting = Settings.getSetting(rs.getString("option"));
                Lists.getSettings().put(setting, rs.getString("value"));
                System.out.println(Lists.getSettings().toString());
            }
            getDatabaseManager().closeConnection(connection, ps, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        retrieveUsers();
    }

    private static void retrieveUsers() {
        try {
            Connection connection = getDatabaseManager().getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM `members`");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Lists.getUsers().add(rs.getLong("UserID"));
                if (rs.getString("Permission") != null || !rs.getString("Permission").isEmpty())
                    new BotAPI().getPermissionManager().setUserRank(rs.getLong("UserID"), SimpleRank.getRank(rs.getString("Permission")));
                System.out.println("retrieved " + rs.getString("Username"));
            }

            getDatabaseManager().closeConnection(connection, ps, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setJda(JDA jda) {
        SettingsManager.jda = jda;
    }
}
