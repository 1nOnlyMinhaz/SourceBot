package team.apix.discord.libs.commands;

import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.api.EmbedMessageManager;
import team.apix.discord.utils.api.PermissionManager;
import team.apix.discord.utils.vars.entites.Announcement;
import team.apix.discord.utils.vars.entites.enums.SimpleRank;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;

import java.util.concurrent.TimeUnit;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class ComBroadcast extends Announcement implements CommandExecutor{
    @Command(aliases = {"broadcast", "announce", "bc"})
    public void onCommand(Guild guild, Message command, User user, MessageChannel messageChannel, String[] args) {
        BotAPI botAPI = new BotAPI();
        EmbedMessageManager embedManager = new EmbedMessageManager();
        PermissionManager pm = botAPI.getPermissionManager();

        if(!pm.userRoleAtLeast(guild.getMember(user), SimpleRank.ADMIN)) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getNoComPermission());
            return;
        }

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

            if(command.getMentionedChannels().size() != 1) {
                botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            } else {
                botAPI.getMessageManager().sendMessage(command.getMentionedChannels().get(0), embedManager.getAsDescription(message));
                botAPI.getMessageManager().deleteMessage(command, "Auto Cleared");
            }
        }else if(args[0].equalsIgnoreCase("--now")) {
            String channel = args[1];

            StringBuilder str = new StringBuilder();
            for(int i = 2; i < args.length; i++) {
                str.append(args[i]).append(" ");
            }

            String message = str.toString().trim();

            if(command.getMentionedChannels().size() != 1) {
                botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            } else {
                botAPI.getMessageManager().sendMessage(command.getMentionedChannels().get(0), message);
                botAPI.getMessageManager().deleteMessage(command, "Auto Cleared");
            }
        } else if(args[0].equalsIgnoreCase("later")) {
            try {
                int timeString = Integer.parseInt(args[1]);
                TimeUnit timeUnit;
                if(args[2].equalsIgnoreCase("m"))
                    timeUnit = TimeUnit.MINUTES;
                else if(args[2].equalsIgnoreCase("h"))
                    timeUnit = TimeUnit.HOURS;
                else if(args[2].equalsIgnoreCase("d"))
                    timeUnit = TimeUnit.DAYS;
                else
                    timeUnit = TimeUnit.SECONDS;

                StringBuilder str = new StringBuilder();
                for (int i = 4; i < args.length; i++) {
                    str.append(args[i]).append(" ");
                }

                String message = str.toString().trim();

                if (command.getMentionedChannels().size() != 1) {
                    botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
                } else {
                    botAPI.getMessageManager().deleteMessage(command, "Auto Cleared");
                    botAPI.getMessageManager().sendMessage(command.getMentionedChannels().get(0), embedManager.getAsDescription(message), timeString, timeUnit);
                }
            }catch (Exception e){
                botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            }

        } else if(args[0].equalsIgnoreCase("--later")) {
            try {
                int timeString = Integer.parseInt(args[1]);
                TimeUnit timeUnit;
                if(args[2].equalsIgnoreCase("m"))
                    timeUnit = TimeUnit.MINUTES;
                else if(args[2].equalsIgnoreCase("h"))
                    timeUnit = TimeUnit.HOURS;
                else if(args[2].equalsIgnoreCase("d"))
                    timeUnit = TimeUnit.DAYS;
                else
                    timeUnit = TimeUnit.SECONDS;

                StringBuilder str = new StringBuilder();
                for (int i = 4; i < args.length; i++) {
                    str.append(args[i]).append(" ");
                }

                String message = str.toString().trim();

                if (command.getMentionedChannels().size() != 1) {
                    botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
                } else {
                    botAPI.getMessageManager().deleteMessage(command, "Auto Cleared");
                    botAPI.getMessageManager().sendMessage(command.getMentionedChannels().get(0), message, timeString, timeUnit);
                }
            }catch (Exception e){
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("an error occurred, probably because you're using s or second instead of seconds etc"));
            }

        } else if(args[0].equalsIgnoreCase("repeat")) {
            Announcement announcement = new Announcement();
            announcement.setID(1);
        } else {
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
        }
    }


    private MessageEmbed getUsage() {
        return new BotAPI().getEmbedMessageManager().getUsage("!broadcast {now|later|repeat} [time] {channel} {message}");
    }
}
