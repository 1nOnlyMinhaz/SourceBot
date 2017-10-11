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
import java.util.concurrent.TimeUnit;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class MessageReceived extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent e) {
        BotAPI botAPI = new BotAPI();

        User user = e.getAuthor();
        Message message = e.getMessage();

        //botAPI.getMessageManager().log(false, String.format("\"%s\"", message.getContent()));

        if (Lists.getSlowmodeChannelCooldown().containsKey(e.getChannel().getIdLong())) {
            if (Lists.getSlowmodeUserCooldown().containsKey(e.getChannel().getIdLong() + ":" + user.getIdLong())) {
                long seconds = ((Lists.getSlowmodeUserCooldown().get(e.getChannel().getIdLong() + ":" + user.getIdLong()) / 1000) + Lists.getSlowmodeChannelCooldown().get(e.getChannel().getIdLong())) - (System.currentTimeMillis() / 1000);
                if (seconds > 0) {
                    botAPI.getMessageManager().deleteMessage(message);
                    return;
                } else
                    Lists.getSlowmodeUserCooldown().put(e.getChannel().getIdLong() + ":" + user.getIdLong(), System.currentTimeMillis());
            } else
                Lists.getSlowmodeUserCooldown().put(e.getChannel().getIdLong() + ":" + user.getIdLong(), System.currentTimeMillis());
        }

        if (!e.getTextChannel().isNSFW())
            for (String word : Lists.getProfanityList()) {
                if (message.getContent().toLowerCase().contains(word)) {
                    botAPI.getMessageManager().deleteMessage(message);
                    Message rebukeMessage = botAPI.getMessageManager().sendMessage(message.getChannel(), (String.format("**Language %s!!!** :rage:", message.getAuthor().getAsMention())));
                    botAPI.getMessageManager().deleteMessageAfter(rebukeMessage, 3L, TimeUnit.SECONDS);
                    return;
                }
            }

        if (!e.getChannel().getType().isGuild() || user.isBot() || message.getContent().startsWith("!"))
            return;

        if (!Lists.getUsers().contains(user.getIdLong()))
            botAPI.getPermissionManager().createMember(user);

        if (Lists.getUserRankingCooldown().containsKey(user.getIdLong())) {
            long seconds = ((Lists.getUserRankingCooldown().get(user.getIdLong()) / 1000) + 60) - (System.currentTimeMillis() / 1000);
            if (seconds > 0)
                return;
            else
                Lists.getUserRankingCooldown().remove(user.getIdLong());
        } else
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
