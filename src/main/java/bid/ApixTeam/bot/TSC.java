package bid.ApixTeam.bot;

import bid.ApixTeam.bot.libs.Listener;
import bid.ApixTeam.bot.utils.api.IncidentManager;
import bid.ApixTeam.bot.utils.api.SettingsManager;
import bid.ApixTeam.bot.utils.threads.dummy;
import bid.ApixTeam.bot.utils.vars.Levels;
import bid.ApixTeam.bot.utils.vars.Messages;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.Random;
import java.util.Timer;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class TSC extends SettingsManager {
    public static void main(String[] args) throws LoginException, InterruptedException, RateLimitedException {
        int r = new Random().nextInt(2);
        JDA jda = new JDABuilder(AccountType.BOT)
                .setToken(args.length == 1 ? args[0] : "MzU2MTI1ODc2Njc4NTU3NzI3.DJdAcg._67wYa-oyF7LGUtsW_hwI32Yenk")
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setGame(r == 0 ? Game.playing(Messages.GAME_PLAYIN[new Random().nextInt(Messages.GAME_PLAYIN.length)])
                        : r == 1 ? Game.watching(Messages.GAME_WATCHIN[new Random().nextInt(Messages.GAME_WATCHIN.length)])
                        : Game.listening(Messages.GAME_LISTENIN[new Random().nextInt(Messages.GAME_LISTENIN.length)]))
                .buildBlocking();

        setup(jda);
        new IncidentManager().setup();
        new Levels();
        new Listener(jda);

        Timer timer = new Timer();
        timer.schedule(new dummy(jda), 0, 1000);
    }
}
