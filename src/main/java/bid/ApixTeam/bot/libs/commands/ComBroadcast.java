package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class ComBroadcast implements CommandExecutor {
    @Command(aliases = {"broadcast", "announce", "bc"})
    public void onCommand(Guild guild, Message command, MessageChannel messageChannel, Object[] objects) {
        BotAPI botAPI = new BotAPI();
        EmbedMessageManager embedManager = new EmbedMessageManager();

        if(objects.length != 2) {
            botAPI.getMessageManager().sendMessage(messageChannel, this.getUsage());
        } else {
            String message = (String) objects[0];
            String channel = (String) objects[1];

            botAPI.getMessageManager().deleteMessage(command, "Auto cleared");
            botAPI.getMessageManager().sendMessage(guild.getTextChannelById(channel), embedManager.getAsDescription(message));
        }
    }

    private MessageEmbed getUsage() {
        return new BotAPI().getEmbedMessageManager().getUsage("!broadcast {message} {channel name}");
    }
}
