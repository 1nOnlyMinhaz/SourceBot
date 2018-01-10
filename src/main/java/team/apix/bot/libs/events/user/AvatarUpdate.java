package team.apix.bot.libs.events.user;

import team.apix.bot.utils.BotAPI;
import team.apix.bot.utils.api.PermissionManager;
import team.apix.bot.utils.vars.entites.enums.UserUpdate;
import net.dv8tion.jda.core.events.user.UserAvatarUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class AvatarUpdate extends ListenerAdapter {
    public void onUserAvatarUpdate(UserAvatarUpdateEvent e){
        BotAPI botAPI = new BotAPI();
        PermissionManager pm = botAPI.getPermissionManager();

        pm.updateUser(e.getUser(), UserUpdate.AVATAR);
    }
}
