package bid.ApixTeam.bot.libs.events;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildMemberLeft extends ListenerAdapter {
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        BotAPI botAPI = new BotAPI();
        EmbedMessageManager embedManager = new EmbedMessageManager();
        Guild guild = event.getGuild();
        MessageChannel channel = guild.getTextChannelById("356094583483924482");
        User user = event.getUser();

        botAPI.getMessageManager().sendMessage(channel, embedManager.getLeaveEmbed(user));
    }
}
