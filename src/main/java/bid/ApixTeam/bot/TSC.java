package bid.ApixTeam.bot;

import bid.ApixTeam.bot.libs.Listener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

/**
 * TSC was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class TSC {
    public static void main(String[] args) throws LoginException, InterruptedException, RateLimitedException {
        JDA jda = new JDABuilder(AccountType.BOT)
                .setToken("")
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setGame(Game.of("!help for commands"))
                .buildBlocking();

        new Listener(jda);
    }
}
