package bid.ApixTeam.bot.utils.vars.entites;

import java.util.concurrent.TimeUnit;

public class Announcement implements IBroadcast {
    private int id;
    private long channelID, delay;
    private String message, timeUnitt;
    private TimeUnit timeUnit;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public long getChannelID() {
        return channelID;
    }

    public void setChannelID(long channelID) {
        this.channelID = channelID;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Override
    public String getTimeUnitt() {
        return timeUnitt;
    }

    public void setTimeUnitt(String timeUnitt) {
        this.timeUnitt = timeUnitt;
    }
}
