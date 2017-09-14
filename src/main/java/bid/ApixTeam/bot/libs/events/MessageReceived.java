package bid.ApixTeam.bot.libs.events;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.RankingType;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class MessageReceived extends ListenerAdapter {
    public void onMessageReceieved(MessageReceivedEvent e){
        BotAPI botAPI = new BotAPI();
        User user = e.getAuthor();
        Message message = e.getMessage();

        if(e.getTextChannel().getType() == ChannelType.PRIVATE || user.isBot())
            return;

        if (Lists.getUserRankingCooldown().containsKey(user.getIdLong()) || Lists.getUserRankinCooldown().contains(user.getIdLong()))
            return;

        botAPI.getDatabaseManager().createUserRanking(user);
        Random random = new Random();
        int exp = random.nextInt((25 - 15)) + 15;

        botAPI.getDatabaseManager().giveUserExp(user, exp);
        botAPI.getMessageManager().logInfo(String.format("[%s] [INFO] @%s#%s earned %d exp.", new Date(),user.getName(), user.getDiscriminator(), exp));

        HashMap<RankingType, Integer> ranking = botAPI.getDatabaseManager().getUserRanking(user);
        if (Lists.getLevelsMaxExp().get(ranking.get(RankingType.LEVEL)) != null && ranking.get(RankingType.EXPERIENCE) >= Lists.getLevelsMaxExp().get(ranking.get(RankingType.LEVEL)))
            botAPI.getDatabaseManager().userLevelUp(user);

        Lists.getUserRankinCooldown().add(user.getIdLong());
        Lists.getUserRankingCooldown().put(user.getIdLong(), 60);
    }
}
