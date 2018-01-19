package team.apix.discord.utils.api;

import net.dv8tion.jda.core.entities.*;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class MessageManager {
    private static MessageManager messageManager = new MessageManager();

    public static MessageManager getMessageManager() {
        return messageManager;
    }

    public void log(boolean b, String s){
        if(b)
            System.out.printf("[%s] [ERROR] %s%n", new Date(), s);
        else
            System.out.printf("[%s] [INFO] %s%n", new Date(), s);
    }

    public Message sendMessage(MessageChannel messageChannel, String s){
        return messageChannel.sendMessage(s).complete();
    }

    public Message sendMessage(MessageChannel messageChannel, Message m){
        return messageChannel.sendMessage(m).complete();
    }

    public Message sendMessage(MessageChannel messageChannel, MessageEmbed m){
        return messageChannel.sendMessage(m).complete();
    }

    public Message sendMessage(MessageChannel messageChannel, String s, long delay, TimeUnit timeUnit){
        return messageChannel.sendMessage(s).completeAfter(delay, timeUnit);
    }

    public Message sendMessage(MessageChannel messageChannel, Message m, long delay, TimeUnit timeUnit){
        return messageChannel.sendMessage(m).completeAfter(delay, timeUnit);
    }

    public Message sendMessage(MessageChannel messageChannel, MessageEmbed m, long delay, TimeUnit timeUnit){
        return messageChannel.sendMessage(m).completeAfter(delay, timeUnit);
    }

    public void sendMessageQueue(MessageChannel messageChannel, String s){
        messageChannel.sendMessage(s).queue();
    }

    public void sendMessageQueue(MessageChannel messageChannel, Message m){
        messageChannel.sendMessage(m).queue();
    }

    public void sendMessageQueue(MessageChannel messageChannel, MessageEmbed m){
        messageChannel.sendMessage(m).queue();
    }

    public void sendMessageQueue(MessageChannel messageChannel, String s, long delay, TimeUnit timeUnit){
        messageChannel.sendMessage(s).queueAfter(delay, timeUnit);
    }

    public void sendMessageQueue(MessageChannel messageChannel, Message m, long delay, TimeUnit timeUnit){
        messageChannel.sendMessage(m).queueAfter(delay, timeUnit);
    }

    public void sendMessageQueue(MessageChannel messageChannel, MessageEmbed m, long delay, TimeUnit timeUnit){
        messageChannel.sendMessage(m).queueAfter(delay, timeUnit);
    }

    public void deleteMessage(Message m){
        m.delete().queue();
    }

    public void deleteMessage(Message m, String reason){
        m.delete().reason(reason).queue();
    }

    public void deleteMessageAfter(Message m, String reason, Long delay, TimeUnit timeUnit){
        m.delete().reason(reason).queueAfter(delay, timeUnit);
    }

    public void deleteMessageAfter(Message m, Long delay, TimeUnit timeUnit){
        m.delete().queueAfter(delay, timeUnit);
    }

    public void deleteMessages(TextChannel textChannel, Collection<Message> messages){
        textChannel.deleteMessages(messages).queue();
    }

    public void clearUserMessages(TextChannel textChannel, Collection<Message> messages, User user, int amount) {
        int counter = 1;
        for(Message iMessage : messages) {
            if(counter > amount) break;
            if(iMessage.getAuthor().getId().equals(user.getId()) && !(iMessage.getContentDisplay().startsWith("!"))) {
                Consumer<Void> callback = (response) -> System.out.printf("[%s] [INFO] [API] [%s:%s] Bulk message delete. %n", new Date(), user.getName(), textChannel.getName());
                textChannel.deleteMessageById(iMessage.getId()).queue(callback);
                counter++;
            }
        }
    }
}
