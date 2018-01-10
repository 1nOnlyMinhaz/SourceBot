package team.apix.discord.utils.threads;

import team.apix.discord.utils.api.IncidentManager;
import team.apix.discord.utils.api.SettingsManager;
import team.apix.discord.utils.vars.Lists;
import team.apix.discord.utils.vars.entites.Incident;
import team.apix.discord.utils.vars.entites.enums.IncidentType;
import team.apix.discord.utils.vars.entites.enums.Settings;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;

import java.util.TimerTask;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class dummy extends TimerTask {
    private JDA jda;

    public dummy(JDA jda) {
        this.jda = jda;
    }

    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        try {
            if (Lists.getIncidentLog().size() >= 1) {
                for (int id : Lists.getIncidentLog().keySet()) {
                    Incident incident = IncidentManager.getIncidentManager().getIncident(id);
                    if (!incident.isRunning())
                        continue;

                    IncidentType type = IncidentType.valueOf(incident.getType());

                    long seconds = ((incident.getSystime() / 1000) + (incident.getDelay() / 1000)) - (System.currentTimeMillis() / 1000);
                    if (seconds > 1)
                        continue;

                    Guild guild = jda.getGuildById(SettingsManager.getSettingsManager().getSetting(Settings.MAIN_GUILD_ID));
                    if (type.equals(IncidentType.TEMP_MUTE))
                        guild.getController().removeSingleRoleFromMember(guild.getMemberById(incident.getU2()),
                                jda.getRoleById(SettingsManager.getSettingsManager().getSetting(Settings.ROLES_MUTED))).queue();
                    else if (type.equals(IncidentType.TEMP_BAN))
                        guild.getController().unban(String.valueOf(incident.getU2())).queue();

                    incident.setRunning(false);
                    IncidentManager.getIncidentManager().updateIncident(incident);
                }
            }
        } catch (Exception ignored){
            // ignored
        }
    }
}
