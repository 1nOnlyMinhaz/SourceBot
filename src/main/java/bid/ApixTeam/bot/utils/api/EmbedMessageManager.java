package bid.ApixTeam.bot.utils.api;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.Messages;
import bid.ApixTeam.bot.utils.vars.enums.RankingType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.util.*;

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

    public MessageEmbed getNoPermission(){
        return new EmbedBuilder()
                .setColor(new Color(234, 255, 235))
                .setDescription(Messages.NO_PERMISSION)
                .build();
    }

    public MessageEmbed getNoComPermission(){
        return new EmbedBuilder()
                .setColor(new Color(234, 255, 235))
                .setDescription(Messages.NO_COM_PERMISSION)
                .build();
    }

    public MessageEmbed getAsDescription(String s){
        return new EmbedBuilder()
                .setColor(new Color(234, 255, 235))
                .setDescription(s)
                .build();
    }

    public MessageEmbed getUsage(String command){
        return new EmbedBuilder()
                .setColor(new Color(234, 255, 235))
                .setDescription(String.format(Messages.INCORRECT_USAGE, command))
                .build();
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
        embedBuilder.addField("Exp.", String.format("%s", exp + (Lists.getLevelsMaxExp().get(lvl) == null ? "/∞" : "/" + Lists.getLevelsMaxExp().get(lvl)) + (exp == tot ? "" : String.format(" (Total. %d)", tot))), true);

        return embedBuilder.build();
    }

    public MessageEmbed getRanksEmbed(BotAPI botAPI, User user) {
        try {
            HashMap<Long, HashMap<RankingType, Integer>> usersRanking = botAPI.getDatabaseManager().getUsersRanking(5);
            HashMap<RankingType, Integer> userRanking;
            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setColor(new Color(234, 255, 235));
            embedBuilder.setAuthor("Top 5 leaderboard", null, user.getJDA().getSelfUser().getAvatarUrl());

            Map<Integer, String> map = new TreeMap<>(Collections.reverseOrder());

            for (Long l : usersRanking.keySet()) {
                userRanking = usersRanking.get(l);
                int rnk = userRanking.get(RankingType.RANK);
                int lvl = userRanking.get(RankingType.LEVEL);
                int exp = userRanking.get(RankingType.EXPERIENCE);
                int tot = userRanking.get(RankingType.TOTAL_EXPERIENCE);
                map.put(rnk, String.format("%d;%d;%d;%d;%d", l, rnk, lvl, exp, tot));
                userRanking.clear();
            }

            ArrayList<Integer> keys = new ArrayList<>(map.keySet());
            for (int i = keys.size() - 1; i >= 0; i--) {
                String[] strings = map.get(keys.get(i)).split(";");
                int rnk = Integer.parseInt(strings[1]);
                int lvl = Integer.parseInt(strings[2]);
                int exp = Integer.parseInt(strings[3]);
                int tot = Integer.parseInt(strings[4]);
                //embedBuilder.addField(String.format("%d. @%s#%s", rnk, user.getJDA().getUserById(Long.valueOf(strings[0])).getName(), user.getJDA().getUserById(Long.valueOf(strings[0])).getDiscriminator()), String.format("Lvl. %d | Exp. %d", lvl, exp), false);
                embedBuilder.appendDescription(String.format("**%d**%s. %s\n", rnk, rnk == 1 ? "st" : rnk == 2 ? "nd" : rnk == 3 ? "rd" : "th", user.getJDA().getUserById(Long.valueOf(strings[0])).getAsMention()));
                embedBuilder.appendDescription(String.format("`Lvl. %d` | `Exp. %d`\n", lvl, exp));
            }

            return embedBuilder.build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public MessageEmbed getHelpEmbed(BotAPI botAPI, User user) {
        return new EmbedBuilder()
                .setAuthor("Default commands", null, user.getJDA().getSelfUser().getAvatarUrl())
                .addField("!help", "Displays this help message.", true)
                .addField("!info [opt. rank | moderation | administration]", "Displays information on the bot.", false)
                .addField("!rank [opt. @user]", "Displays your or another user's rank of activity on the server.", false)
                .addField("!clear|!clean|!cls|!purge {number} [opt. @user] [opt. -s (silent)]", "Clears a number of messages, optionally from a user.", false)
                .setColor(new Color(234, 255, 235))
                .build();
    }

    public MessageEmbed getInfoMain(BotAPI botAPI, User user) {
        return new EmbedBuilder()
                .setAuthor("Bot Information", null, user.getJDA().getSelfUser().getAvatarUrl())
                .setDescription("TheSourceCodeBot was made by ApixTeam in association with TheSourceCode. It was made for only the TheSourceCode Discord server along with PMs. It has interesting commands which among them include implementation for submitting plugin spotlight forms, and user reports.")
                .setColor(new Color(234, 255, 235))
                .build();
    }

    public MessageEmbed getInfoRank(BotAPI botAPI, User user) {
        return new EmbedBuilder()
                .setAuthor("Rank Information", null, user.getJDA().getSelfUser().getAvatarUrl())
                .setDescription("The ranking system for TheSourceCode Discord bot works as follows: When a user chats they get 15-25 Exp. but you only gain Exp once every minute to prevent spam ranking. When you type !rank in <#355468935413628930> it grabs the database of users that have chatted and determines your rank compared to every user, your level, and your total Exp. In conclusion the ranking system is an effective way to get people to be active.")
                .setColor(new Color(234, 255, 235))
                .build();
    }

    public MessageEmbed getInfoMod(BotAPI botAPI, User user) {
        return new EmbedBuilder()
                .setAuthor("Moderation Information", null, user.getJDA().getSelfUser().getAvatarUrl())
                .setDescription("We take pride in our Moderators for their ability to be professional and to have fun at the same time. To be a Moderator you must have what it takes, our Mods definitely do!")
                .setColor(new Color(234, 255, 235))
                .build();
    }

    public MessageEmbed getInfoAdmin(BotAPI botAPI, User user) {
        return new EmbedBuilder()
                .setAuthor("Administration Information", null, user.getJDA().getSelfUser().getAvatarUrl())
                .setDescription("Our Administrators are at a higher level of professionalism and loyalty, we don't mess around with our picks. They mean a lot to us as we know we mean to them!")
                .setColor(new Color(234, 255, 235))
                .build();
    }

    public MessageEmbed getClearLimit(){
        return new EmbedBuilder()
                .setDescription("Discord limits message removal from 1 to 100 messages only.")
                .setColor(new Color(234, 255, 235))
                .build();
    }

    public MessageEmbed getClearSuccess(int amount, String s){
        return new EmbedBuilder()
                .setDescription(String.format("Successfully cleared `%d` %s.", amount, s))
                .setColor(new Color(234, 255, 235))
                .build();
    }
}