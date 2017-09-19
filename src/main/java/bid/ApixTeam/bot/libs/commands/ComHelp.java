package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
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
        EmbedMessageManager embedManager = new EmbedMessageManager();

        if(objects.length != 0) {
            botAPI.getMessageManager().sendMessage(messageChannel, "Incorrect usage! `!help`");
            return;
        }

        if(messageChannel.getType().isGuild()) {
            try {
                botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getHelpEmbed(botAPI, user));
                botAPI.getMessageManager().sendMessage(messageChannel, ":white_check_mark: *sent you some help*.. please check your PMs.");
            } catch (ErrorResponseException e) {
                botAPI.getMessageManager().sendMessage(messageChannel, "Umm :cold_sweat: couldn't send you help :sob: maybe it's because your PMs are locked!");
            }
        } else {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getHelpEmbed(botAPI, user));
        }
    }
}
