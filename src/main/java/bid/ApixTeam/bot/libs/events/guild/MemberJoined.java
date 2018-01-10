package bid.ApixTeam.bot.libs.events.guild;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.SettingsManager;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.entites.enums.Settings;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Source-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class MemberJoined extends ListenerAdapter {
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        BotAPI botAPI = new BotAPI();
        SettingsManager sm = botAPI.getSettingsManager();
        Guild guild = event.getGuild();
        User user = event.getUser();

        if(Lists.getMutedUsers().contains(user.getIdLong()))
            guild.getController().addSingleRoleToMember(event.getMember(), guild.getRoleById(sm.getSetting(Settings.ROLES_MUTED))).queue();
    }
}
