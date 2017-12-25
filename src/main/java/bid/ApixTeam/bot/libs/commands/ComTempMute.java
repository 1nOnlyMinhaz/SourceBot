package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.IncidentManager;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class ComTempMute implements CommandExecutor {
    @Command(aliases = {"tempmute", "temp-mute"}, privateMessages = false)
    public void onCommand(Guild guild, User user, MessageChannel messageChannel, Message message, String[] strings){
        BotAPI botAPI = new BotAPI();
        IncidentManager incidentManager = botAPI.getIncidentManager();
    }
}
