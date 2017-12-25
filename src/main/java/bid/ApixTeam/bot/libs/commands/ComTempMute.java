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
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.managers.GuildController;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class ComTempMute implements CommandExecutor {
    @Command(aliases = {"tempmute", "temp-mute"}, privateMessages = false)
    public void onCommand(Guild guild, User user, MessageChannel messageChannel, Message message, String[] strings) {
        BotAPI botAPI = new BotAPI();
        IncidentManager incidentManager = botAPI.getIncidentManager();
        EmbedMessageManager embedManager = botAPI.getEmbedMessageManager();
        PermissionManager pm = botAPI.getPermissionManager();

        if (!pm.userRoleAtLeast(guild.getMember(user), SimpleRank.MOD)) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getNoComPermission());
            return;
        } else if (strings.length < 3 && message.getMentionedUsers().size() < 1) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("Please make sure to mention at least one user that should get muted!"));
            return;
        }

        try {
            int delay = Integer.parseInt(strings[0]);
            TimeUnit timeUnit;
            if (strings[1].equalsIgnoreCase("m"))
                timeUnit = TimeUnit.MINUTES;
            else if (strings[1].equalsIgnoreCase("h"))
                timeUnit = TimeUnit.HOURS;
            else if (strings[1].equalsIgnoreCase("d"))
                timeUnit = TimeUnit.DAYS;
            else
                timeUnit = TimeUnit.SECONDS;

            GuildController guildController = guild.getController();
            String s;
            ArrayList<String> arrayList = new ArrayList<>();
            for (User target : message.getMentionedUsers()) {
                if (pm.userAtLeast(target, SimpleRank.MOD) || pm.userRoleAtLeast(guild.getMember(target), SimpleRank.MOD) || target == guild.getJDA().getSelfUser())
                    continue;

                int id = incidentManager.createIncident(user, target, IncidentType.TEMP_MUTE, "N/A", delay, timeUnit);

                guildController.addSingleRoleToMember(guild.getMember(target), guild.getRoleById(Lists.getSettings().get(Settings.ROLES_MUTED))).queue();
                if (!Lists.getMutedUsers().contains(target.getIdLong()))
                    Lists.getMutedUsers().add(target.getIdLong());
                arrayList.add(target.getAsMention());

                Message incidentMessage = botAPI.getMessageManager()
                        .sendMessage(guild.getTextChannelById(botAPI.getSettingsManager().getSetting(Settings.CHAN_REPORTS)),
                        embedManager.getIncidentEmbed(guild.getJDA(), incidentManager.getIncident(id)));

                Incident incident = incidentManager.getIncident(id);
                incident.setMessageID(incidentMessage.getIdLong());
                incidentManager.updateIncident(incident);
            }

            s = ComMute.getMuteString(arrayList);

            if (s.isEmpty()) {
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription(message.getMentionedUsers().size() == 1 ? "You cannot mute that person." : "You cannot mute those people."));
                return;
            }

            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription(String.format("%s %s been muted!", s, message.getMentionedUsers().size() > 1 ? "have" : "has")));
        }catch (NumberFormatException e){
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("The first parameter isn't a numeral value :rage:"));
        }
    }
}
