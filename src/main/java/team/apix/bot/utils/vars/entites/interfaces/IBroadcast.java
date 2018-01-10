package team.apix.bot.utils.vars.entites.interfaces;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public interface IBroadcast {
    int getID();

    long getChannelID();

    String getMessage();

    int getDelay();
}
