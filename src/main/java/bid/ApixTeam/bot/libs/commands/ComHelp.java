package bid.ApixTeam.bot.libs.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class ComHelp implements CommandExecutor {
    @Command(aliases = {"help", "commands"}, description = "displays the available commands", async = true)
    public void onCommand(Guild guild, MessageChannel messageChannel, Message message, Object[] objects){

    }
}
