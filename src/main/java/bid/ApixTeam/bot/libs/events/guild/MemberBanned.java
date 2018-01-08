package bid.ApixTeam.bot.libs.events.guild;

import bid.ApixTeam.bot.utils.BotAPI;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.GuildBanEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class MemberBanned extends ListenerAdapter {
    public void onGuildBan(GuildBanEvent event) {
        BotAPI botAPI = new BotAPI();
        User user = event.getUser();

        botAPI.getDatabaseManager().resetUserRanking(user);
    }
}
