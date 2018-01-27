package team.apix.discord;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import team.apix.discord.libs.Listener;
import team.apix.discord.utils.api.EconomyManager;
import team.apix.discord.utils.api.IncidentManager;
import team.apix.discord.utils.api.SettingsManager;
import team.apix.discord.utils.connection.SQLite3;
import team.apix.discord.utils.threads.GameStatue;
import team.apix.discord.utils.threads.dummy;
import team.apix.discord.utils.vars.Levels;
import team.apix.discord.utils.vars.Lists;

import javax.security.auth.login.LoginException;
import java.util.Timer;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class SourceBot extends SettingsManager {
    public static void main(String[] args) throws LoginException, InterruptedException, RateLimitedException {
        JDA jda = new JDABuilder(AccountType.BOT)
                .setToken(args.length == 1 ? args[0] : "MzU2MTI1ODc2Njc4NTU3NzI3.DJdAcg._67wYa-oyF7LGUtsW_hwI32Yenk")
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setBulkDeleteSplittingEnabled(true)
                .buildBlocking();

        Lists.setInitial(System.currentTimeMillis());

        setup(jda);
        new IncidentManager().setup();
        new EconomyManager().setup();
        new Levels();
        
        SQLite3 log = new SQLite3();
        log.createTables();
        log.setupCooldowns();
        
        new Listener(jda);
        
        Timer timer = new Timer();
        timer.schedule(new dummy(jda), 0, 1000);
        timer.schedule(new GameStatue(jda), 0, 10800000);
    }
}
