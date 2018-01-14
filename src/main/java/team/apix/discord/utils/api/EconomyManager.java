package team.apix.discord.utils.api;

import net.dv8tion.jda.core.entities.User;
import team.apix.discord.utils.vars.Lists;
import team.apix.discord.utils.vars.entites.Transactions;
import team.apix.discord.utils.vars.entites.enums.Settings;
import team.apix.discord.utils.vars.entites.enums.Transaction;

import java.sql.*;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class EconomyManager {
    private static EconomyManager economyManager = new EconomyManager();

    public static EconomyManager getEconomyManager() {
        return economyManager;
    }

    private Connection getConnection() throws SQLException {
        return DatabaseManager.getDatabaseManager().getConnection();
    }

    private void closeConnection(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        DatabaseManager.getDatabaseManager().closeConnection(connection, statement, resultSet);
    }

    private final String ECO_SINGLE = "coin";
    private final String ECO_PLURAL = "coins";

    public int getMaxBalance() {
        String setting = SettingsManager.getSettingsManager().getSetting(Settings.BALANCE_MAX);
        if (setting == null)
            return 1000000000;

        return Integer.parseInt(setting);
    }

    public String getFormattedBalance(int balance) {
        return balance == 1 ? String.format("%d %s", balance, ECO_SINGLE) : String.format("%d %s", balance, ECO_PLURAL);
    }

    public void setup() {
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT `UserID`, `Balance` FROM `members`");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                Lists.getUserBalance().put(rs.getLong("UserID"), rs.getInt("Balance"));

            ps = connection.prepareStatement("SELECT * FROM `transactions`");
            rs = ps.executeQuery();
            while (rs.next())
                Lists.getTransactions().put(rs.getInt("ID"), new Transactions(rs.getInt("ID"),
                        rs.getInt("Amount"), rs.getTimestamp("IssuedOn"),
                        rs.getLong("UserID"), Transaction.valueOf(rs.getString("Tyoe")),
                        rs.getString("Log")));

            ps = connection.prepareStatement("SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'transactions'");
            rs = ps.executeQuery();

            while (rs.next()) Lists.setLastTransaction(rs.getInt(1));
            closeConnection(connection, ps, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasBalance(User user) {
        return Lists.getUserBalance().containsKey(user.getIdLong());
    }

    public int getBalance(User user) {
        return Lists.getUserBalance().getOrDefault(user.getIdLong(), 0);
    }

    public Transaction setBalance(User user, int newBalance, Transaction transaction, String log) {
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE `members` SET `Balance` = ? WHERE `UserID` = ?");
            ps.setInt(1, newBalance);
            ps.setLong(2, user.getIdLong());
            int i = ps.executeUpdate();

            if (i == 0)
                return Transaction.TRANSACTION_FAIL;

            Lists.getUserBalance().put(user.getIdLong(), newBalance);
            closeConnection(connection, ps, null);
            issueTransaction(user, transaction != null ? transaction : Transaction.UNDEFINED, newBalance, log);
            return Transaction.TRANSACTION_SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Transaction.TRANSACTION_FAIL;
    }

    public Transaction deposit(User user, int amount, Transaction transaction, String log) {
        if (hasBalance(user))
            return Transaction.TRANSACTION_FAIL;

        int balance = getBalance(user);
        int newBalance = balance + amount;

        if(newBalance > getMaxBalance())
            return Transaction.TRANSACTION_FAIL;

        return setBalance(user, newBalance, transaction, log);
    }

    public Transaction withdraw(User user, int amount, Transaction transaction, String log) {
        if (hasBalance(user))
            return Transaction.TRANSACTION_FAIL;

        int balance = getBalance(user);
        int newBalance = balance - amount;

        if(newBalance < 0)
            return Transaction.TRANSACTION_FAIL;

        return setBalance(user, newBalance, transaction, log);
    }

    private void issueTransaction(User user, Transaction type, int amount, String log) {
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `transactions` (`UserID`, `Type`, 'Amount', 'Log') VALUES (?, ?, ?, ?)");
            ps.setLong(1, user.getIdLong());
            ps.setString(2, type.getString());
            ps.setInt(3, amount);
            ps.setString(4, log);

            ps.executeUpdate();
            closeConnection(connection, ps, null);
            Lists.setLastTransaction(Lists.getLastTransaction() + 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
