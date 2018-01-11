package team.apix.discord.libs.commands;

import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.api.EmbedMessageManager;
import team.apix.discord.utils.api.PermissionManager;
import team.apix.discord.utils.vars.entites.enums.SimpleRank;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class ComClear implements CommandExecutor {
    @Command(aliases = {"clear", "clean", "cls", "purge"}, async = true, privateMessages = false)
    public void onCommand(Guild guild, User user, MessageChannel messageChannel, Message message, Object[] objects) {
        BotAPI botAPI = new BotAPI();
        EmbedMessageManager embed = botAPI.getEmbedMessageManager();
        PermissionManager pm = botAPI.getPermissionManager();

        if(!pm.userRoleAtLeast(guild.getMember(user), SimpleRank.ADMIN)) {
            botAPI.getMessageManager().sendMessage(messageChannel, embed.getNoComPermission());
            return;
        }

        try {
            if (messageChannel.getType().isGuild()) {
                if (objects.length < 1 || objects.length > 3) {
                    botAPI.getMessageManager().sendMessage(messageChannel, this.getUsage());
                } else if (objects.length == 1) {
                    int amount;
                    try {
                        amount = Integer.parseInt(objects[0].toString());
                    } catch (NumberFormatException e) {
                        botAPI.getMessageManager().sendMessage(messageChannel, this.getUsage());
                        return;
                    }

                    if (amount < 1 || amount > 100) {
                        botAPI.getMessageManager().sendMessage(messageChannel, embed.getClearLimit());
                        return;
                    }
                    String s = amount == 1 ? "message" : "messages";
                    List<Message> messageHistory = new MessageHistory(messageChannel).retrievePast(amount == 100 ? amount : amount + 1).complete();

                    botAPI.getMessageManager().deleteMessage(message, "Auto Cleared.");
                    botAPI.getMessageManager().deleteMessages(guild.getTextChannelById(messageChannel.getId()), messageHistory);
                    Message m = botAPI.getMessageManager().sendMessage(messageChannel, embed.getClearSuccess(amount, s));

                    botAPI.getMessageManager().deleteMessageAfter(m, "Auto Cleared", 6L, TimeUnit.SECONDS);
                } else if (objects.length == 2) {
                    if (message.getMentionedUsers().size() == 1) {

                        int amount;

                        try {
                            amount = Integer.parseInt(objects[0].toString());
                        } catch (NumberFormatException e) {
                            botAPI.getMessageManager().sendMessage(messageChannel, this.getUsage());
                            return;
                        }

                        User clrUser = message.getMentionedUsers().get(0);

                        if (amount < 1 || amount > 100) {
                            botAPI.getMessageManager().sendMessage(messageChannel, embed.getClearLimit());
                            return;
                        }
                        String s = amount == 1 ? "message" : "messages";
                        List<Message> messageHistory = new MessageHistory(messageChannel).retrievePast(100).complete();

                        botAPI.getMessageManager().deleteMessage(message, "Auto Cleared.");
                        botAPI.getMessageManager().clearUserMessages(guild.getTextChannelById(messageChannel.getId()), messageHistory, clrUser, amount);
                        Message m = botAPI.getMessageManager().sendMessage(messageChannel, embed.getClearSuccess(amount, s));

                        botAPI.getMessageManager().deleteMessageAfter(m, "Auto Cleared", 6L, TimeUnit.SECONDS);
                    } else if (objects[1].toString().equalsIgnoreCase("-s")) {
                        int amount;

                        try {
                            amount = Integer.parseInt(objects[0].toString());
                        } catch (NumberFormatException e) {
                            botAPI.getMessageManager().sendMessage(messageChannel, this.getUsage());
                            return;
                        }

                        if (amount < 1 || amount > 100) {
                            botAPI.getMessageManager().sendMessage(messageChannel, embed.getClearLimit());
                            return;
                        }
                        List<Message> messageHistory = new MessageHistory(messageChannel).retrievePast(amount == 100 ? amount : amount + 1).complete();

                        botAPI.getMessageManager().deleteMessage(message, "Auto Cleared.");
                        botAPI.getMessageManager().deleteMessages(guild.getTextChannelById(messageChannel.getId()), messageHistory);
                    }
                } else if (objects.length == 3) {
                    int amount;
                    try {
                        amount = Integer.parseInt(objects[0].toString());
                    } catch (NumberFormatException e) {
                        botAPI.getMessageManager().sendMessage(messageChannel, this.getUsage());
                        return;
                    }

                    if (!(message.getMentionedUsers().size() == 1)) {
                        botAPI.getMessageManager().sendMessage(messageChannel, this.getUsage());
                        return;
                    }

                    User clrUser = message.getMentionedUsers().get(0);

                    if (amount < 1 || amount > 100) {
                        botAPI.getMessageManager().sendMessage(messageChannel, embed.getClearLimit());
                        return;
                    }
                    List<Message> messageHistory = new MessageHistory(messageChannel).retrievePast(100).complete();

                    botAPI.getMessageManager().deleteMessage(message, "Auto Cleared.");
                    botAPI.getMessageManager().clearUserMessages(guild.getTextChannelById(messageChannel.getId()), messageHistory, clrUser, amount);
                }
            }
        }catch (IllegalArgumentException e){
            Message message1 = botAPI.getMessageManager().sendMessage(messageChannel, embed.getAsDescription("Discord doesn't allow me to delete posts older than 2 weeks :cold_sweat:"));
            botAPI.getMessageManager().deleteMessage(message1);
        }
    }

    private MessageEmbed getUsage() {
        return new BotAPI().getEmbedMessageManager().getUsage("!clear {number} [opt. @user]");
    }
}
