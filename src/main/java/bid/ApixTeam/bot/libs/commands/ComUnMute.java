package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import bid.ApixTeam.bot.utils.api.PermissionManager;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.entites.enums.Settings;
import bid.ApixTeam.bot.utils.vars.entites.enums.SimpleRank;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.managers.GuildController;

import java.util.ArrayList;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class ComUnMute implements CommandExecutor {
    @Command(aliases = {"unmute", "un_mute"}, privateMessages = false)
    public void onCommand(Guild guild, User user, MessageChannel messageChannel, Message message, String[] strings) {
        BotAPI botAPI = new BotAPI();
        EmbedMessageManager embedManager = botAPI.getEmbedMessageManager();
        PermissionManager pm = botAPI.getPermissionManager();

        if (!pm.userRoleAtLeast(guild.getMember(user), SimpleRank.SR_MOD)) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getNoComPermission());
            return;
        } else if (strings.length < 1 && message.getMentionedUsers().size() < 1) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("Please make sure to mention at least one user that should get unmuted!"));
            return;
        }

        GuildController guildController = guild.getController();
        String s;
        ArrayList<String> arrayList = new ArrayList<>();
        for (User target : message.getMentionedUsers()) {
            guildController.removeSingleRoleFromMember(guild.getMember(target), guild.getRoleById(Lists.getSettings().get(Settings.ROLES_MUTED))).queue();
            if (Lists.getMutedUsers().contains(target.getIdLong()))
                Lists.getMutedUsers().remove(target.getIdLong());
            arrayList.add(target.getAsMention());
        }

        if (arrayList.size() == 2)
            s = String.join(" and ", arrayList);
        else if (arrayList.size() > 2) {
            s = String.join(", ", arrayList);
            String st = s.substring(0, s.lastIndexOf(","));
            if (!st.contains(arrayList.get(arrayList.size() - 1))) {
                st += String.format(" and %s", arrayList.get(arrayList.size() - 1));
                s = st;
            }
        } else s = String.join(", ", arrayList);

        if (s.isEmpty())
            return;

        botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription(String.format("%s %s been unmuted!", s, message.getMentionedUsers().size() > 1 ? "have" : "has")));
    }
}
