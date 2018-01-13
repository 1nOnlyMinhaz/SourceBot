package team.apix.discord.utils.vars.entites;

import team.apix.discord.utils.vars.entites.enums.Transaction;

import java.sql.Timestamp;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class Transactions {
    private int id, amount;
    private Timestamp timestamp;
    private long userId;
    private Transaction type;
    private String log;

    public Transactions(int id, int amount, Timestamp timestamp, long userId, Transaction type, String log) {
        this.id = id;
        this.amount = amount;
        this.timestamp = timestamp;
        this.userId = userId;
        this.type = type;
        this.log = log;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    private void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    private void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public long getUserId() {
        return userId;
    }

    private void setUserId(long userId) {
        this.userId = userId;
    }

    public Transaction getType() {
        return type;
    }

    private void setType(Transaction type) {
        this.type = type;
    }

    public String getLog() {
        return log;
    }

    private void setLog(String log) {
        this.log = log;
    }
}
