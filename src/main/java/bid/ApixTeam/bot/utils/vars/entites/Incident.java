package bid.ApixTeam.bot.utils.vars.entites;

import java.sql.Timestamp;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class Incident implements IIncident {
    private int id;
    private Timestamp timestamp;
    private long u1id, u2id, systime, delay, messageID;
    private String type, reason;
    private boolean running;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public long getU1() {
        return u1id;
    }

    @Override
    public long getU2() {
        return u2id;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public long getSystime() {
        return systime;
    }

    @Override
    public long getDelay() {
        return delay;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public long getMessageID(){
        return messageID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setU1id(long u1id) {
        this.u1id = u1id;
    }

    public void setU2id(long u2id) {
        this.u2id = u2id;
    }

    public void setSystime(long systime) {
        this.systime = systime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }
}
