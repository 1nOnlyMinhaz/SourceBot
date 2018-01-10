package bid.ApixTeam.bot.libs.events.guild;

import bid.ApixTeam.bot.utils.BotAPI;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.GuildBanEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class MemberBanned extends ListenerAdapter {
    public void onGuildBan(GuildBanEvent event) {
        BotAPI botAPI = new BotAPI();
        User user = event.getUser();

        botAPI.getDatabaseManager().resetUserRanking(user);
    }
}
