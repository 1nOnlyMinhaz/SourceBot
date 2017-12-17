package bid.ApixTeam.bot.utils.vars.entites;

import java.sql.Timestamp;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class Incident implements IIncident {
    private int id, delay;
    private Timestamp timestamp;
    private long u1id, u2id, systime;
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
    public int getDelay() {
        return delay;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected void setDelay(int delay) {
        this.delay = delay;
    }

    protected void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    protected void setU1id(long u1id) {
        this.u1id = u1id;
    }

    protected void setU2id(long u2id) {
        this.u2id = u2id;
    }

    protected void setSystime(long systime) {
        this.systime = systime;
    }

    protected void setType(String type) {
        this.type = type;
    }

    protected void setReason(String reason) {
        this.reason = reason;
    }

    protected void setRunning(boolean running) {
        this.running = running;
    }
}
