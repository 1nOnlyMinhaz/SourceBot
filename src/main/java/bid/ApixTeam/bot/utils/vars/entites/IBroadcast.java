package bid.ApixTeam.bot.utils.vars.entites;

import java.util.concurrent.TimeUnit;

public interface IBroadcast {
    int getID();

    long getChannelID();

    String getMessage();

    long getDelay();

    TimeUnit getTimeUnit();

    String getTimeUnitt();
}
