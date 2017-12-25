package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.IncidentManager;
import bid.ApixTeam.bot.utils.api.SettingsManager;
import bid.ApixTeam.bot.utils.vars.entites.Incident;
import bid.ApixTeam.bot.utils.vars.entites.enums.IncidentType;
import bid.ApixTeam.bot.utils.vars.entites.enums.Settings;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;

import java.util.concurrent.TimeUnit;

public class ComReport implements CommandExecutor {
    @Command(aliases = "report")
    public void onCommand(Guild guild, MessageChannel messageChannel, Message command, User user, String args[]) {
        BotAPI botAPI = new BotAPI();
        SettingsManager sm = botAPI.getSettingsManager();
        IncidentManager incidentManager = botAPI.getIncidentManager();

        if(args.length < 2 || command.getMentionedUsers().size() == 0 || !args[0].matches("<@(.*?)>")) {
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            //  botAPI.getMessageManager().deleteMessage(command, "Auto Cleared");
            return;
        }

        if(command.getMentionedUsers().get(0).getId().equalsIgnoreCase(command.getAuthor().getId())) {
            botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription("I don't think you do want that to work..."));
            return;
        }else if(command.getMentionedUsers().get(0).getId().equals(command.getJDA().getSelfUser().getId())) {
            botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription("Nice try fam..."));
            return;
        }

        StringBuilder str = new StringBuilder();
        for(int i = 1; i < args.length; i++) {
            str.append(args[i]).append(" ");
        }

        String reason = str.toString().trim();

        int id = incidentManager.createIncident(user, command.getMentionedUsers().get(0), IncidentType.REPORT, reason, 0, TimeUnit.SECONDS);

        Incident incident = incidentManager.getIncident(id);
        Message message = botAPI.getMessageManager().sendMessage(guild.getTextChannelById(sm.getSetting(Settings.CHAN_REPORTS)),
                botAPI.getEmbedMessageManager().getIncidentEmbed(guild.getJDA(), incident));
        incident.setMessageID(message.getIdLong());
        incidentManager.updateIncident(incident);
        botAPI.getMessageManager().deleteMessage(command, "Auto Cleared");
    }

    private MessageEmbed getUsage() {
        return new BotAPI().getEmbedMessageManager().getUsage("!report {@mention) {reason}");
    }
}
