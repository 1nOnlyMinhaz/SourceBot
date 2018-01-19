package team.apix.discord.libs.events.guild;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.api.SettingsManager;
import team.apix.discord.utils.connection.SQLite3;
import team.apix.discord.utils.vars.entites.enums.Settings;

import java.util.Arrays;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class MessageUpdated extends ListenerAdapter {
    private SQLite3 log;

    public MessageUpdated(SQLite3 log) {
        this.log = log;
    }

    public void onGuildMessageUpdate(GuildMessageUpdateEvent e) {
        BotAPI botAPI = new BotAPI();
        SettingsManager sm = botAPI.getSettingsManager();
        Message message = e.getMessage();
        User user = e.getAuthor();

        String s = log.getMessage(e.getMessageIdLong());
        if (s == null)
            return;

        // 0 = long | user id
        // 1 = time | sent on
        // 2 = str  | original message
        // 3 = str  | 1st updated message
        // 4 = str  | 2nd updated message
        // 5 = time | last update
        // 6 = bool | is deleted
        // 7 = long | user id who deleted the message
        String[] strings = s.replace("null", "[-0apix0-]X[--sourcemonika--]").split("//-apixsource-//");
        System.out.println(Arrays.toString(strings));
        if (strings[3].equals("[-0apix0-]X[--sourcemonika--]"))
            log.updateMessage1(e.getMessageIdLong(), message.getContentRaw());
        else if (!strings[4].equalsIgnoreCase("[-0apix0-]X[--sourcemonika--]")) {
            log.updateMessage1(e.getMessageIdLong(), strings[4]);
            log.updateMessage2(e.getMessageIdLong(), message.getContentRaw());
        } else
            log.updateMessage2(e.getMessageIdLong(), message.getContentRaw());

        if (sm.getSetting(Settings.CHAN_LOGS) == null)
            return;

        s = log.getMessage(e.getMessageIdLong());
        strings = s.split("//-apixsource-//");

        /*botAPI.getMessageManager().sendMessageQueue(e.getGuild()
              .getTextChannelById(sm.getSetting(Settings.CHAN_LOGS)),
                botAPI.getEmbedMessageManager()
                      .getLogUpdatedMessage(e.getChannel(), e.getMessageIdLong(), user, strings));
    */}
}
