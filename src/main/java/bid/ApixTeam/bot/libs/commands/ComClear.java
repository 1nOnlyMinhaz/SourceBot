package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */

public class ComClear implements CommandExecutor {
    @Command(aliases = {"clear", "clean", "cls", "purge"}, async = true)
    public void onCommand(Guild guild, MessageChannel messageChannel, Message message, Object[] objects) {
        BotAPI botAPI = new BotAPI();

        if(messageChannel.getType().isGuild()) {
            if(objects.length < 1 || objects.length > 3) {
                botAPI.getMessageManager().sendMessage(messageChannel, this.getUsage());
            } else if(objects.length == 1) {
                int amount;
                try {
                    amount = Integer.parseInt(objects[0].toString());
                } catch (NumberFormatException e) {
                    botAPI.getMessageManager().sendMessage(messageChannel, this.getUsage());
                    return;
                }

                if(amount < 1 || amount > 100) {
                    botAPI.getMessageManager().sendMessage(messageChannel, "Discord limits message removal from 1 to 100 messages only.");
                    return;
                }
                String s = amount == 1 ? "message" : "messages";
                List<Message> messageHistory = new MessageHistory(messageChannel).retrievePast(amount == 100 ? amount : amount + 1).complete();

                botAPI.getMessageManager().deleteMessage(message, "Auto Cleared.");
                botAPI.getMessageManager().deleteMessages(guild.getTextChannelById(messageChannel.getId()), messageHistory);
                Message m = botAPI.getMessageManager().sendMessage(messageChannel, String.format("Successfully cleared `%d` %s.", amount, s));

                botAPI.getMessageManager().deleteMessageAfter(m, "Auto Cleared", 2L, TimeUnit.SECONDS);
            } else if(objects.length == 2) {
                if(message.getMentionedUsers().size() == 1) {

                    int amount;

                    try {
                        amount = Integer.parseInt(objects[0].toString());
                    } catch (NumberFormatException e) {
                        botAPI.getMessageManager().sendMessage(messageChannel, this.getUsage());
                        return;
                    }

                    User clrUser = message.getMentionedUsers().get(0);

                    if(amount < 1 || amount > 100) {
                        botAPI.getMessageManager().sendMessage(messageChannel, "Discord limits message removal from 1 to 100 messages only.");
                        return;
                    }
                    String s = amount == 1 ? "message" : "messages";
                    List<Message> messageHistory = new MessageHistory(messageChannel).retrievePast(amount == 100 ? amount : amount + 1).complete();

                    botAPI.getMessageManager().deleteMessage(message, "Auto Cleared.");
                    botAPI.getMessageManager().clearUserMessages(guild.getTextChannelById(messageChannel.getId()), messageHistory, clrUser);
                    Message m = botAPI.getMessageManager().sendMessage(messageChannel, String.format("Successfully cleared `%d` %s.", amount, s));

                    botAPI.getMessageManager().deleteMessageAfter(m, "Auto Cleared", 2L, TimeUnit.SECONDS);
                } else if(objects[1].toString().equalsIgnoreCase("-s")) {
                    int amount;

                    try {
                        amount = Integer.parseInt(objects[0].toString());
                    } catch (NumberFormatException e) {
                        botAPI.getMessageManager().sendMessage(messageChannel, this.getUsage());
                        return;
                    }

                    if(amount < 1 || amount > 100) {
                        botAPI.getMessageManager().sendMessage(messageChannel, "Discord limits message removal from 1 to 100 messages only.");
                        return;
                    }
                    List<Message> messageHistory = new MessageHistory(messageChannel).retrievePast(amount == 100 ? amount : amount + 1).complete();

                    botAPI.getMessageManager().deleteMessage(message, "Auto Cleared.");
                    botAPI.getMessageManager().deleteMessages(guild.getTextChannelById(messageChannel.getId()), messageHistory);
                }
            } else if(objects.length == 3) {
                int amount;
                try {
                    amount = Integer.parseInt(objects[0].toString());
                } catch (NumberFormatException e) {
                    botAPI.getMessageManager().sendMessage(messageChannel, this.getUsage());
                    return;
                }

                if(!(message.getMentionedUsers().size() == 1)) {
                    botAPI.getMessageManager().sendMessage(messageChannel, this.getUsage());
                    return;
                }

                User clrUser = message.getMentionedUsers().get(0);

                if(amount < 1 || amount > 100) {
                    botAPI.getMessageManager().sendMessage(messageChannel, "Discord limits message removal from 1 to 100 messages only.");
                    return;
                }
                List<Message> messageHistory = new MessageHistory(messageChannel).retrievePast(amount == 100 ? amount : amount + 1).complete();

                botAPI.getMessageManager().deleteMessage(message, "Auto Cleared.");
                botAPI.getMessageManager().clearUserMessages(guild.getTextChannelById(messageChannel.getId()), messageHistory, clrUser);
            }
        }
    }

    private String getUsage() {
        return "Incorrect usage! `!clear|!clean|!cls|!purge {number} [opt. @user] [opt. -s (silent)]`";
    }
}
