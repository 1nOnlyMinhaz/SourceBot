package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import bid.ApixTeam.bot.utils.api.IncidentManager;
import bid.ApixTeam.bot.utils.api.PermissionManager;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.entites.Incident;
import bid.ApixTeam.bot.utils.vars.entites.enums.IncidentType;
import bid.ApixTeam.bot.utils.vars.entites.enums.Settings;
import bid.ApixTeam.bot.utils.vars.entites.enums.SimpleRank;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.GuildController;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class ComMute implements CommandExecutor {
    @Command(aliases = {"mute", "shut_up"}, privateMessages = false)
    public void onCommand(Guild guild, User user, MessageChannel messageChannel, Message message, String[] strings) {
        BotAPI botAPI = new BotAPI();
        EmbedMessageManager embedManager = botAPI.getEmbedMessageManager();
        PermissionManager pm = botAPI.getPermissionManager();
        IncidentManager incidentManager = botAPI.getIncidentManager();

        if(!pm.userRoleAtLeast(guild.getMember(user), SimpleRank.MOD)) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getNoComPermission());
            return;
        } else if(strings.length < 2 || message.getMentionedUsers().size() != 1) {
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            return;
        }

        GuildController guildController = guild.getController();

        User target = message.getMentionedUsers().get(0);
        if (canMute(guild, user, messageChannel, botAPI, embedManager, pm, target)) return;

        StringBuilder str = new StringBuilder();
        for(int i = 1; i < strings.length; i++) {
            str.append(strings[i]).append(" ");
        }
        String reason = str.toString().trim();

        int id = incidentManager.createIncident(user, target, IncidentType.MUTE, reason, 0, TimeUnit.SECONDS);

        String s;
        ArrayList<String> arrayList = new ArrayList<>();

        guildController.addSingleRoleToMember(guild.getMember(target), guild.getRoleById(Lists.getSettings().get(Settings.ROLES_MUTED))).queue();
        if(!Lists.getMutedUsers().contains(target.getIdLong())) {
            Lists.getMutedUsers().add(target.getIdLong());
        } else {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("That user is already muted!"));
            return;
        }
        arrayList.add(target.getAsMention());

        Message incidentMessage = botAPI.getMessageManager()
                .sendMessage(guild.getTextChannelById(botAPI.getSettingsManager().getSetting(Settings.CHAN_INCIDENTS)),
                        embedManager.getIncidentEmbed(guild.getJDA(), incidentManager.getIncident(id)));

        Incident incident = incidentManager.getIncident(id);
        incident.setMessageID(incidentMessage.getIdLong());
        incidentManager.updateIncident(incident);

        s = separateUsers(arrayList);

        if(s.isEmpty()) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription(message.getMentionedUsers().size() == 1 ? "You cannot mute that person." : "You cannot mute those people."));
            return;
        }

        botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription(String.format("%s %s been muted!", s, message.getMentionedUsers().size() > 1 ? "have" : "has")));
    }

    static String separateUsers(ArrayList<String> arrayList) {
        String s;
        if(arrayList.size() == 2)
            s = String.join(" and ", arrayList);
        else if(arrayList.size() > 2) {
            s = String.join(", ", arrayList);
            String st = s.substring(0, s.lastIndexOf(","));
            if(!st.contains(arrayList.get(arrayList.size() - 1))) {
                st += " and " + arrayList.get(arrayList.size() - 1);
                s = st;
            }
        } else s = String.join(", ", arrayList);
        return s;
    }

    static boolean canMute(Guild guild, User user, MessageChannel messageChannel, BotAPI botAPI, EmbedMessageManager embedManager, PermissionManager pm, User target) {
        if(pm.userRoleAtLeast(guild.getMember(target), SimpleRank.MOD) && !pm.userRoleAtLeast(guild.getMember(user), SimpleRank.ADMIN)) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("You cannot mute this person!"));
            return true;
        } else if(target == guild.getJDA().getSelfUser()) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("Nice try nerd"));
            return true;
        }
        return false;
    }

    private MessageEmbed getUsage() {
        return EmbedMessageManager.getEmbedMessageManager().getAsDescription("**Incorrect usage!** Correct format: `!mute {@mention} {reason}` :wink:");
    }
}
