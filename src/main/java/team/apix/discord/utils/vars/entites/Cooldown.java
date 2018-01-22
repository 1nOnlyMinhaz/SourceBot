package team.apix.discord.utils.vars.entites;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class Cooldown {
    private int delay;
    private long systime, userid;
    private String command = null;

    public Cooldown(long userid, int delay, long systime) {
        this.delay = delay;
        this.systime = systime;
    }

    public Cooldown(long userid, int delay, long systime, String command) {
        this.delay = delay;
        this.systime = systime;
        this.command = command;
    }

    public int getDelay() {
        return delay;
    }

    public long getSystime() {
        return systime;
    }

    public String getCommand() {
        return command;
    }

    public long getUserid() {
        return userid;
    }
}
