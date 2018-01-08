package bid.ApixTeam.bot.utils.vars.entites;

import bid.ApixTeam.bot.utils.vars.entites.interfaces.IBroadcast;

public class Announcement implements IBroadcast {
    private int id;
    private long channelID;
    private int delay;
    private String message;

    @Override
    public int getID() {
        return id;
    }

    @Override
    public long getChannelID() {
        return channelID;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getDelay() {
        return delay;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChannelID(long channelID) {
        this.channelID = channelID;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setID(int id) {
        this.id = id;
    }
}
