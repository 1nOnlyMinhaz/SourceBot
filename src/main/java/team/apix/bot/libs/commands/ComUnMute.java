package team.apix.bot.libs.commands;

import team.apix.bot.utils.BotAPI;
import team.apix.bot.utils.api.EmbedMessageManager;
import team.apix.bot.utils.api.PermissionManager;
import team.apix.bot.utils.vars.Lists;
import team.apix.bot.utils.vars.entites.enums.Settings;
import team.apix.bot.utils.vars.entites.enums.SimpleRank;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.GuildController;

import java.util.ArrayList;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class ComUnMute implements CommandExecutor {
    @Command(aliases = {"unmute", "un_mute"}, privateMessages = false)
    public void onCommand(Guild guild, User user, MessageChannel messageChannel, Message message, String[] strings) {
        BotAPI botAPI = new BotAPI();
        EmbedMessageManager embedManager = botAPI.getEmbedMessageManager();
        PermissionManager pm = botAPI.getPermissionManager();

        if(!pm.userRoleAtLeast(guild.getMember(user), SimpleRank.MOD)) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getNoComPermission());
            return;
        } else if(strings.length < 1 || message.getMentionedUsers().size() != 1) {
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            return;
        }

        GuildController guildController = guild.getController();
        String s;
        ArrayList<String> arrayList = new ArrayList<>();
        User target = message.getMentionedUsers().get(0);
        guildController.removeSingleRoleFromMember(guild.getMember(target), guild.getRoleById(Lists.getSettings().get(Settings.ROLES_MUTED))).queue();
        if(Lists.getMutedUsers().contains(target.getIdLong())) {
            Lists.getMutedUsers().remove(target.getIdLong());
        } else {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("That user is not muted!"));
            return;
        }
        arrayList.add(target.getAsMention());

        if(arrayList.size() == 2)
            s = String.join(" and ", arrayList);
        else if(arrayList.size() > 2) {
            s = String.join(", ", arrayList);
            String st = s.substring(0, s.lastIndexOf(","));
            if(!st.contains(arrayList.get(arrayList.size() - 1))) {
                st += String.format(" and %s", arrayList.get(arrayList.size() - 1));
                s = st;
            }
        } else s = String.join(", ", arrayList);

        if(s.isEmpty())
            return;

        botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription(String.format("%s has been unmuted!", s)));
    }

    private MessageEmbed getUsage() {
        return EmbedMessageManager.getEmbedMessageManager().getAsDescription("**Incorrect usage!** Correct format: `!unmute {@mention}` :wink:");
    }
}
