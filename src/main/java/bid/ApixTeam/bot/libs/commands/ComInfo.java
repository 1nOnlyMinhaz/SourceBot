package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import bid.ApixTeam.bot.utils.vars.Messages;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class ComInfo implements CommandExecutor {
    @Command(aliases = "info")
    public void onCommand(Guild guild, User user, MessageChannel messageChannel, Message message, Object[] objects){
        BotAPI botAPI = new BotAPI();
        EmbedMessageManager embedManager = new EmbedMessageManager();

        if(messageChannel.getType().isGuild()) {
            if(objects.length == 0) {
                try {
                    botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getInfoMain(botAPI, user));
                    botAPI.getMessageManager().sendMessage(messageChannel, ":white_check_mark: *sent you some info*.. please check your PMs.");
                } catch (ErrorResponseException e) {
                    botAPI.getMessageManager().sendMessage(messageChannel, "Umm :cold_sweat: couldn't send you info :sob: maybe it's because your PMs are locked!");
                }
            } else if(objects.length == 1) {
                if(objects[0].toString().equalsIgnoreCase("rank") || objects[0].toString().equalsIgnoreCase("ranks") || objects[0].toString().equalsIgnoreCase("levels")) {
                    try {
                        botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getInfoRank(botAPI, user));
                        botAPI.getMessageManager().sendMessage(messageChannel, ":white_check_mark: *sent you some info*.. please check your PMs.");
                    } catch (ErrorResponseException e) {
                        botAPI.getMessageManager().sendMessage(messageChannel, "Umm :cold_sweat: couldn't send you info :sob: maybe it's because your PMs are locked!");
                    }
                } else if(objects[0].toString().equalsIgnoreCase("moderation")) {
                    try {
                        botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getInfoMod(botAPI, user));
                        botAPI.getMessageManager().sendMessage(messageChannel, ":white_check_mark: *sent you some info*.. please check your PMs.");
                    } catch (ErrorResponseException e) {
                        botAPI.getMessageManager().sendMessage(messageChannel, "Umm :cold_sweat: couldn't send you info :sob: maybe it's because your PMs are locked!");
                    }
                } else if(objects[0].toString().equalsIgnoreCase("administration")) {
                    try {
                        botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getInfoAdmin(botAPI, user));
                        botAPI.getMessageManager().sendMessage(messageChannel, ":white_check_mark: *sent you some info*.. please check your PMs.");
                    } catch (ErrorResponseException e) {
                        botAPI.getMessageManager().sendMessage(messageChannel, "Umm :cold_sweat: couldn't send you info :sob: maybe it's because your PMs are locked!");
                    }
                }
            }
        } else {
            if(objects.length == 0) {
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getInfoMain(botAPI, user));
            } else if(objects.length == 1) {
                if(objects[0].toString().equalsIgnoreCase("rank") || objects[0].toString().equalsIgnoreCase("ranks") || objects[0].toString().equalsIgnoreCase("levels")) {
                    botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getInfoRank(botAPI, user));
                } else if(objects[0].toString().equalsIgnoreCase("moderation")) {
                    botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getInfoMod(botAPI, user));
                } else if(objects[0].toString().equalsIgnoreCase("administration")) {
                    botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getInfoAdmin(botAPI, user));
                } else {
                    botAPI.getMessageManager().sendMessage(messageChannel, String.format(Messages.INCORRECT_USAGE, "!info [opt. rank | moderation | administration]"));
                }
            }
        }

    }
}
