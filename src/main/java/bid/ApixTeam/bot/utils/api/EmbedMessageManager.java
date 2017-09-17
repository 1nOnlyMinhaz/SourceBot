package bid.ApixTeam.bot.utils.api;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.RankingType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class EmbedMessageManager {
    private static EmbedMessageManager embedMessageManager = new EmbedMessageManager();
    private String[] brags = {"Try and keep up.", "pffftt, gud one.", "I'm always in the top.", "ez.", "huh, too eazy.", "Is this ez mode?", "Over 9000!"};
    private String[] bots = {"Ew, ban that thing", "GET THAT THING OUTTA HERE!", "reminds me of stupid mee6", "boooooooooooooooriingg", "if you ask me, i'd rather ban it.", "pfffft, I'm much better."};

    public static EmbedMessageManager getEmbedMessageManager() {
        return embedMessageManager;
    }

    public MessageEmbed getRankEmbed(BotAPI botAPI, User user) {
        HashMap<RankingType, Integer> userRanking = botAPI.getDatabaseManager().getUserRankings(user);
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(new Color(234, 255, 235));

        if (user == user.getJDA().getSelfUser()) {
            embedBuilder.setDescription(brags[new Random().nextInt(brags.length)]);
            return embedBuilder.build();
        } else if (user.isBot()) {
            embedBuilder.setDescription(bots[new Random().nextInt(bots.length)]);
            return embedBuilder.build();
        } else if (userRanking == null) {
            embedBuilder.setDescription(String.format("%s hasn't ranked yet", user.getName()));
            return embedBuilder.build();
        }

        int rnk = userRanking.get(RankingType.RANK);
        int lvl = userRanking.get(RankingType.LEVEL);
        int exp = userRanking.get(RankingType.EXPERIENCE);
        int tot = userRanking.get(RankingType.TOTAL_EXPERIENCE);

        embedBuilder.setAuthor(user.getName(), null, user.getAvatarUrl());

        embedBuilder.addField("Rank.", String.valueOf(rnk), true);
        embedBuilder.addField("Level.", String.valueOf(lvl), true);
        embedBuilder.addField("Exp.", String.format("%s", exp + (Lists.getLevelsMaxExp().get(lvl) == null ? "/∞" : "/" + Lists.getLevelsMaxExp().get(lvl) ) + (exp == tot ? "" : String.format(" (Total. %d)", tot))), true);

        return embedBuilder.build();
    }
}