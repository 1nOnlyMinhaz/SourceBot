package team.apix.bot.utils.vars.entites.interfaces;

import java.sql.Timestamp;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
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
