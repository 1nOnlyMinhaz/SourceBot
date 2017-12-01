package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComBroadcast implements CommandExecutor {
    @Command(aliases = {"broadcast", "announce", "bc"})
    public void onCommand(Guild guild, Message command, MessageChannel messageChannel, String[] args) {
        BotAPI botAPI = new BotAPI();
        EmbedMessageManager embedManager = new EmbedMessageManager();

        if(args.length < 3) {
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            return;
        }

        if(args[0].equalsIgnoreCase("now")) {
            String channel = args[1];

            StringBuilder str = new StringBuilder();
            for(int i = 2; i < args.length; i++) {
                str.append(args[i]).append(" ");
            }

            String message = str.toString().trim();

            Pattern channelPattern = Pattern.compile("\\<\\#(.*?)\\>");
            Matcher channelMatcher = channelPattern.matcher(channel);

            if(!channelMatcher.find()) {
                botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            } else {
                String channelId = channelMatcher.group(1);

                botAPI.getMessageManager().sendMessage(guild.getTextChannelById(channelId), embedManager.getAsDescription(message));
                botAPI.getMessageManager().deleteMessage(command, "Auto Cleared");
            }
        } else if(args[0].equalsIgnoreCase("later")) {
            String timeString = args[1];
            int time;

            try {
                time = Integer.parseInt(timeString);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
                return;
            }

            String channel = args[2];

            StringBuilder str = new StringBuilder();
            for(int i = 3; i < args.length; i++) {
                str.append(args[i]).append(" ");
            }

            String message = str.toString().trim();

            Pattern channelPattern = Pattern.compile("\\<\\#(.*?)\\>");
            Matcher channelMatcher = channelPattern.matcher(channel);

            if(!channelMatcher.find()) {
                botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            } else {
                String channelId = channelMatcher.group(1);

                botAPI.getMessageManager().deleteMessage(command, "Auto Cleared");
                botAPI.getMessageManager().sendMessage(guild.getTextChannelById(channelId), embedManager.getAsDescription(message)); //Idk how to queueAfter here, wont let me plx fix father
            }

        } else if(args[0].equalsIgnoreCase("repeat")) {
            //Daddy fix plx
        } else {
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
        }
    }


    private MessageEmbed getUsage() {
        return new BotAPI().getEmbedMessageManager().getUsage("!broadcast {now|later|repeat} [time (for later & repeat)] {channel} {message}");
    }
}
