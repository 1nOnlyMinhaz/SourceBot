package bid.ApixTeam.bot.libs.events.user;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.PermissionManager;
import bid.ApixTeam.bot.utils.vars.entites.enums.UserUpdate;
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
