package team.apix.discord.libs.events.guild;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.api.SettingsManager;
import team.apix.discord.utils.vars.Lists;
import team.apix.discord.utils.vars.Messages;
import team.apix.discord.utils.vars.entites.enums.RankingType;
import team.apix.discord.utils.vars.entites.enums.Settings;
import team.apix.discord.utils.vars.entites.enums.SimpleRank;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class MessageReceived extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        BotAPI botAPI = new BotAPI();

        User user = e.getAuthor();
        Message message = e.getMessage();
        SettingsManager sm = botAPI.getSettingsManager();

        if (sm.isSet(Settings.CHAN_SCHANGELOG) && sm.getSetting(Settings.CHAN_SCHANGELOG).equals(e.getChannel().getId()) && botAPI.getPermissionManager().userRoleAtLeast(e.getMember(), SimpleRank.ADMIN)) {
            message.addReaction("✅").queue();
            message.addReaction("❌").queue();
        }

        if (!e.getChannel().getType().isGuild() || user.isBot())
            return;

        /**  SLOWMODE   */
        if (Lists.getSlowmodeChannelCooldown().containsKey(e.getChannel().getIdLong()) && !botAPI.getPermissionManager().userRoleAtLeast(e.getMember(), SimpleRank.MOD)) {
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

        /**  PROFANITY  */
        String prof = botAPI.getSettingsManager().getSetting(Settings.PROFANITY_LIST);
        if (!e.getChannel().isNSFW() && prof != null) {
            String[] profanity = prof.split(",");
            for (String word : profanity) {
                if (message.getContentRaw().toLowerCase().contains(word)) {
                    botAPI.getMessageManager().deleteMessage(message);
                    Message rebukeMessage = botAPI.getMessageManager().sendMessage(message.getChannel(), String.format(Messages.PROFANITY[new Random().nextInt(Messages.PROFANITY.length)], message.getAuthor().getAsMention()));
                    botAPI.getMessageManager().deleteMessageAfter(rebukeMessage, 5L, TimeUnit.SECONDS);
                    return;
                }
            }
        }

        /** COMMAND CHECKER   */
        if (message.getContentRaw().startsWith("!"))
            for (String s : Lists.getCommands())
                if (message.getContentRaw().startsWith(String.format("!%s", s)))
                    return;

        if (!Lists.getUsers().contains(user.getIdLong())) botAPI.getPermissionManager().createMember(user);

        if (botAPI.getSettingsManager().getSetting(Settings.RANKED_IGNORED) != null && e.getChannel().getId().equals(botAPI.getSettingsManager().getSetting(Settings.RANKED_IGNORED)))
            return;

        if (e.getMember().getRoles().contains(e.getGuild().getRoleById(botAPI.getSettingsManager().getSetting(Settings.ROLES_MUTED))))
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

        if (!Lists.getUserRankingCooldown().containsKey(user.getIdLong()))
            Lists.getUserRankingCooldown().put(user.getIdLong(), System.currentTimeMillis());

        botAPI.getDatabaseManager().giveUserExp(user, exp);
        if (Lists.isTestingEnvironment())
            botAPI.getMessageManager().log(false, String.format("@%s#%s earned %d exp.", user.getName(), user.getDiscriminator(), exp));

        HashMap<RankingType, Integer> ranking = botAPI.getDatabaseManager().getUserRanking(user);
        if (Lists.getLevelsMaxExp().get(ranking.get(RankingType.LEVEL)) != null && ranking.get(RankingType.EXPERIENCE) >= Lists.getLevelsMaxExp().get(ranking.get(RankingType.LEVEL)))
            botAPI.getDatabaseManager().userLevelUp(user, botAPI.getEcon(), ranking, exp);

        botAPI.getDatabaseManager().userRankUp(e.getGuild(), user, botAPI.getSettingsManager());
    }
}
