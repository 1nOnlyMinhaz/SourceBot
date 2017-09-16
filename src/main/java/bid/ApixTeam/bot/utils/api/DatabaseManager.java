package bid.ApixTeam.bot.utils.api;

import bid.ApixTeam.bot.utils.connection.DataSource;
import bid.ApixTeam.bot.utils.vars.RankingType;
import net.dv8tion.jda.core.entities.User;

import java.sql.*;
import java.util.HashMap;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class DatabaseManager {
    private static DatabaseManager databaseManager = new DatabaseManager();

    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    private Connection getConnection() throws SQLException {
        return DataSource.getConnection();
    }

    private boolean isInRanking(User user) {
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT `ID` FROM `rankings` WHERE `UserID` = ?");
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

    public void userLevelUp(User user, int exp){
        try {
            HashMap<RankingType, Integer> userRanking = getUserRanking(user);
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE `rankings` SET `experience` = ?, `level` = ? WHERE `UserID` = ?");
            ps.setInt(1, exp);
            ps.setInt(2, userRanking.get(RankingType.LEVEL) + 1);
            ps.setLong(3, user.getIdLong());
            ps.executeUpdate();
            closeConnection(connection, ps, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if (resultSet != null)
            resultSet.close();
        if (statement != null)
            statement.close();
        if (connection != null)
            connection.close();
    }
}
