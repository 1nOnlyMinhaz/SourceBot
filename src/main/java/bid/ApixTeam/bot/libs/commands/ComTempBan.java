package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import bid.ApixTeam.bot.utils.api.IncidentManager;
import bid.ApixTeam.bot.utils.api.PermissionManager;
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
 * Source-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class ComTempBan implements CommandExecutor {
    @Command(aliases = {"tempban", "temp-ban"}, privateMessages = false)
    public void onCommand(Guild guild, User user, MessageChannel messageChannel, Message message, String[] strings) {
        BotAPI botAPI = new BotAPI();
        IncidentManager incidentManager = botAPI.getIncidentManager();
        EmbedMessageManager embedManager = botAPI.getEmbedMessageManager();
        PermissionManager pm = botAPI.getPermissionManager();

        if(!pm.userRoleAtLeast(guild.getMember(user), SimpleRank.ADMIN)) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getNoComPermission());
            return;
        } else if(message.getMentionedUsers().size() != 1) {
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            return;
        } else if(strings.length < 4) {
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            return;
        }

        try {
            int delay;
            try {
                delay = Integer.parseInt(strings[0]);
            } catch (NumberFormatException e) {
                botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
                return;
            }

            TimeUnit timeUnit;
            if(strings[1].equalsIgnoreCase("m"))
                timeUnit = TimeUnit.MINUTES;
            else if(strings[1].equalsIgnoreCase("h"))
                timeUnit = TimeUnit.HOURS;
            else if(strings[1].equalsIgnoreCase("d")) {
                timeUnit = TimeUnit.DAYS;
            } else {
                timeUnit = TimeUnit.SECONDS;
            }

            GuildController guildController = guild.getController();

            User target = message.getMentionedUsers().get(0);
            if(pm.userRoleAtLeast(guild.getMember(target), SimpleRank.ADMIN)) {
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("You cannot ban this person!"));
                return;
            } else if(target == guild.getJDA().getSelfUser()) {
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("Nice try nerd"));
                return;
            }

            StringBuilder str = new StringBuilder();
            for(int i = 3; i < strings.length; i++) {
                str.append(strings[i]).append(" ");
            }
            String reason = str.toString().trim();

            int id = incidentManager.createIncident(user, target, IncidentType.TEMP_BAN, reason, delay, timeUnit);

            String s;
            ArrayList<String> arrayList = new ArrayList<>();

            guildController.ban(target, 1).reason(reason).queue();
            arrayList.add(target.getAsMention());

            Message incidentMessage = botAPI.getMessageManager()
                    .sendMessage(guild.getTextChannelById(botAPI.getSettingsManager().getSetting(Settings.CHAN_INCIDENTS)),
                            embedManager.getIncidentEmbed(guild.getJDA(), incidentManager.getIncident(id)));

            Incident incident = incidentManager.getIncident(id);
            incident.setMessageID(incidentMessage.getIdLong());
            incidentManager.updateIncident(incident);

            s = ComMute.separateUsers(arrayList);

            if (s.isEmpty()) {
                botAPI.getMessageManager().sendMessage(messageChannel, "You cannot ban that person.");
                return;
            }

            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription(String.format("%s %s been temporarily banned!", s, message.getMentionedUsers().size() > 1 ? "have" : "has")));
        } catch (NumberFormatException e) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("The first parameter isn't a numeral value :rage:"));
        }
    }

    private MessageEmbed getUsage() {
        return EmbedMessageManager.getEmbedMessageManager().getAsDescription("**Incorrect usage!** Correct format: `!tempban {time} {s|m|h|d} {@mention} {reason}` :wink:");
    }
}
