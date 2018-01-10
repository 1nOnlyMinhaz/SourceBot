package team.apix.discord.utils;

import team.apix.discord.utils.api.*;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
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
