package bid.ApixTeam.bot.utils.api;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;

import java.io.File;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class PrivateMessageManager {
    private static PrivateMessageManager privateMessageManager = new PrivateMessageManager();

    public static PrivateMessageManager getPrivateMessageManager() {
        return privateMessageManager;
    }

    public void sendMessage(User user, String s){
        user.openPrivateChannel().queue((privateChannel -> privateChannel.sendMessage(s).queue()));
    }

    public void sendMessage(User user, Message message){
        user.openPrivateChannel().queue((privateChannel -> privateChannel.sendMessage(message).queue()));
    }

    public void sendMessage(User user, MessageEmbed messageEmbed){
        PrivateChannel privateChannel = user.openPrivateChannel().complete();
        privateChannel.sendMessage(messageEmbed).complete();
    }

    public void sendFile(User user, Message message, File file){
        user.openPrivateChannel().queue((privateChannel -> privateChannel.sendFile(file, message).queue()));
    }

    public void sendFile(User user, Message message, File file, String fileName){
        user.openPrivateChannel().queue((privateChannel -> privateChannel.sendFile(file, fileName, message).queue()));
    }
}
