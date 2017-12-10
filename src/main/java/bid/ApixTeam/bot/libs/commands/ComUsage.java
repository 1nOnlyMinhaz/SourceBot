package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;

public class ComUsage implements CommandExecutor {

    @Command(aliases = {"usage", "command", "cmd"})
    public void onCommand(User user, MessageChannel channel, Message message, String[] args) {

        BotAPI botAPI = new BotAPI();

        EmbedMessageManager embedManager = new EmbedMessageManager();

        if (args.length == 0 || args.length > 1) {
            if (channel.getType().isGuild()) {
                botAPI.getMessageManager().sendMessage(channel, botAPI.getEmbedMessageManager().getUsage("!usage {command}"));
            } else {
                try {
                    botAPI.getPrivateMessageManager().sendMessage(user, botAPI.getEmbedMessageManager().getUsage("!usage {command} *Use !help for a list of commands.*"));
                } catch (ErrorResponseException e) {
                    botAPI.getMessageManager().sendMessage(channel, String.format("Umm, <@%s> :cold_sweat: couldn't send u usage :sob: maybe it's because your PMs are locked!", user.getId()));
                }
            }
        } else {
            try {
                if (args[0].equalsIgnoreCase("help")) {
                    botAPI.getPrivateMessageManager().sendMessage(user, botAPI.getEmbedMessageManager().getUsageHelp(user));
                    botAPI.getMessageManager().sendMessage(channel, embedManager.getAsDescription(":white_check_mark: *sent you usage for !help*.. please check your PMs."));

                } else if (args[0].equalsIgnoreCase("info")) {

                    botAPI.getPrivateMessageManager().sendMessage(user, botAPI.getEmbedMessageManager().getUsageInfo(user));
                    botAPI.getMessageManager().sendMessage(channel, embedManager.getAsDescription(":white_check_mark: *sent you usage for !info*.. please check your PMs."));

                } else if (args[0].equalsIgnoreCase("rank")) {

                    botAPI.getPrivateMessageManager().sendMessage(user, botAPI.getEmbedMessageManager().getUsageRank(user));
                    botAPI.getMessageManager().sendMessage(channel, embedManager.getAsDescription(":white_check_mark: *sent you usage for !rank*.. please check your PMs."));

                } else if (args[0].equalsIgnoreCase("levels")) {

                    botAPI.getPrivateMessageManager().sendMessage(user, botAPI.getEmbedMessageManager().getUsageLevels(user));
                    botAPI.getMessageManager().sendMessage(channel, embedManager.getAsDescription(":white_check_mark: *sent you usage for !levels*.. please check your PMs."));

                } else if (args[0].equalsIgnoreCase("clear")) {
                    botAPI.getPrivateMessageManager().sendMessage(user, botAPI.getEmbedMessageManager().getUsageClear(user));
                    botAPI.getMessageManager().sendMessage(channel, embedManager.getAsDescription(":white_check_mark: *sent you usage for !clear*.. please check your PMs."));

                } else if (args[0].equalsIgnoreCase("slowmode")) {
                    botAPI.getPrivateMessageManager().sendMessage(user, botAPI.getEmbedMessageManager().getUsageSlowmode(user));
                    botAPI.getMessageManager().sendMessage(channel, embedManager.getAsDescription(":white_check_mark: *sent you usage for !slowmode*.. please check your PMs."));
                } else if (args[0].equalsIgnoreCase("mute")) {

                    botAPI.getPrivateMessageManager().sendMessage(user, botAPI.getEmbedMessageManager().getUsageMute(user));
                    botAPI.getMessageManager().sendMessage(channel, embedManager.getAsDescription(":white_check_mark: *sent you usage for !mute*.. please check your PMs."));
                } else if (args[0].equalsIgnoreCase("unmute")) {
                    botAPI.getPrivateMessageManager().sendMessage(user, botAPI.getEmbedMessageManager().getUsageUnmute(user));
                    botAPI.getMessageManager().sendMessage(channel, embedManager.getAsDescription(":white_check_mark: *sent you usage for !unmute*.. please check your PMs."));
                } else {
                    botAPI.getMessageManager().sendMessage(channel, botAPI.getEmbedMessageManager().getUsage("!usage {command}"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                botAPI.getMessageManager().sendMessage(channel, String.format("Umm, <@%s> :cold_sweat: couldn't send u usage :sob: maybe it's because your PMs are locked!", user.getId()));
            }
        }
    }
}
