package team.apix.discord.libs.commands.defualt;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;
import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.api.ExtraUtils;
import team.apix.discord.utils.api.IncidentManager;
import team.apix.discord.utils.api.SettingsManager;
import team.apix.discord.utils.vars.entites.Incident;
import team.apix.discord.utils.vars.entites.enums.IncidentType;
import team.apix.discord.utils.vars.entites.enums.Settings;

import java.util.concurrent.TimeUnit;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class ComReport implements CommandExecutor {
    @Command(aliases = "report")
    public void onCommand(Guild guild, MessageChannel messageChannel, Message command, User user, String args[]) {
        BotAPI botAPI = new BotAPI();
        ExtraUtils eu = botAPI.getExtraUtils();
        String cmd = "report";

        SettingsManager sm = botAPI.getSettingsManager();
        IncidentManager incidentManager = botAPI.getIncidentManager();

        if(args.length < 2 || command.getMentionedUsers().size() == 0 || !args[0].matches("<@(.*?)>")) {
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            //  botAPI.getMessageManager().deleteMessage(command, "Auto Cleared");
            return;
        }

        if(messageChannel.getType().isGuild()) {
            if(command.getMentionedUsers().get(0).getId().equalsIgnoreCase(command.getAuthor().getId())) {
                botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription("I don't think you do want that to work..."));
                return;
            } else if(command.getMentionedUsers().get(0).getId().equals(command.getJDA().getSelfUser().getId())) {
                botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription("Nice try fam..."));
                return;
            }

            if(eu.cooldown(botAPI, messageChannel, user, cmd, 300))
                return;

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
        } else {
            if(command.getMentionedUsers().get(0).getId().equalsIgnoreCase(command.getAuthor().getId())) {
                botAPI.getPrivateMessageManager().sendMessage(user, botAPI.getEmbedMessageManager().getAsDescription("I don't think you do want that to work..."));
                return;
            } else if(command.getMentionedUsers().get(0).getId().equals(command.getJDA().getSelfUser().getId())) {
                botAPI.getPrivateMessageManager().sendMessage(user, botAPI.getEmbedMessageManager().getAsDescription("Nice try fam..."));
                return;
            }

            if(eu.cooldown(botAPI, messageChannel, user, cmd, 300))
                return;

            Guild guildById = command.getJDA().getGuildById(sm.getSetting(Settings.MAIN_GUILD_ID));
            int counter = 0;
            for(Member member : guildById.getMembers()) {
                if(member.getUser().getIdLong() != command.getMentionedUsers().get(0).getIdLong()) {
                    if(counter == guildById.getMembers().size()) {
                        botAPI.getPrivateMessageManager().sendMessage(user, botAPI.getEmbedMessageManager().getAsDescription("Maybe next time try a user that exists?"));
                    }
                }
            }

            StringBuilder str = new StringBuilder();
            for(int i = 1; i < args.length; i++) {
                str.append(args[i]).append(" ");
            }

            String reason = str.toString().trim();

            int id = incidentManager.createIncident(user, command.getMentionedUsers().get(0), IncidentType.REPORT, reason, 0, TimeUnit.SECONDS);

            Incident incident = incidentManager.getIncident(id);
            Message message = botAPI.getMessageManager().sendMessage(guildById.getTextChannelById(sm.getSetting(Settings.CHAN_REPORTS)),
                    botAPI.getEmbedMessageManager().getIncidentEmbed(guildById.getJDA(), incident));
            incident.setMessageID(message.getIdLong());
            incidentManager.updateIncident(incident);
            botAPI.getPrivateMessageManager().sendMessage(user, botAPI.getEmbedMessageManager().getAsDescription(String.format("You have successfully reported %s!", command.getMentionedUsers().get(0).getAsMention())));
        }
    }

    private MessageEmbed getUsage() {
        return new BotAPI().getEmbedMessageManager().getUsage("!report {@mention) {reason}");
    }
}
