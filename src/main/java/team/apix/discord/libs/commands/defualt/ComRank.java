package team.apix.discord.libs.commands.defualt;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.api.EmbedMessageManager;
import team.apix.discord.utils.api.ExtraUtils;
import team.apix.discord.utils.api.SettingsManager;
import team.apix.discord.utils.vars.entites.enums.Settings;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class ComRank implements CommandExecutor {
    @Command(aliases = {"rank", "level"}, async = true)
    public void onCommand(User user, MessageChannel messageChannel, Message message, Object[] objects) {
        BotAPI botAPI = new BotAPI();
        ExtraUtils eu = botAPI.getExtraUtils();
        String command = "rank";

        EmbedMessageManager embedManager = botAPI.getEmbedMessageManager();
        SettingsManager sm = botAPI.getSettingsManager();

        if (eu.isntInChannel(messageChannel, sm, Settings.CHAN_RANK_CHECK))
            return;

        if (messageChannel.getType().isGuild()) {
            if (objects.length == 0) {
                if (eu.cooldown(botAPI, messageChannel, user, command, 900))
                    return;

                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getRankEmbed(botAPI, user));
            } else if (message.getMentionedUsers().size() == 1) {
                if (message.getMentionedUsers().get(0) == user) {
                    if (eu.cooldown(botAPI, messageChannel, user, command, 900))
                        return;

                    botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getRankEmbed(botAPI, user));
                    return;
                }
                if (eu.cooldown(botAPI, messageChannel, user, String.format("%s|mention", command), 900))
                    return;

                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getRankEmbed(botAPI, message.getMentionedUsers().get(0)));
            } else
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getUsage("!rank [@mention]"));
        } else {
            if (objects.length == 0) {
                if (eu.cooldown(botAPI, messageChannel, user, command, 900))
                    return;

                botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getRankEmbed(botAPI, user));
            } else
                botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getAsDescription(String.format("yeah.... i think you'd actually need to do that on the **%s** server.",
                        user.getJDA().getGuildById(sm.getSetting(Settings.MAIN_GUILD_ID)).getName())));
        }
    }
}