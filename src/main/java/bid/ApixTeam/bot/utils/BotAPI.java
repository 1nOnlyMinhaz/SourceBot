package bid.ApixTeam.bot.utils;

import bid.ApixTeam.bot.utils.api.*;

/**
 * Source was created by ApixTeam (C) 2017
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

    public PrivateMessageManager getPrivateMessageManager(){
        return PrivateMessageManager.getPrivateMessageManager();
    }

    public PermissionManager getPermissionManager(){
        return PermissionManager.getPermissionManager();
    }

    public SettingsManager getSettingsManager(){
        return SettingsManager.getSettingsManager();
    }

    public IncidentManager getIncidentManager(){
        return IncidentManager.getIncidentManager();
    }

    public ExtraUtils getExtraUtils() {
        return ExtraUtils.getExtraUtils();
    }
}
