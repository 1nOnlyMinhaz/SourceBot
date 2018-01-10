package team.apix.discord.utils.threads;

import team.apix.discord.utils.vars.Messages;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;

import java.util.Random;
import java.util.TimerTask;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class GameStatue extends TimerTask {
    private JDA jda;

    public GameStatue(JDA jda) {
        this.jda = jda;
    }

    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        int r = new Random().nextInt(2);
        this.jda.getPresence()
                .setGame(r == 0 ? Game.playing(Messages.GAME_PLAYIN[new Random().nextInt(Messages.GAME_PLAYIN.length)])
                : r == 1 ? Game.watching(Messages.GAME_WATCHIN[new Random().nextInt(Messages.GAME_WATCHIN.length)])
                : Game.listening(Messages.GAME_LISTENIN[new Random().nextInt(Messages.GAME_LISTENIN.length)]));
    }
}
