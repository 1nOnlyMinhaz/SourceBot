package team.apix.discord.libs.events.guild;

import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.api.EmbedMessageManager;
import team.apix.discord.utils.api.SettingsManager;
import team.apix.discord.utils.vars.entites.enums.Settings;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class MemberLeft extends ListenerAdapter {
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        BotAPI botAPI = new BotAPI();
        EmbedMessageManager embedManager = botAPI.getEmbedMessageManager();
        SettingsManager settings = botAPI.getSettingsManager();
        Guild guild = event.getGuild();
        MessageChannel channel = settings.getSetting(Settings.CHAN_WELCOME) != null ? guild.getTextChannelById(settings.getSetting(Settings.CHAN_WELCOME)) : guild.getTextChannelById("356094583483924482");
        User user = event.getUser();

        botAPI.getMessageManager().sendMessage(channel, embedManager.getLeaveEmbed(user));
    }
}
