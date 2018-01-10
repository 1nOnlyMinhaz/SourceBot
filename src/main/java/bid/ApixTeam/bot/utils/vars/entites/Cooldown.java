package bid.ApixTeam.bot.utils.vars.entites;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class Cooldown {
    private int delay;
    private long systime;
    private String command = null;

    public Cooldown(int delay, long systime) {
        this.delay = delay;
        this.systime = systime;
    }

    public Cooldown(int delay, long systime, String command) {
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
}
