package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.PermissionManager;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.Messages;
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

        if (!pm.userAtLeast(user, SimpleRank.BOT_ADMIN)) {
            botAPI.getMessageManager().sendMessage(messageChannel, Messages.NO_COM_PERMISSION);
            return;
        }

        if (objects[0].toString().equalsIgnoreCase("perm-jr-mod")) {
            if (message.getMentionedUsers().size() == 1)
                checkUserPermission(botAPI, pm, messageChannel, message, SimpleRank.JR_MOD);
            else if (message.getMentionedRoles().size() == 1)
                checkRolePermission(botAPI, pm, messageChannel, message, SimpleRank.JR_MOD);
        } else if (objects[0].toString().equalsIgnoreCase("perm-sr-mod")) {
            if (message.getMentionedUsers().size() == 1)
                checkUserPermission(botAPI, pm, messageChannel, message, SimpleRank.SR_MOD);
            else if (message.getMentionedRoles().size() == 1)
                checkRolePermission(botAPI, pm, messageChannel, message, SimpleRank.SR_MOD);
        } else if (objects[0].toString().equalsIgnoreCase("perm-jr-admin")) {
            if (message.getMentionedUsers().size() == 1)
                checkUserPermission(botAPI, pm, messageChannel, message, SimpleRank.JR_ADMIN);
            else if (message.getMentionedRoles().size() == 1)
                checkRolePermission(botAPI, pm, messageChannel, message, SimpleRank.JR_ADMIN);
        } else if (objects[0].toString().equalsIgnoreCase("perm-sr-admin")) {
            if (message.getMentionedUsers().size() == 1)
                checkUserPermission(botAPI, pm, messageChannel, message, SimpleRank.SR_ADMIN);
            else if (message.getMentionedRoles().size() == 1)
                checkRolePermission(botAPI, pm, messageChannel, message, SimpleRank.SR_ADMIN);
        } else if (objects[0].toString().equalsIgnoreCase("set-jr-mod")) {
            if (message.getMentionedUsers().size() == 1)
                setUserPermission(botAPI, pm, messageChannel, message, SimpleRank.JR_MOD);
            else if (message.getMentionedRoles().size() == 1)
                setRolePermission(botAPI, pm, messageChannel, message, SimpleRank.JR_MOD);
        } else if (objects[0].toString().equalsIgnoreCase("set-sr-mod")) {
            if (message.getMentionedUsers().size() == 1)
                setUserPermission(botAPI, pm, messageChannel, message, SimpleRank.SR_MOD);
            else if (message.getMentionedRoles().size() == 1)
                setRolePermission(botAPI, pm, messageChannel, message, SimpleRank.SR_MOD);
        } else if (objects[0].toString().equalsIgnoreCase("set-jr-admin")) {
            if (message.getMentionedUsers().size() == 1)
                setUserPermission(botAPI, pm, messageChannel, message, SimpleRank.JR_ADMIN);
            else if (message.getMentionedRoles().size() == 1)
                setRolePermission(botAPI, pm, messageChannel, message, SimpleRank.JR_ADMIN);
        } else if (objects[0].toString().equalsIgnoreCase("set-sr-admin")) {
            if (message.getMentionedUsers().size() == 1)
                setUserPermission(botAPI, pm, messageChannel, message, SimpleRank.SR_ADMIN);
            else if (message.getMentionedRoles().size() == 1)
                setRolePermission(botAPI, pm, messageChannel, message, SimpleRank.SR_ADMIN);
        }
    }

    private void checkUserPermission(BotAPI botAPI, PermissionManager pm, MessageChannel messageChannel, Message message, SimpleRank simpleRank){
        User target = message.getMentionedUsers().get(0);
        boolean atLeast = pm.userAtLeast(target, simpleRank);
        boolean higher = pm.userHigherThan(target, simpleRank);
        boolean lower = pm.userLowerThan(target, simpleRank);
        botAPI.getMessageManager().sendMessage(messageChannel, String.format(":thinking: **%s** to **%s**? `AtLeast`:`%s`, `Higher`:`%s`, `Lower`:`%s`", target.getName(), simpleRank.getDescription(), atLeast, higher, lower));
    }

    private void checkRolePermission(BotAPI botAPI, PermissionManager pm, MessageChannel messageChannel, Message message, SimpleRank simpleRank){
        Role role = message.getMentionedRoles().get(0);
        boolean atLeast = pm.roleAtLeast(role, simpleRank);
        boolean higher = pm.roleHigherThan(role, simpleRank);
        boolean lower = pm.roleLowerThan(role, simpleRank);
        botAPI.getMessageManager().sendMessage(messageChannel, String.format(":thinking: **%s** to **%s**? `AtLeast`:`%s`, `Higher`:`%s`, `Lower`:`%s`", role.getName(), simpleRank.getDescription(), atLeast, higher, lower));
    }

    private void setUserPermission(BotAPI botAPI, PermissionManager pm, MessageChannel messageChannel, Message message, SimpleRank simpleRank){
        User target = message.getMentionedUsers().get(0);
        pm.setUserPermission(target, simpleRank);
        botAPI.getMessageManager().sendMessage(messageChannel, "done.");
    }

    private void setRolePermission(BotAPI botAPI, PermissionManager pm, MessageChannel messageChannel, Message message, SimpleRank simpleRank){
        Role role = message.getMentionedRoles().get(0);
        pm.setRolePermission(botAPI, role, simpleRank);
        botAPI.getMessageManager().sendMessage(messageChannel, "done.");
    }
}
