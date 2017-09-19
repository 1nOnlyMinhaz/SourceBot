package bid.ApixTeam.bot.utils.api;

import net.dv8tion.jda.core.entities.*;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
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

    public void clearUserMessages(TextChannel textChannel, Collection<Message> messages, User user) {
        for(Message iMessage : messages) {
            if(iMessage.getAuthor().getId().equals(user.getId())) {
                Consumer<Void> callback = (response) -> System.out.printf("[%s] [INFO] [API] [%s:%s] Bulk message delete. %n", new Date(), user.getName(), textChannel.getName());
                textChannel.deleteMessages(messages).queue(callback);
            }
        }
    }
}
