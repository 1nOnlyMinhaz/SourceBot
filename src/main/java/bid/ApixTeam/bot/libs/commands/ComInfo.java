package bid.ApixTeam.bot.libs.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class ComInfo implements CommandExecutor {
    @Command(aliases = "info")
    public void onCommand(Guild guild, User user, MessageChannel messageChannel, Message message, Object[] objects){

    }
}
