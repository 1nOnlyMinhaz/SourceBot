package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.PermissionManager;
import bid.ApixTeam.bot.utils.vars.enums.SimpleRank;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class ComCheck implements CommandExecutor {
    @Command(aliases = "check")
    public void onCommand(User user, MessageChannel messageChannel, Message message, Object[] objects) {
        if (objects.length != 2)
            return;

        BotAPI botAPI = new BotAPI();
        PermissionManager pm = botAPI.getPermissionManager();
        if (objects[0].toString().equalsIgnoreCase("perm")) {
            if (message.getMentionedUsers().size() == 1) {
                User target = message.getMentionedUsers().get(0);
                boolean atLeast = pm.userAtLeast(target, SimpleRank.JR_MOD);
                boolean higher = pm.userHigherThan(target, SimpleRank.JR_MOD);
                boolean lower = pm.userLowerThan(target, SimpleRank.JR_MOD);
                botAPI.getMessageManager().sendMessage(messageChannel, String.format(":thinking: %s to JR_MOD? `AtLeast`:`%s`, `Higher`:`%s`, `Lower`:`%s`", target.getName(), atLeast, higher, lower));
            } else if (message.getMentionedRoles().size() == 1) {
                Role role = message.getMentionedRoles().get(0);
                boolean atLeast = pm.roleAtLeast(role, SimpleRank.JR_MOD);
                boolean higher = pm.roleHigherThan(role, SimpleRank.JR_MOD);
                boolean lower = pm.roleLowerThan(role, SimpleRank.JR_MOD);
                botAPI.getMessageManager().sendMessage(messageChannel, String.format(":thinking: %s to JR_MOD? `AtLeast`:`%s`, `Higher`:`%s`, `Lower`:`%s`", role.getName(), atLeast, higher, lower));
            }
        }
    }
}
