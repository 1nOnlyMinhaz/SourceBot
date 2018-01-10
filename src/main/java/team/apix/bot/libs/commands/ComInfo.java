package team.apix.bot.libs.commands;

import team.apix.bot.utils.BotAPI;
import team.apix.bot.utils.api.EmbedMessageManager;
import team.apix.bot.utils.api.ExtraUtils;
import team.apix.bot.utils.api.PermissionManager;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class ComInfo implements CommandExecutor {
    @Command(aliases = "info")
    public void onCommand(User user, MessageChannel messageChannel, Object[] objects) {
        BotAPI botAPI = new BotAPI();
        PermissionManager pm = botAPI.getPermissionManager();
        ExtraUtils eu = botAPI.getExtraUtils();
        String command = "info";

        if(eu.isCoolingdown(user, pm, command)){
            botAPI.getMessageManager().sendMessage(messageChannel, eu.getCooldownMessage(user, pm, command));
            return;
        }else
            eu.throwCooldown(user, pm, command, 60);

        EmbedMessageManager embedManager = new EmbedMessageManager();

        if(objects.length != 0) {
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            return;
        }

        botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getInfoEmbed(messageChannel));
        if(messageChannel.getType().isGuild()) {
            botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription(":white_check_mark: *sent you some information*.. please check your PMs."));
        }
    }

    private MessageEmbed getUsage() {
        return new BotAPI().getEmbedMessageManager().getUsage("!info");
    }
}
