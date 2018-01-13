package team.apix.discord.libs.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;
import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.api.EconomyManager;
import team.apix.discord.utils.api.EmbedMessageManager;
import team.apix.discord.utils.api.ExtraUtils;
import team.apix.discord.utils.vars.Messages;

import java.util.Random;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class ComBalance implements CommandExecutor {
    @Command(aliases = {"balance", "coins"})
    public void onCommand(Guild guild, User user, MessageChannel messageChannel, Message message, String[] strings) {
        BotAPI botAPI = new BotAPI();
        ExtraUtils eu = botAPI.getExtraUtils();
        EconomyManager eco = botAPI.getEcon();
        EmbedMessageManager em = botAPI.getEmbedMessageManager();
        String command = "coins";

        if (strings.length == 1) {
            if (!messageChannel.getType().isGuild()) {
                botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription("You can't do that in PMs at the moment :cold_sweat:"));
                return;
            }

            if (message.getMentionedMembers().size() != 1) {
                botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
                return;
            }

            if (eu.getAsMember(user) == null) {
                botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription(":cold_sweat: um... couldn't find that user :cold_sweat:"));
                return;
            }

            if (message.getMentionedUsers().get(0) == user) {
                if (eu.cooldown(botAPI, messageChannel, user, command, 60))
                    return;

                botAPI.getMessageManager().sendMessage(messageChannel,
                        em.getAsDescription(String.format("You have **%s**",
                                eco.getFormattedBalance(eco.getBalance(user))), user));
                return;
            }else if(message.getMentionedUsers().get(0) == guild.getJDA().getSelfUser()){
                botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription(Messages.BALANCE_BRAGS[new Random().nextInt(Messages.BALANCE_BRAGS.length)]));
                return;
            }

            if (eu.cooldown(botAPI, messageChannel, user, String.format("%s|mention", command), 60))
                return;

            botAPI.getMessageManager().sendMessage(messageChannel,
                    em.getAsDescription(String.format("%s has **%s**", message.getMentionedUsers().get(0).getAsMention(),
                            eco.getFormattedBalance(eco.getBalance(message.getMentionedMembers().get(0).getUser()))), user));
            return;
        } else if (strings.length != 0) {
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            return;
        }

        if (eu.cooldown(botAPI, messageChannel, user, command, 60))
            return;

        botAPI.getMessageManager().sendMessage(messageChannel,
                em.getAsDescription(String.format("You have **%s**",
                        eco.getFormattedBalance(eco.getBalance(user))), user));
    }

    private MessageEmbed getUsage() {
        return new BotAPI().getEmbedMessageManager().getUsage("!coins [@mention]");
    }
}
