package team.apix.discord.utils.vars.entites;

import team.apix.discord.utils.vars.entites.interfaces.IBroadcast;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
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
