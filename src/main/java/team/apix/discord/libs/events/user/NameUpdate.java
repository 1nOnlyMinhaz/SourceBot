package team.apix.discord.libs.events.user;

import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.api.PermissionManager;
import team.apix.discord.utils.vars.entites.enums.UserUpdate;
import net.dv8tion.jda.core.events.user.UserNameUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class NameUpdate extends ListenerAdapter {
    public void onUserNameUpdate(UserNameUpdateEvent e){
        BotAPI botAPI = new BotAPI();
        PermissionManager pm = botAPI.getPermissionManager();

        pm.updateUser(e.getUser(), UserUpdate.USERNAME);
    }
}
