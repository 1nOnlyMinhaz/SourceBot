package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import bid.ApixTeam.bot.utils.api.PermissionManager;
import bid.ApixTeam.bot.utils.api.SettingsManager;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.enums.Settings;
import bid.ApixTeam.bot.utils.vars.enums.SimpleRank;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class ComSettings implements CommandExecutor {
    @Command(aliases = "settings", async = true)
    public void onCommand(User user, MessageChannel messageChannel, Message message, String[] strings) {
        if (!(strings.length >= 2 && strings.length <= 4))
            return;

        BotAPI botAPI = new BotAPI();
        PermissionManager pm = botAPI.getPermissionManager();
        EmbedMessageManager em = botAPI.getEmbedMessageManager();
        SettingsManager sm = botAPI.getSettingsManager();

        if (!pm.userAtLeast(user, SimpleRank.BOT_ADMIN)) return;

        if (strings[0].equalsIgnoreCase("set")) {
            if (strings[1].equalsIgnoreCase("role") && message.getMentionedRoles().size() == 1) {
                if (strings.length != 4)
                    return;

                if (strings[3].equalsIgnoreCase("muted"))
                    setRolePermission(botAPI, em, pm, messageChannel, message, SimpleRank.MUTED);
                else if (strings[3].equalsIgnoreCase("jrmod"))
                    setRolePermission(botAPI, em, pm, messageChannel, message, SimpleRank.JR_MOD);
                else if (strings[3].equalsIgnoreCase("srmod"))
                    setRolePermission(botAPI, em, pm, messageChannel, message, SimpleRank.SR_MOD);
                else if (strings[3].equalsIgnoreCase("jrmod"))
                    setRolePermission(botAPI, em, pm, messageChannel, message, SimpleRank.JR_ADMIN);
                else if (strings[3].equalsIgnoreCase("sradmin"))
                    setRolePermission(botAPI, em, pm, messageChannel, message, SimpleRank.SR_ADMIN);
                else if (strings[3].equalsIgnoreCase("chief"))
                    setRolePermission(botAPI, em, pm, messageChannel, message, SimpleRank.CHIEF_ADMIN);
            } else if (strings[1].equalsIgnoreCase("channel") && message.getMentionedChannels().size() == 1) {
                if (strings.length != 4)
                    return;

                if (strings[3].equalsIgnoreCase("welcome"))
                    setChannelType(botAPI, sm, em, messageChannel, message, Settings.CHAN_WELCOME);
                else if (strings[3].equalsIgnoreCase("rank"))
                    setChannelType(botAPI, sm, em, messageChannel, message, Settings.CHAN_RANK_CHECK);
            }
        } else if (strings[0].equalsIgnoreCase("check")) {
            if (strings[1].equalsIgnoreCase("user") && message.getMentionedUsers().size() == 1)
                checkUserPermission(botAPI, em, pm, messageChannel, message);
            else if (strings[1].equalsIgnoreCase("role") && message.getMentionedRoles().size() == 1)
                checkRolePermission(botAPI, em, pm, messageChannel, message);
        }
    }

    private void checkUserPermission(BotAPI botAPI, EmbedMessageManager em, PermissionManager pm, MessageChannel messageChannel, Message message) {
        User user = message.getMentionedUsers().get(0);
        Member member = message.getGuild().getMember(user);
        for (SimpleRank sr : SimpleRank.values()) {
            if (sr.isLowerThan(SimpleRank.JR_MOD) || sr.isHigherThan(SimpleRank.CHIEF_ADMIN))
                continue;
            botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription(String.format(":thinking: **%s** to **%s**? `AtLeast`:`%s`, `Higher`:`%s`, `Lower`:`%s`", user.getName(), sr.getDescription(), pm.userRoleAtLeast(member, sr), pm.userRoleHigherThan(member, sr), pm.userRoleLowerThan(member, sr)), Color.DARK_GRAY));
        }
    }

    private void checkRolePermission(BotAPI botAPI, EmbedMessageManager em, PermissionManager pm, MessageChannel messageChannel, Message message) {
        Role role = message.getMentionedRoles().get(0);
        for (SimpleRank sr : SimpleRank.values()) {
            if (sr.isLowerThan(SimpleRank.JR_MOD))
                continue;
            botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription(String.format(":thinking: **%s** to **%s**? `AtLeast`:`%s`, `Higher`:`%s`, `Lower`:`%s`", role.getName(), sr.getDescription(), pm.roleAtLeast(role, sr), pm.roleHigherThan(role, sr), pm.roleLowerThan(role, sr)), Color.DARK_GRAY));
        }
    }

    private void setUserPermission(BotAPI botAPI, EmbedMessageManager em, PermissionManager pm, MessageChannel messageChannel, Message message, SimpleRank simpleRank) {
        User target = message.getMentionedUsers().get(0);
        pm.setUserPermission(target, simpleRank);
        botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription("done.", Color.DARK_GRAY));
    }

    private void setRolePermission(BotAPI botAPI, EmbedMessageManager em, PermissionManager pm, MessageChannel messageChannel, Message message, SimpleRank simpleRank) {
        Role role = message.getMentionedRoles().get(0);
        pm.setRolePermission(botAPI, role, simpleRank);
        botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription("done.", Color.DARK_GRAY));
    }

    private void setChannelType(BotAPI botAPI, SettingsManager sm, EmbedMessageManager em, MessageChannel messageChannel, Message message, Settings settings) {
        TextChannel tc = message.getMentionedChannels().get(0);
        sm.addSetting(settings, tc.getId());
        botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription("done", Color.DARK_GRAY));
    }
}
