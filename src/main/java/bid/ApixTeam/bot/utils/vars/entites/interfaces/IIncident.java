package bid.ApixTeam.bot.utils.vars.entites.interfaces;

import java.sql.Timestamp;

/**
 * Source-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public interface IIncident {

    int getId();

    Timestamp getTimestamp();

    String getType();

    long getU1();

    long getU2();

    String getReason();

    long getSystime();

    long getDelay();

    boolean isRunning();

    long getMessageID();
}
