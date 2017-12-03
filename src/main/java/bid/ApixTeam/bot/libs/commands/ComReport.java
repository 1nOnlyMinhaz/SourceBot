package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ComReport implements CommandExecutor {
    @Command(aliases = {"report", "userreport", "usereport", "reportuser"})
    public void onCommand(Guild guild, MessageChannel messageChannel, Message command, User user, String args[]) {
        BotAPI botAPI = new BotAPI();

        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

        if(args.length < 2) {
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            botAPI.getMessageManager().deleteMessage(command, "Auto Cleared");
            return;
        }

        if(command.getMentionedUsers().size() == 0) {
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            botAPI.getMessageManager().deleteMessage(command, "Auto Cleared");
            return;
        }

        if(!args[0].matches("\\<\\@(.*?)\\>")) {
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            botAPI.getMessageManager().deleteMessage(command, "Auto Cleared");
            return;
        }

        if(command.getMentionedUsers().get(0).getId().equalsIgnoreCase(command.getAuthor().getId())) {
            botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription("I don't think you want that to work..."));
            return;
        }

        if(command.getMentionedUsers().get(0).getId().equals(command.getJDA().getSelfUser().getId())) {
            botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription("Nice try fam..."));
            return;
        }

        StringBuilder str = new StringBuilder();
        for(int i = 1; i < args.length; i++) {
            str.append(args[i]).append(" ");
        }

        String reason = str.toString().trim();

        botAPI.getMessageManager().sendMessage(guild.getTextChannelById("372802550799269888"), botAPI.getEmbedMessageManager().getReportEmbed(command.getAuthor(), command.getMentionedUsers().get(0), reason, timeStamp));
        botAPI.getMessageManager().deleteMessage(command, "Auto Cleared");
    }

    public MessageEmbed getUsage() {
        return new BotAPI().getEmbedMessageManager().getUsage("!report {@mention) {reason}");
    }
}
