package team.apix.discord.libs;

import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.JDA3Handler;
import net.dv8tion.jda.core.JDA;
import team.apix.discord.libs.commands.*;
import team.apix.discord.libs.events.guild.MemberBanned;
import team.apix.discord.libs.events.guild.MemberJoined;
import team.apix.discord.libs.events.guild.MemberLeft;
import team.apix.discord.libs.events.guild.MessageReceived;
import team.apix.discord.libs.events.user.AvatarUpdate;
import team.apix.discord.libs.events.user.NameUpdate;
import team.apix.discord.utils.vars.Lists;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class Listener {
    public Listener(JDA jda) {
        // Events
        jda.addEventListener(new MessageReceived(),
                new MemberLeft(), new MemberBanned(),
                new AvatarUpdate(), new NameUpdate(),
                new MemberJoined());

        // Commands
        CommandHandler commandHandler = new JDA3Handler(jda);
        commandHandler.setDefaultPrefix("!");
        commandHandler.registerCommand(new ComHelp());
        commandHandler.registerCommand(new ComInfo());
        commandHandler.registerCommand(new ComRank());
        commandHandler.registerCommand(new ComClear());
        commandHandler.registerCommand(new ComSettings());
        commandHandler.registerCommand(new ComMute());
        commandHandler.registerCommand(new ComTempMute());
        commandHandler.registerCommand(new ComUnMute());
        commandHandler.registerCommand(new ComLevels());
        commandHandler.registerCommand(new ComSlowmode());
        commandHandler.registerCommand(new ComBroadcast());
        commandHandler.registerCommand(new ComReport());
        commandHandler.registerCommand(new ComUrbanDictionary());
        commandHandler.registerCommand(new ComTempBan());
        commandHandler.registerCommand(new ComPing());
        //commandHandler.registerCommand(new ComBalance());
        commandHandler.registerCommand(new ComBrainfuck());

        for (CommandHandler.SimpleCommand sm : commandHandler.getCommands())
            for (String s : sm.getCommandAnnotation().aliases())
                Lists.getCommands().add(s);
    }
}
