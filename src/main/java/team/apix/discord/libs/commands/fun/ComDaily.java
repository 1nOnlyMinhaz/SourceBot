package team.apix.discord.libs.commands.fun;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.api.ExtraUtils;
import team.apix.discord.utils.api.SettingsManager;
import team.apix.discord.utils.vars.entites.enums.Settings;

import java.util.concurrent.TimeUnit;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class ComDaily implements CommandExecutor {
    @Command(aliases = {"daily", "dailies"})
    public void onCommand(User user, MessageChannel messageChannel, Message message, String[] strings){
        BotAPI botAPI = new BotAPI();
        ExtraUtils eu = botAPI.getExtraUtils();
        SettingsManager sm = botAPI.getSettingsManager();
        String command = "dailies";

        if (eu.isntInChannel(messageChannel, sm, Settings.CHAN_RANK_CHECK))
            return;

        if(strings.length != 0){
            botAPI.getMessageManager().sendMessageQueue(messageChannel, getUsage());
            return;
        }

        if (eu.forceCooldown(botAPI, messageChannel, user, command, (int) eu.toSeconds(TimeUnit.DAYS, 1)))
            return;

        botAPI.getMessageManager().sendMessageQueue(messageChannel, "rewarded for today! comeback tomorrow for more!");
    }

    private MessageEmbed getUsage() {
        return new BotAPI().getEmbedMessageManager().getUsage("!daily");
    }
}
