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
        } else if(strings.length < 3 && message.getMentionedUsers().size() < 1) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("Please make sure to mention at least one user that should get tempbanned!"));
            return;
        }

        try {
            int delay = Integer.parseInt(strings[0]);
            TimeUnit timeUnit;
            if(strings[1].equalsIgnoreCase("m"))
                timeUnit = TimeUnit.MINUTES;
            else if(strings[1].equalsIgnoreCase("h"))
                timeUnit = TimeUnit.HOURS;
            else if(strings[1].equalsIgnoreCase("d"))
                timeUnit = TimeUnit.DAYS;
            else
                timeUnit = TimeUnit.SECONDS;

            GuildController guildController = guild.getController();
            String s;
            ArrayList<String> arrayList = new ArrayList<>();
            for(User target : message.getMentionedUsers()) {
                if(pm.userAtLeast(target, SimpleRank.ADMIN) || pm.userRoleAtLeast(guild.getMember(target), SimpleRank.ADMIN) || target == guild.getJDA().getSelfUser())
                    continue;

                int id = incidentManager.createIncident(user, target, IncidentType.TEMP_BAN, String.format("Temporarily banned by %s#%s", user.getName(), user.getDiscriminator()), delay, timeUnit);

                guildController.ban(target, 1).reason(String.format("Temporarily banned by %s#%s", user.getName(), user.getDiscriminator())).queue();
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
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription(message.getMentionedUsers().size() == 1 ? "You cannot ban that person." : "You cannot ban those people."));
                return;
            }

            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription(String.format("%s %s been temporarily banned!", s, message.getMentionedUsers().size() > 1 ? "have" : "has")));
        } catch (NumberFormatException e) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("The first parameter isn't a numeral value :rage:"));
        }
    }
}
