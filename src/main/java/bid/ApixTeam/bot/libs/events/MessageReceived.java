package bid.ApixTeam.bot.libs.events;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.enums.RankingType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Random;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class MessageReceived extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent e) {
        BotAPI botAPI = new BotAPI();
        User user = e.getAuthor();
        Message message = e.getMessage();

        if (!e.getChannel().getType().isGuild() || user.isBot() || message.getContent().startsWith("!"))
            return;

        if (Lists.getUserRankingCooldown().containsKey(user.getIdLong())){
            long seconds = ((Lists.getUserRankingCooldown().get(user.getIdLong()) / 1000) + 60) - (System.currentTimeMillis() / 1000);
            if(seconds > 0)
                return;
            else
                Lists.getUserRankingCooldown().remove(user.getIdLong());
        }else
            Lists.getUserRankingCooldown().put(user.getIdLong(), System.currentTimeMillis());

        botAPI.getDatabaseManager().createUserRanking(user);
        Random random = new Random();
        int exp = random.nextInt((25 - 15)) + 15;

        botAPI.getDatabaseManager().giveUserExp(user, exp);
        botAPI.getMessageManager().log(false, String.format("@%s#%s earned %d exp.", user.getName(), user.getDiscriminator(), exp));

        HashMap<RankingType, Integer> ranking = botAPI.getDatabaseManager().getUserRanking(user);
        if (Lists.getLevelsMaxExp().get(ranking.get(RankingType.LEVEL)) != null && ranking.get(RankingType.EXPERIENCE) >= Lists.getLevelsMaxExp().get(ranking.get(RankingType.LEVEL)))
            botAPI.getDatabaseManager().userLevelUp(user, exp);
    }
}
