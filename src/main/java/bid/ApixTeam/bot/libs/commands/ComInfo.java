package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class ComInfo implements CommandExecutor {
    @Command(aliases = "info")
    public void onCommand(User user, MessageChannel messageChannel, Object[] objects) {
        BotAPI botAPI = new BotAPI();
        EmbedMessageManager embedManager = new EmbedMessageManager();

        if (messageChannel.getType().isGuild()) {
            try {
                if (objects.length == 0) {
                    botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription(":white_check_mark: *sent you some info*.. please check your PMs."));
                    botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getInfoMain(user));
                } else if (objects.length == 1) {
                    if (objects[0].toString().equalsIgnoreCase("rank") || objects[0].toString().equalsIgnoreCase("ranks") || objects[0].toString().equalsIgnoreCase("levels")) {
                        botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getInfoRank(user));
                        botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription(":white_check_mark: *sent you some info*.. please check your PMs."));
                    } else if (objects[0].toString().equalsIgnoreCase("moderation") || objects[0].toString().equalsIgnoreCase("mods")) {
                        botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getInfoMod(user));
                        botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription(":white_check_mark: *sent you some info*.. please check your PMs."));
                    } else if (objects[0].toString().equalsIgnoreCase("administration") || objects[0].toString().equalsIgnoreCase("admins")) {
                        botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getInfoAdmin(user));
                        botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription(":white_check_mark: *sent you some info*.. please check your PMs."));
                    }
                }
            } catch (ErrorResponseException e) {
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("Umm :cold_sweat: couldn't send you info :sob: maybe it's because your PMs are locked!"));
            }
        } else {
            if (objects.length == 0) {
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getInfoMain(user));
            } else if (objects.length == 1) {
                if (objects[0].toString().equalsIgnoreCase("rank") || objects[0].toString().equalsIgnoreCase("ranks") || objects[0].toString().equalsIgnoreCase("levels")) {
                    botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getInfoRank(user));
                } else if (objects[0].toString().equalsIgnoreCase("moderation")) {
                    botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getInfoMod(user));
                } else if (objects[0].toString().equalsIgnoreCase("administration")) {
                    botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getInfoAdmin(user));
                } else {
                    botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getUsage("!info [opt. rank | moderation | administration]"));
                }
            }
        }
    }
}
