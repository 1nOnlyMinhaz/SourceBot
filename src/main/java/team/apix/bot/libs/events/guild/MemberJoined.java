package team.apix.bot.libs.events.guild;

import team.apix.bot.utils.BotAPI;
import team.apix.bot.utils.api.SettingsManager;
import team.apix.bot.utils.vars.Lists;
import team.apix.bot.utils.vars.entites.enums.Settings;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
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
