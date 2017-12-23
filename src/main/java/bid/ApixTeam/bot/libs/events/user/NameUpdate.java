package bid.ApixTeam.bot.libs.events.user;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.PermissionManager;
import bid.ApixTeam.bot.utils.vars.entites.enums.UserUpdate;
import net.dv8tion.jda.core.events.user.UserNameUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class NameUpdate extends ListenerAdapter {
    public void onUserNameUpdate(UserNameUpdateEvent e){
        BotAPI botAPI = new BotAPI();
        PermissionManager pm = botAPI.getPermissionManager();

        pm.updateUser(e.getUser(), UserUpdate.USERNAME);
    }
}
