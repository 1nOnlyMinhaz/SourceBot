package bid.ApixTeam.bot.libs.events;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.Messages;
import bid.ApixTeam.bot.utils.vars.entites.enums.RankingType;
import bid.ApixTeam.bot.utils.vars.entites.enums.Settings;
import bid.ApixTeam.bot.utils.vars.entites.enums.SimpleRank;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
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

        if (!e.getChannel().getType().isGuild() || user.isBot())
            return;

        if(Lists.getSlowmodeChannelCooldown().containsKey(e.getChannel().getIdLong()) && !botAPI.getPermissionManager().userAtLeast(user, SimpleRank.MOD)) {
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

        String prof = botAPI.getSettingsManager().getSetting(Settings.PROFANITY_LIST);
        if (!e.getTextChannel().isNSFW() && prof != null) {
            String[] profanity = prof.split(",");
            for (String word : profanity){
                if (message.getContentRaw().toLowerCase().contains(word)) {
                    botAPI.getMessageManager().deleteMessage(message);
                    Message rebukeMessage = botAPI.getMessageManager().sendMessage(message.getChannel(), String.format(Messages.PROFANITY[new Random().nextInt(Messages.PROFANITY.length)], message.getAuthor().getAsMention()));
                    botAPI.getMessageManager().deleteMessageAfter(rebukeMessage, 5L, TimeUnit.SECONDS);
                    return;
                }
            }
        }

        if (message.getContentRaw().startsWith("!"))
            for (String s : Lists.getCommands())
                if (message.getContentRaw().startsWith(String.format("!%s", s)))
                    return;

        if (!Lists.getUsers().contains(user.getIdLong())) botAPI.getPermissionManager().createMember(user);

        if(botAPI.getSettingsManager().getSetting(Settings.RANKED_IGNORED) != null && e.getTextChannel().getId().equals(botAPI.getSettingsManager().getSetting(Settings.RANKED_IGNORED)))
            return;

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

        if(!Lists.getUserRankingCooldown().containsKey(user.getIdLong()))
            Lists.getUserRankingCooldown().put(user.getIdLong(), System.currentTimeMillis());

        botAPI.getDatabaseManager().giveUserExp(user, exp);
        botAPI.getMessageManager().log(false, String.format("@%s#%s earned %d exp.", user.getName(), user.getDiscriminator(), exp));

        HashMap<RankingType, Integer> ranking = botAPI.getDatabaseManager().getUserRanking(user);
        if (Lists.getLevelsMaxExp().get(ranking.get(RankingType.LEVEL)) != null && ranking.get(RankingType.EXPERIENCE) >= Lists.getLevelsMaxExp().get(ranking.get(RankingType.LEVEL)))
            botAPI.getDatabaseManager().userLevelUp(user, ranking, exp);

        botAPI.getDatabaseManager().userRankUp(e.getGuild(), user, botAPI.getSettingsManager());
    }
}
