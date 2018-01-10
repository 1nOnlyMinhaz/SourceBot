package team.apix.bot;

import team.apix.bot.libs.Listener;
import team.apix.bot.utils.api.IncidentManager;
import team.apix.bot.utils.api.SettingsManager;
import team.apix.bot.utils.threads.GameStatue;
import team.apix.bot.utils.threads.dummy;
import team.apix.bot.utils.vars.Levels;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.Timer;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class Source extends SettingsManager {
    public static void main(String[] args) throws LoginException, InterruptedException, RateLimitedException {
        JDA jda = new JDABuilder(AccountType.BOT)
                .setToken(args.length == 1 ? args[0] : "MzU2MTI1ODc2Njc4NTU3NzI3.DJdAcg._67wYa-oyF7LGUtsW_hwI32Yenk")
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .buildBlocking();

        setup(jda);
        new IncidentManager().setup();
        new Levels();
        new Listener(jda);

        Timer timer = new Timer();
        timer.schedule(new dummy(jda), 0, 1000);
        timer.schedule(new GameStatue(jda), 0, 10800000);
    }
}
