package team.apix.discord.utils.api;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.connection.MySQL;
import team.apix.discord.utils.vars.entites.enums.RankingType;
import team.apix.discord.utils.vars.entites.enums.Settings;
import team.apix.discord.utils.vars.entites.enums.Transaction;

import java.sql.*;
import java.util.HashMap;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class DatabaseManager {
    private static DatabaseManager databaseManager = new DatabaseManager();

    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    Connection getConnection() throws SQLException {
        return MySQL.getConnection();
    }

    private boolean isInRanking(User user) {
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT `UserID` FROM `rankings` WHERE `UserID` = ?");
            ps.setLong(1, user.getIdLong());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                closeConnection(connection, ps, rs);
                return true;
            }

            closeConnection(connection, ps, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean isInRanking(Long id) {
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT `ID` FROM `rankings` WHERE `UserID` = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                closeConnection(connection, ps, rs);
                return true;
            }

            closeConnection(connection, ps, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void createUserRanking(User user) {
        if (isInRanking(user))
            return;

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `rankings` (`UserID`) VALUES (?)");
            ps.setLong(1, user.getIdLong());
            ps.executeUpdate();
            closeConnection(connection, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUserRanking(Long id) {
        if (isInRanking(id))
            return;

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `rankings` (`UserID`) VALUES (?)");
            ps.setLong(1, id);
            ps.executeUpdate();
            closeConnection(connection, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUserPreExistingRanking(Long id, int lvl, int xp, int total_xp) {
        if (isInRanking(id))
            return;

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `rankings` (`UserID`, `level`, `experience`, `TotalExp`) VALUES (?, ?, ?, ?)");
            ps.setLong(1, id);
            ps.setInt(2, lvl);
            ps.setInt(3, xp);
            ps.setInt(4, total_xp);
            ps.executeUpdate();
            closeConnection(connection, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashMap<RankingType, Integer> getUserRanking(User user) {
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT `level`, `experience`, `TotalExp` FROM `rankings` WHERE `UserID` = ?");
            ps.setLong(1, user.getIdLong());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                HashMap<RankingType, Integer> ranking = new HashMap<>();
                ranking.put(RankingType.LEVEL, rs.getInt("level"));
                ranking.put(RankingType.EXPERIENCE, rs.getInt("experience"));
                ranking.put(RankingType.TOTAL_EXPERIENCE, rs.getInt("TotalExp"));
                closeConnection(connection, ps, rs);
                return ranking;
            } else
                closeConnection(connection, ps, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public HashMap<Long, HashMap<RankingType, Integer>> getUsersRanking(int limit) {
        HashMap<Long, HashMap<RankingType, Integer>> rankings = new HashMap<>();
        try {
            Connection connection = getConnection();
            connection.prepareStatement("SET @rank=0;").executeUpdate();
            PreparedStatement ps = connection.prepareStatement("SELECT @rank:=@rank+1 AS rank, `UserID`, `level`, `experience`, `TotalExp` FROM `rankings` ORDER BY `level` DESC , `experience` DESC LIMIT ?");
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HashMap<RankingType, Integer> ranking = new HashMap<>();
                ranking.put(RankingType.RANK, rs.getInt("rank"));
                ranking.put(RankingType.LEVEL, rs.getInt("level"));
                ranking.put(RankingType.EXPERIENCE, rs.getInt("experience"));
                ranking.put(RankingType.TOTAL_EXPERIENCE, rs.getInt("TotalExp"));
                rankings.put(rs.getLong("UserID"), ranking);
            }
            return rankings;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    HashMap<RankingType, Integer> getUserRankings(User user) {
        if (!isInRanking(user))
            return null;

        HashMap<RankingType, Integer> userRanking = new HashMap<>();
        try {
            Connection connection = getConnection();
            connection.prepareStatement("SET @rank=0;").executeUpdate();
            PreparedStatement ps = connection.prepareStatement("SELECT rank, `UserID`, `level`, `experience`, `TotalExp` FROM " +
                    "(SELECT @rank:=@rank+1 AS rank, `UserID`, `level`, `experience`, `TotalExp` FROM `rankings` ORDER BY `level` DESC, `experience` DESC) A " +
                    "WHERE `UserID` = ?;");
            ps.setLong(1, user.getIdLong());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userRanking.put(RankingType.RANK, rs.getInt("rank"));
                userRanking.put(RankingType.LEVEL, rs.getInt("level"));
                userRanking.put(RankingType.EXPERIENCE, rs.getInt("experience"));
                userRanking.put(RankingType.TOTAL_EXPERIENCE, rs.getInt("TotalExp"));
                closeConnection(connection, ps, rs);
                return userRanking;
            } else
                closeConnection(connection, ps, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void giveUserExp(User user, int amount) {
        try {
            HashMap<RankingType, Integer> userRanking = getUserRanking(user);
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE `rankings` SET `experience` = ?, `TotalExp` = ? WHERE `UserID` = ?");
            ps.setInt(1, userRanking.get(RankingType.EXPERIENCE) + amount);
            ps.setInt(2, userRanking.get(RankingType.TOTAL_EXPERIENCE) + amount);
            ps.setLong(3, user.getIdLong());
            ps.executeUpdate();
            closeConnection(connection, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetUserRanking(User user) {
        if (!isInRanking(user))
            return;

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM `rankings` WHERE `UserID` = ?");
            ps.setLong(1, user.getIdLong());
            ps.executeUpdate();
            closeConnection(connection, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void userLevelUp(User user, EconomyManager eco, HashMap<RankingType, Integer> userRanking, int exp) {
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE `rankings` SET `experience` = ?, `level` = ? WHERE `UserID` = ?");
            ps.setInt(1, exp);
            ps.setInt(2, userRanking.get(RankingType.LEVEL) + 1);
            ps.setLong(3, user.getIdLong());
            ps.executeUpdate();
            closeConnection(connection, ps, null);
            eco.deposit(user, 2, Transaction.REWARD, "Leveled up");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void userRankUp(Guild guild, User user, SettingsManager sm) {
        if (!sm.isSet(Settings.RANKED_REWARDS))
            return;

        HashMap<RankingType, Integer> userRanking = getUserRanking(user);

        String rewards = sm.getSetting(Settings.RANKED_REWARDS);
        String[] s = rewards.split(",");
        for (String st : s) {
            String[] stt = st.split("-");
            if (!guild.getMember(user).getRoles().contains(guild.getRoleById(stt[1])) && (userRanking.get(RankingType.LEVEL)) >= Integer.parseInt(stt[0]))
                guild.getController().addSingleRoleToMember(guild.getMember(user), guild.getRoleById(stt[1])).queue();
        }
    }

    public void usersSyncCoins(Guild guild, BotAPI botAPI, EconomyManager eco) {
        HashMap<Long, HashMap<RankingType, Integer>> rankings = getUsersRanking(1000);
        HashMap<RankingType, Integer> userRanking;

        for (Long userID : rankings.keySet()) {
            userRanking = rankings.get(userID);
            int balance;
            int lvl = userRanking.get(RankingType.LEVEL);
            if (lvl == 0)
                continue;

            balance = lvl * 2;
            Member member = guild.getMemberById(userID);
            if (member == null)
                continue;

            if (eco.deposit(member.getUser(), balance, Transaction.REWARD, "Calculated coins for previous levels.") == Transaction.TRANSACTION_SUCCESS)
                botAPI.getMessageManager().log(false, String.format("Rewarded @%s#%s with %d coins (lvl. %d)", member.getUser().getName(), member.getUser().getDiscriminator(), balance, userRanking.get(RankingType.LEVEL)));
            else
                botAPI.getMessageManager().log(false, String.format("Failed to reward @%s#%s with %d coins (lvl. %d)", member.getUser().getName(), member.getUser().getDiscriminator(), balance, userRanking.get(RankingType.LEVEL)));
        }
    }

    void closeConnection(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if (resultSet != null)
            resultSet.close();
        if (statement != null)
            statement.close();
        if (connection != null)
            connection.close();
    }
}
