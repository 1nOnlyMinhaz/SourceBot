package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import bid.ApixTeam.bot.utils.api.PermissionManager;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.enums.Settings;
import bid.ApixTeam.bot.utils.vars.enums.SimpleRank;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class ComHelp implements CommandExecutor {
    @Command(aliases = {"help"}, description = "displays the available commands")
    public void onCommand(User user, MessageChannel messageChannel, Message message, String[] strings) {
        BotAPI botAPI = new BotAPI();
        EmbedMessageManager embedManager = botAPI.getEmbedMessageManager();
        PermissionManager pm = botAPI.getPermissionManager();
        boolean jrmod = false, srmod = false, jradmin = false, sradmin = false;
        boolean b = messageChannel.getType().isGuild();

        if (pm.userRoleAtLeast(user.getJDA().getGuildById(Lists.getSettings().get(Settings.MAIN_GUILD_ID)).getMember(user), SimpleRank.JR_MOD))
            jrmod = true;
        if (pm.userRoleAtLeast(user.getJDA().getGuildById(Lists.getSettings().get(Settings.MAIN_GUILD_ID)).getMember(user), SimpleRank.SR_MOD))
            srmod = true;
        if (pm.userRoleAtLeast(user.getJDA().getGuildById(Lists.getSettings().get(Settings.MAIN_GUILD_ID)).getMember(user), SimpleRank.JR_ADMIN))
            jradmin = true;
        if (pm.userRoleAtLeast(user.getJDA().getGuildById(Lists.getSettings().get(Settings.MAIN_GUILD_ID)).getMember(user), SimpleRank.SR_ADMIN))
            sradmin = true;

        try {
            if (strings.length == 0) {
                send(botAPI, user, embedManager.getDefaultHelpEmbed(user));
                if (jrmod)
                    send(botAPI, user, embedManager.getJrModHelpEmbed(user));
                if (srmod)
                    send(botAPI, user, embedManager.getSrModHelpEmbed(user));
                if (jradmin)
                    send(botAPI, user, embedManager.getJrAdminHelpEmbed(user));
                if (sradmin)
                    send(botAPI, user, embedManager.getSrAdminHelpEmbed(user));

                if (b)
                    botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription(":white_check_mark: *sent you some help*.. please check your PMs."));
            } else if (strings.length == 1) {
                if (strings[0].equalsIgnoreCase("broadcast") && jradmin)
                    send(botAPI, user, embedManager.getUsage(user, "broadcast", "Announces/broadcasts a message to a certain channel, can be executed once or placed in a loop.", "broadcast (Now|Later|Repeat) [L|R Time] [L|R Unit] (#Channel) (Message)", "!announce\n!bc"));

                if (b)
                    botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription(":white_check_mark: *sent you some help*.. please check your PMs."));
            } else
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getUsage("!help"));
        } catch (ErrorResponseException e) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("Umm :cold_sweat: couldn't send you help :sob: maybe it's because your PMs are locked!"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void send(BotAPI botAPI, User user, MessageEmbed messageEmbed) {
        botAPI.getPrivateMessageManager().sendMessage(user, messageEmbed);
    }
}
