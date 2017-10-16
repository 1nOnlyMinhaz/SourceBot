package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import bid.ApixTeam.bot.utils.api.PermissionManager;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.enums.SimpleRank;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class ComSlowmode implements CommandExecutor {
    @Command(aliases = {"slowmode"}, privateMessages = false)
    public void onCommand(Guild guild, User user, MessageChannel messageChannel, Object[] objects){
        BotAPI botAPI = new BotAPI();
        PermissionManager pm = botAPI.getPermissionManager();
        EmbedMessageManager embedManager = botAPI.getEmbedMessageManager();

        if(!pm.userRoleAtLeast(guild.getMember(user), SimpleRank.SR_MOD)){
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getNoComPermission());
            return;
        }

        if(objects.length != 1){
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getUsage("!slowmode {TIME_IN_SECONDS|OFF}"));
            return;
        }

        if(objects[0] instanceof Integer){
            if(Lists.getSlowmodeChannelCooldown().containsKey(messageChannel.getIdLong())){
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("This channel is already in slowmode"));
                return;
            }

            Lists.getSlowmodeChannelCooldown().put(messageChannel.getIdLong(), (Integer) objects[0]);
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("This channel is in slowmode now :upside_down:"));
        }else if(objects[0].toString().equalsIgnoreCase("off")){
            if(!Lists.getSlowmodeChannelCooldown().containsKey(messageChannel.getIdLong())){
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("This channel isn't in slowmode"));
                return;
            }

            Lists.getSlowmodeChannelCooldown().remove(messageChannel.getIdLong());
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("This channel is not in slowmode anymore"));
        }else
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getUsage("!slowmode {TIME_IN_SECONDS|OFF}"));
    }
}
