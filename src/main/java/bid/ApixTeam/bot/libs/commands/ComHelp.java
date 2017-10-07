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
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class ComHelp implements CommandExecutor {
    @Command(aliases = {"help", "commands"}, description = "displays the available commands")
    public void onCommand(User user, MessageChannel messageChannel, Message message, Object[] objects) {
        BotAPI botAPI = new BotAPI();
        EmbedMessageManager embedManager = botAPI.getEmbedMessageManager();
        PermissionManager pm = botAPI.getPermissionManager();
        boolean jrmod = false, srmod = false, jradmin = false, sradmin = false;

        if(pm.userRoleAtLeast(user.getJDA().getGuildById(Lists.getSettings().get(Settings.MAIN_GUILD_ID)).getMember(user), SimpleRank.JR_MOD))
            jrmod = true;
        if(pm.userRoleAtLeast(user.getJDA().getGuildById(Lists.getSettings().get(Settings.MAIN_GUILD_ID)).getMember(user), SimpleRank.SR_MOD))
            srmod = true;
        if(pm.userRoleAtLeast(user.getJDA().getGuildById(Lists.getSettings().get(Settings.MAIN_GUILD_ID)).getMember(user), SimpleRank.JR_ADMIN))
            jradmin = true;
        if(pm.userRoleAtLeast(user.getJDA().getGuildById(Lists.getSettings().get(Settings.MAIN_GUILD_ID)).getMember(user), SimpleRank.SR_ADMIN))
            sradmin = true;

        if(objects.length != 0) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getUsage("!help"));
            return;
        }

        try {
            if(messageChannel.getType().isGuild()) {
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription(":white_check_mark: *sent you some help*.. please check your PMs."));
                botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getDefaultHelpEmbed(user));
                if(jrmod)
                    botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getJrModHelpEmbed(user));
                if(srmod)
                    botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getSrModHelpEmbed(user));
                //if(jradmin)
                //    botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getJrAdminHelpEmbed(user));
                //if(sradmin)
                //    botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getSrAdminHelpEmbed(user));
            } else {
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getDefaultHelpEmbed(user));
                if(jrmod)
                    botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getJrModHelpEmbed(user));
                if(srmod)
                    botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getSrModHelpEmbed(user));
                //if(jradmin)
                //    botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getJrAdminHelpEmbed(user));
                //if(sradmin)
                //    botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getSrAdminHelpEmbed(user));
            }
        } catch (ErrorResponseException e) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("Umm :cold_sweat: couldn't send you help :sob: maybe it's because your PMs are locked!"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
