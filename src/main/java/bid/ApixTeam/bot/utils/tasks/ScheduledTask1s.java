package bid.ApixTeam.bot.utils.tasks;

import bid.ApixTeam.bot.utils.vars.Lists;

import java.util.TimerTask;

/**
 * ApixBot was created by ApixTeam, ltd (C) 2017
 */
public class ScheduledTask1s extends TimerTask {
    @Override
    public void run() {
        synchronized (this) {
            if (Lists.getUserRankinCooldown().size() == 0 && Lists.getUserRankingCooldown().size() == 0)
                return;

            for (Long l : Lists.getUserRankingCooldown().keySet())
                if (Lists.getUserRankingCooldown().get(l) < 1) {
                    Lists.getUserRankingCooldown().remove(l);
                    Lists.getUserRankinCooldown().remove(l);
                } else
                    Lists.getUserRankingCooldown().put(l, Lists.getUserRankingCooldown().get(l) - 1);
        }
    }
}
