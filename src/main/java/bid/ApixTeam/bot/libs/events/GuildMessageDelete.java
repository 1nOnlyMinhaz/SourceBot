package bid.ApixTeam.bot.libs.events;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.SettingsManager;
import bid.ApixTeam.bot.utils.vars.enums.Settings;
import net.dv8tion.jda.core.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class GuildMessageDelete extends ListenerAdapter {
    public void onGuildMessageDelete(GuildMessageDeleteEvent e) {
        try {
            BotAPI botAPI = new BotAPI();
            SettingsManager sm = botAPI.getSettingsManager();

            botAPI.getMessageManager().sendMessage(e.getJDA().getTextChannelById(sm.getSetting(Settings.CHAN_LOGS)), e.getChannel().getMessageById(e.getMessageId()).complete().getContent());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
