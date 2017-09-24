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
    private static SettingsManager settingsManager = new SettingsManager();

    public static SettingsManager getSettingsManager() {
        return settingsManager;
    }

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

                if(rs.getString("option").startsWith("permission"))
                    Lists.getRolePermissions().put(Long.valueOf(rs.getString("value")), SimpleRank.valueOf(rs.getString("option").replace("permission-", "").replace("-", "_").toUpperCase()));
                System.out.println(Lists.getRolePermissions().toString());
            }
            getDatabaseManager().closeConnection(connection, ps, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        retrieveUsers();
    }

    private static void retrieveUsers() {
        BotAPI botAPI = new BotAPI();
        try {
            Connection connection = getDatabaseManager().getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM `members`");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Lists.getUsers().add(rs.getLong("UserID"));
                if (rs.getString("Permission") != null || !rs.getString("Permission").isEmpty())
                    botAPI.getPermissionManager().setUserRank(rs.getLong("UserID"), SimpleRank.getRank(rs.getString("Permission")));
                System.out.println("retrieved " + rs.getString("Username"));
            }

            getDatabaseManager().closeConnection(connection, ps, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkSettings(Settings settings){
        return Lists.getSettings().containsKey(settings);
    }

    public void addSettings(Settings settings, String value){
        if(checkSettings(settings)){
            updateSettings(settings, value);
            return;
        }

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `settings` (`option`, `value`) VALUES (?, ?)");
            ps.setString(1, settings.getOption());
            ps.setString(2, value);
            ps.executeUpdate();
            closeConnection(connection, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSettings(Settings settings, String value){
        if(!checkSettings(settings)){
            addSettings(settings, value);
            return;
        }

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE `settings` SET `value` = ? WHERE `option` = ?");
            ps.setString(1, value);
            ps.setString(2, settings.getOption());
            ps.executeUpdate();
            closeConnection(connection, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setJda(JDA jda) {
        SettingsManager.jda = jda;
    }
}
