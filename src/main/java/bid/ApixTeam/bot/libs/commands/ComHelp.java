package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import bid.ApixTeam.bot.utils.api.ExtraUtils;
import bid.ApixTeam.bot.utils.api.PermissionManager;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.entites.enums.Settings;
import bid.ApixTeam.bot.utils.vars.entites.enums.SimpleRank;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class ComHelp implements CommandExecutor {
    @Command(aliases = {"help"}, description = "displays the available commands")
    public void onCommand(User user, MessageChannel messageChannel, Message message, String[] strings) {
        BotAPI botAPI = new BotAPI();
        PermissionManager pm = botAPI.getPermissionManager();
        ExtraUtils eu = botAPI.getExtraUtils();
        String command = "help";

        if(eu.isCoolingdown(user, pm, command)){
            botAPI.getMessageManager().sendMessage(messageChannel, eu.getCooldownMessage(user, pm, command));
            return;
        }else
            eu.throwCooldown(user, pm, command, 60);

        EmbedMessageManager embedManager = botAPI.getEmbedMessageManager();
        boolean mod = false, admin = false, botAdmin = false;
        boolean b = messageChannel.getType().isGuild();

        if(pm.userRoleAtLeast(user.getJDA().getGuildById(Lists.getSettings().get(Settings.MAIN_GUILD_ID)).getMember(user), SimpleRank.MOD))
            mod = true;
        if(pm.userRoleAtLeast(user.getJDA().getGuildById(Lists.getSettings().get(Settings.MAIN_GUILD_ID)).getMember(user), SimpleRank.ADMIN))
            admin = true;
        if(pm.userAtLeast(user, SimpleRank.BOT_ADMIN)) {
            mod = true;
            admin = true;
            botAdmin = true;
        }

        try {
            if (strings.length == 0) {
                send(botAPI, user, embedManager.getDefaultHelpEmbed(user));
                if (b)
                    botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription(":white_check_mark: *sent you some help*.. please check your PMs."));
                if(mod)
                    send(botAPI, user, embedManager.getModerationHelpEmbed(user));
                if(admin)
                    send(botAPI, user, embedManager.getAdministrationHelpEmbed(user));

            } else if (strings.length == 1) {
                boolean v = false;
                if(strings[0].equalsIgnoreCase("broadcast") && admin)
                    v = send(botAPI, user, embedManager.getUsage(user, "broadcast", "Announces/broadcasts a message to a certain channel, can be executed once or placed in a loop.", "broadcast (Now|Later|~~Repeat~~) [L|~~R~~ Time] [L|~~R~~ Unit] (#Channel) (Message)", "!announce\n!bc"));
                else if(strings[0].equalsIgnoreCase("clear") && admin)
                    v = send(botAPI, user, embedManager.getUsage(user, "clear", "Clears a set amount of messages.", "clear (number) [<-s>|@mention]", "!clean\n!purge\n!cls"));
                else if (strings[0].equalsIgnoreCase("help"))
                    v = send(botAPI, user, embedManager.getUsage(user, "help", ":rolling_eyes:", "help [Command]", null));
                else if (strings[0].equalsIgnoreCase("info"))
                    v = send(botAPI, user, embedManager.getUsage(user, "info", "Information about various amount of things, such as how ranking works.", "info [ranks/mods/admins]", null));
                else if (strings[0].equalsIgnoreCase("levels"))
                    v = send(botAPI, user, embedManager.getUsage(user, "levels", "Displays the top `5` players on the server", null, "!ranks"));
                else if(strings[0].equalsIgnoreCase("mute") && mod)
                    v = send(botAPI, user, embedManager.getUsage(user, "mute", "Do I really need to explain this?", "mute (@mention[s])", "!shut_up"));
                else if(strings[0].equalsIgnoreCase("tempmute") && mod)
                    v = send(botAPI, user, embedManager.getUsage(user, "tempmute", "Mutes a user for a certain amount of time", "tempmute (delay) (<s/m/h/d>) (@mention[s])", "!temp_mute"));
                else if(strings[0].equalsIgnoreCase("tempban") && admin)
                    v = send(botAPI, user, embedManager.getUsage(user, "tempban", "Bans a user for a certain amount of time", "tempban (delay) (<s/m/h/d>) (@mention[s])", "!temp_ban"));
                else if(strings[0].equalsIgnoreCase("unmute") && mod)
                    v = send(botAPI, user, embedManager.getUsage(user, "unmute", "srsly tho?", "unmute (@mention[s])", "!un_mute"));
                else if (strings[0].equalsIgnoreCase("rank"))
                    v = send(botAPI, user, embedManager.getUsage(user, "rank", "Displays your activity ranking among the server.", "rank [@mention]", "!level"));
                else if (strings[0].equalsIgnoreCase("report"))
                    v = send(botAPI, user, embedManager.getUsage(user, "report", "Report a certain user.", "report (@mention) (reason)", null));
                else if(strings[0].equalsIgnoreCase("settings") && admin) {
                    v = send(botAPI, user, embedManager.getUsage(user, "settings", "Manage the bot settings", "<command> tools profanity add (word) \n<!command> tools profanity remove (word) \n<!command> tools profanity list\n<!command> tools channel ignore (#channel)\n<!command> tools channel un-ignore (#channel)\n<!command> tools channel ignored\n<!command> tools rankup update\n<!command> tools rankup set (level) (@role)", null));
                    if(botAdmin)
                        v = send(botAPI, user, embedManager.getUsage(user, "settings", "Manage the bot settings", "<command> bot set channel (#channel) (type)\n<!command> bot set role (@role) (type)\n<!command> bot check (<user/role>) (@mention/@role)", null));
                }else if(strings[0].equalsIgnoreCase("slowmode") && mod)
                    v = send(botAPI, user, embedManager.getUsage(user, "slowmode", "Slows down the chat, any message that's been sent by a user within the specified amount of time will get deleted.", "slowmode (Time in seconds|<Off>)", "!slowdown"));

                if (b && v)
                    botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription(":white_check_mark: *sent you some help*.. please check your PMs."));
                else if(!v)
                    botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription(":cold_sweat: I don't think you have access to that command."));
            } else
                botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getUsage("!help"));
        } catch (ErrorResponseException e) {
            botAPI.getMessageManager().sendMessage(messageChannel, embedManager.getAsDescription("Umm :cold_sweat: couldn't send you help :sob: maybe it's because your PMs are locked!"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean send(BotAPI botAPI, User user, MessageEmbed messageEmbed) {
        botAPI.getPrivateMessageManager().sendMessage(user, messageEmbed);
        return true;
    }
}
