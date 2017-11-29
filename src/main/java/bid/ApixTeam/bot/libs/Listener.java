package bid.ApixTeam.bot.libs;

import bid.ApixTeam.bot.libs.commands.*;
import bid.ApixTeam.bot.libs.events.GuildMemberLeft;
import bid.ApixTeam.bot.libs.events.MessageReceived;
import bid.ApixTeam.bot.utils.vars.Lists;
import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.JDA3Handler;
import net.dv8tion.jda.core.JDA;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class Listener {
    public Listener(JDA jda) {
        // Events
        jda.addEventListener(new MessageReceived(), new GuildMemberLeft());

        // Commands
        CommandHandler commandHandler = new JDA3Handler(jda);
        commandHandler.setDefaultPrefix("!");
        commandHandler.registerCommand(new ComHelp());
        commandHandler.registerCommand(new ComInfo());
        commandHandler.registerCommand(new ComRank());
        commandHandler.registerCommand(new ComClear());
        commandHandler.registerCommand(new ComSettings());
        commandHandler.registerCommand(new ComMute());
        commandHandler.registerCommand(new ComUnMute());
        commandHandler.registerCommand(new ComLevels());
        commandHandler.registerCommand(new ComUsage());
        commandHandler.registerCommand(new ComSlowmode());
        commandHandler.registerCommand(new ComBroadcast());

        for (CommandHandler.SimpleCommand sm : commandHandler.getCommands())
            for (String s : sm.getCommandAnnotation().aliases())
                Lists.getCommands().add(s);
    }
}
