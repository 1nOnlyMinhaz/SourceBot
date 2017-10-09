package bid.ApixTeam.bot.libs.events;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

public class GuildMemberLeft extends ListenerAdapter {
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        BotAPI botAPI = new BotAPI();

        EmbedMessageManager embedManager = new EmbedMessageManager();

        Guild guild = event.getGuild();
        MessageChannel channel = guild.getTextChannelById("356094583483924482");
        User user = event.getUser();

        Random random = new Random();
        float percent = random.nextFloat();
        percent = 1 - percent;

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        percent = Float.parseFloat(df.format(percent));

        String leaveMessage = String.format("Oh no! :cry: <@%s> left the server!", user.getId());
        String leaveMessage2 = String.format("RIP! <@%s> abandoned us!", user.getId());
        String leaveMessage3 = String.format("Cya! <@%s> left us out to dry!", user.getId());
        String leaveMessage4 = String.format("<@%s> has left the game", user.getId());
        String leaveMessage5 = String.format("Later! <@%s> just rage quit!", user.getId());

        if(percent >= 0 && percent <= 0.20F)
            botAPI.getMessageManager().sendMessage(channel, embedManager.getAsDescription(leaveMessage));
        else if(percent >= 0.21F && percent <= 0.4F)
            botAPI.getMessageManager().sendMessage(channel, embedManager.getAsDescription(leaveMessage2));
        else if(percent >= 0.41F && percent <= 0.6F)
            botAPI.getMessageManager().sendMessage(channel, embedManager.getAsDescription(leaveMessage3));
        else if(percent >= 0.61F && percent <= 0.8F)
            botAPI.getMessageManager().sendMessage(channel, embedManager.getAsDescription(leaveMessage4));
        else if(percent >= 0.81F && percent <= 1F)
            botAPI.getMessageManager().sendMessage(channel, embedManager.getAsDescription(leaveMessage5));
        System.out.println(percent);
    }
}
