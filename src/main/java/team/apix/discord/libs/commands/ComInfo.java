package team.apix.discord.libs.commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.api.EmbedMessageManager;
import team.apix.discord.utils.api.ExtraUtils;
import team.apix.discord.utils.api.PermissionManager;
import team.apix.discord.utils.vars.Lists;
import team.apix.discord.utils.vars.Messages;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class ComInfo implements CommandExecutor {
    @Command(aliases = "info")
    public void onCommand(User user, MessageChannel messageChannel, String[] strings) {
        BotAPI botAPI = new BotAPI();
        PermissionManager pm = botAPI.getPermissionManager();
        ExtraUtils eu = botAPI.getExtraUtils();
        String command = "info";

        EmbedMessageManager embedManager = new EmbedMessageManager();

        if(eu.siqc(strings, 1, 0, "exp") || eu.siqc(strings, 1, 0, "experience")){
            if(eu.isCoolingdown(user, command)){
                botAPI.getMessageManager().sendMessage(messageChannel, eu.getCooldownMessage(user, command));
                return;
            }else
                eu.throwCooldown(user, pm, command, 60);

            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsInfo(messageChannel, "earning experience", Messages.BOT_INFO_EXP));
            return;
        }else if (strings.length != 0){
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            return;
        }

        if(eu.isCoolingdown(user, command)){
            botAPI.getMessageManager().sendMessage(messageChannel, eu.getCooldownMessage(user, command));
            return;
        }else
            eu.throwCooldown(user, pm, command, 60);

        botAPI.getPrivateMessageManager().sendMessage(user, embedManager.getInfo(messageChannel, botAPI.getExtraUtils().getElapsedTime(Lists.getInitial(), System.currentTimeMillis())));
        if(messageChannel.getType().isGuild())
            botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription(":white_check_mark: *sent you some information*.. please check your PMs."));
    }

    private MessageEmbed getUsage() {
        return new BotAPI().getEmbedMessageManager().getUsage("!info [exp]");
    }
}
