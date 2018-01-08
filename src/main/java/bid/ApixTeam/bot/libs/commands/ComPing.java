package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.ExtraUtils;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * TSC was created by ApixTeam (C) 2018
 * in association with TheSourceCode (C) 2018
 */
public class ComPing implements CommandExecutor {
    @Command(aliases = "ping")
    public void onCommand(User user, MessageChannel messageChannel, Message message){
        BotAPI botAPI = new BotAPI();
        ExtraUtils eu = botAPI.getExtraUtils();
        String command = "ping";

        if(eu.isCoolingdown(user, command)){
            botAPI.getMessageManager().sendMessage(messageChannel, eu.getCooldownMessage(user, command));
            return;
        }else
            eu.throwCooldown(user, command, 30);

        botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription("Pong!"));
    }
}
