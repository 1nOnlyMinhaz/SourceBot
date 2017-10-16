package bid.ApixTeam.bot;

import bid.ApixTeam.bot.libs.Listener;
import bid.ApixTeam.bot.utils.api.SettingsManager;
import bid.ApixTeam.bot.utils.threads.dummy;
import bid.ApixTeam.bot.utils.vars.Levels;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.Timer;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class TSC extends SettingsManager {
    public static void main(String[] args) throws LoginException, InterruptedException, RateLimitedException {
        JDA jda = new JDABuilder(AccountType.BOT)
                .setToken("MzU2MTI1ODc2Njc4NTU3NzI3.DJdAcg._67wYa-oyF7LGUtsW_hwI32Yenk")
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setGame(Game.of("!help for commands."))
                .buildBlocking();

        setup(jda);
        new Levels();
        new Listener(jda);

        Timer timer = new Timer();
        timer.schedule(new dummy(), 0, 1000);
    }
}
