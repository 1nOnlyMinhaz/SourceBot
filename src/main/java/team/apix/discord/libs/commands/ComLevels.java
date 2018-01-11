package team.apix.discord.libs.commands;

import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.api.EmbedMessageManager;
import team.apix.discord.utils.api.ExtraUtils;
import team.apix.discord.utils.api.PermissionManager;
import team.apix.discord.utils.api.SettingsManager;
import team.apix.discord.utils.vars.entites.enums.Settings;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class ComLevels implements CommandExecutor {
    @Command(aliases = {"ranks", "levels", "leaderboard"}, async = true)
    public void onCommand(User user, MessageChannel messageChannel, Message message, Object[] objects) {
        BotAPI botAPI = new BotAPI();
        PermissionManager pm = botAPI.getPermissionManager();
        ExtraUtils eu = botAPI.getExtraUtils();
        String command = "leaderboard";

        if(eu.isCoolingdown(user, command)){
            botAPI.getMessageManager().sendMessage(messageChannel, eu.getCooldownMessage(user, command));
            return;
        }else
            eu.throwCooldown(user, pm, command, 600);

        EmbedMessageManager embedManager = botAPI.getEmbedMessageManager();
        SettingsManager sm = botAPI.getSettingsManager();

        if ((sm.getSetting(Settings.CHAN_RANK_CHECK) != null && !sm.getSetting(Settings.CHAN_RANK_CHECK).equals(messageChannel.getId()) && messageChannel.getType().isGuild())
                && (sm.getSetting(Settings.CHAN_RANK_CHECK) != null && !sm.getSetting(Settings.CHAN_ADMIN).equals(messageChannel.getId()) && messageChannel.getType().isGuild()))
            return;

        if (messageChannel.getType().isGuild()) {
            if (objects.length == 0)
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getRanksEmbed(botAPI, user));
            else
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getUsage("!levels"));
        } else {
            if (objects.length == 0)
                botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getRanksEmbed(botAPI, user));
            else
                botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getUsage("!levels"));
        }
    }
}