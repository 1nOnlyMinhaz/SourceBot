package bid.ApixTeam.bot.libs.events;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.SettingsManager;
import bid.ApixTeam.bot.utils.vars.enums.Settings;
import net.dv8tion.jda.core.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class GuildMessageUpdate extends ListenerAdapter {
    public void onGuildMessageUpdate(GuildMessageUpdateEvent e) {
        BotAPI botAPI = new BotAPI();
        SettingsManager sm = botAPI.getSettingsManager();

        botAPI.getMessageManager().sendMessage(e.getJDA().getTextChannelById(sm.getSetting(Settings.CHAN_LOGS)), e.getMessage().getContent());
    }
}
