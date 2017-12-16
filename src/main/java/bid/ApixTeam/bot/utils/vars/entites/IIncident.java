package bid.ApixTeam.bot.utils.vars.entites;

import java.sql.Timestamp;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public interface IIncident {
    int id = 0;

    Timestamp timestamp = null;

    String type = null;

    long u1id = 0L;

    long u2id = 0L;

    String reason = null;

    long systime = 0L;

    int delay = 0;

    boolean running = false;

    int getId();

    Timestamp getTimestamp();

    String getType();

    long getU1();

    long getU2();

    String getReason();

    long getSystime();

    int getDelay();

    boolean isRunning();
}
