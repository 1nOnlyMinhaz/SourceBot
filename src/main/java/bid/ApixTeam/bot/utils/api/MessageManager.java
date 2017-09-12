package bid.ApixTeam.bot.utils.api;

import net.dv8tion.jda.core.entities.*;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class MessageManager {
    private static MessageManager messageManager = new MessageManager();

    public static MessageManager getMessageManager() {
        return messageManager;
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
}
