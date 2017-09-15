package bid.ApixTeam.bot.utils;

import bid.ApixTeam.bot.utils.api.DatabaseManager;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import bid.ApixTeam.bot.utils.api.MessageManager;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class BotAPI {
    public DatabaseManager getDatabaseManager(){
        return DatabaseManager.getDatabaseManager();
    }

    public MessageManager getMessageManager(){
        return MessageManager.getMessageManager();
    }

    public EmbedMessageManager getEmbedMessageManager(){
        return EmbedMessageManager.getEmbedMessageManager();
    }
}
