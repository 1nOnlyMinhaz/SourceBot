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
    private String[] leaveMessages = {"OH NO! :cry: %PLAYER% just left the server :sob:", "RIP! %PLAYER% abandoned us :sob:", "Cya! %PLAYER% thought that we want him :joy:", "%PLAYER% has left the game.", "Later! %PLAYER% did rage quit :joy:"};

    public static EmbedMessageManager getEmbedMessageManager() {
        return embedMessageManager;
    }

    public MessageEmbed getNoPermission() {
        return new EmbedBuilder()
                .setColor(new Color(234, 255, 235))
                .setDescription(Messages.NO_PERMISSION)
                .build();
    }

    public MessageEmbed getNoComPermission() {
        return new EmbedBuilder()
                .setColor(new Color(234, 255, 235))
                .setDescription(Messages.NO_COM_PERMISSION)
                .build();
    }

    public MessageEmbed getAsDescription(String s) {
        return new EmbedBuilder()
                .setColor(new Color(234, 255, 235))
                .setDescription(s)
                .build();
    }

    public MessageEmbed getAsDescription(String s, Color color) {
        return new EmbedBuilder()
                .setColor(color)
                .setDescription(s)
                .build();
    }

    public MessageEmbed getUsage(String command) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public MessageEmbed getDefaultHelpEmbed(User user) {
        EmbedBuilder defaultCommands = new EmbedBuilder();

        defaultCommands.setAuthor("Default Commands", null, user.getJDA().getSelfUser().getAvatarUrl())
                .addField("!help", "You know... :unamused:", false)
                .addField("!info", "Sends you an informal message about the bot and the server.\n **You MUST have PMs enabled for the server!**", false)
                .addField("!rank", "Displays your activity ranking among the server.\n *More info at `!info rank` :wink:*", false)
                .addField("!levels", "Displays the top `5` players on the ranking system.", false)
                .setColor(new Color(234, 255, 235));
        return defaultCommands.build();
    }

    public MessageEmbed getJrModHelpEmbed(User user) {
        EmbedBuilder jr_modCommands = new EmbedBuilder();

        jr_modCommands.setAuthor("Junior Moderation Commands", null, user.getJDA().getSelfUser().getAvatarUrl())
                .addField("!clear", "Clear messages from the channel command is sent in.", false)
                .addField("!slowmode", "Adds a delay to the chat.", false)
                .setColor(new Color(234, 255, 235));
        return jr_modCommands.build();
    }

    public MessageEmbed getSrModHelpEmbed(User user) {
        EmbedBuilder sr_modCommands = new EmbedBuilder();

        sr_modCommands.setAuthor("Senior Moderation Commands", null, user.getJDA().getSelfUser().getAvatarUrl())
                .addField("!mute", "Prevents a user from speaking in all channels.", false)
                .addField("!unmute", "... u dumb?", false)
                .setColor(new Color(234, 255, 235));
        return sr_modCommands.build();
    }

    public MessageEmbed getJrAdminHelpEmbed(User user) {
        EmbedBuilder jr_adminCommands = new EmbedBuilder();
        jr_adminCommands.setAuthor("Junior Administration Commands", null, user.getJDA().getSelfUser().getAvatarUrl())
                .addField("!broadcast", "duuhhh", false)
                .setColor(new Color(234, 255, 235));
        return jr_adminCommands.build();
    }

    public MessageEmbed getSrAdminHelpEmbed(User user) {
        EmbedBuilder sr_adminCommands = new EmbedBuilder();
        sr_adminCommands.setAuthor("Senior Administration Commands", null, user.getJDA().getSelfUser().getAvatarUrl())
                .setColor(new Color(234, 255, 235));
        return sr_adminCommands.build();
    }

    public MessageEmbed getInfoMain(User user) {
        return new EmbedBuilder()
                .setAuthor("Bot Information", null, user.getJDA().getSelfUser().getAvatarUrl())
                .setDescription("**TheSourceCode Bot** was made by **ApixTeam** in association with **TheSourceCode**.")
                .appendDescription(" It was made to work only for **TheSourceCode** Discord server along with PMs.")
                .appendDescription(" It provides an interesting amount of commands which among them includes the implementation for submitting plugin spotlight forms, and user reports.")
                .setColor(new Color(234, 255, 235))
                .build();
    }

    public MessageEmbed getInfoRank(User user) {
        return new EmbedBuilder()
                .setAuthor("Rank Information", null, user.getJDA().getSelfUser().getAvatarUrl())
                .setDescription("The ranking system for TheSourceCode Discord bot works as follows: When a user chats they get 15-25 Exp. but you only gain Exp once every minute to prevent spam ranking. When you type !rank in <#355468935413628930> it grabs the database of users that have chatted and determines your rank compared to every user, your level, and your total Exp. In conclusion the ranking system is an effective way to get people to be active.")
                .setColor(new Color(234, 255, 235))
                .build();
    }

    public MessageEmbed getInfoMod(User user) {
        return new EmbedBuilder()
                .setAuthor("Moderation Information", null, user.getJDA().getSelfUser().getAvatarUrl())
                .setDescription("We take pride in our Moderators for their ability to be professional and to have fun at the same time. To be a Moderator you must have what it takes, our Mods definitely do!")
                .setColor(new Color(234, 255, 235))
                .build();
    }

    public MessageEmbed getInfoAdmin(User user) {
        return new EmbedBuilder()
                .setAuthor("Administration Information", null, user.getJDA().getSelfUser().getAvatarUrl())
                .setDescription("Our Administrators are at a higher level of professionalism and loyalty, we don't mess around with our picks. They mean a lot to us as we know we mean to them!")
                .setColor(new Color(234, 255, 235))
                .build();
    }

    public MessageEmbed getClearLimit() {
        return new EmbedBuilder()
                .setDescription("Discord limits message removal from 1 to 100 messages only.")
                .setColor(new Color(234, 255, 235))
                .build();
    }

    public MessageEmbed getClearSuccess(int amount, String s) {
        return new EmbedBuilder()
                .setDescription(String.format("Successfully cleared `%d` %s.", amount, s))
                .setColor(new Color(234, 255, 235))
                .build();
    }

    public MessageEmbed getUsage(User user, String command, String desc, String format, String aliases) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setAuthor(String.format("Usage of !%s", command), null, user.getJDA().getSelfUser().getAvatarUrl())
                .addField("Description", desc, false)
                .addField("Format", String.format("!%s", format), false)
                .setColor(new Color(234, 255, 235));
        if (aliases != null)
            embedBuilder.addField("Aliases", aliases, false);
        return embedBuilder.build();
    }

    public MessageEmbed getLeaveEmbed(User user) {
        return new EmbedBuilder()
                .setDescription(leaveMessages[new Random().nextInt(leaveMessages.length)].replace("%PLAYER%", user.getAsMention()))
                .setColor(new Color(234, 255, 235))
                .build();
    }

    public MessageEmbed getReportEmbed(User reporter, User reported, String reason, String timestamp) {
        return new EmbedBuilder()
                .setAuthor("User Report", null, reporter.getJDA().getSelfUser().getAvatarUrl())
                .addField("Reporter", reporter.getName() + "#" + reporter.getDiscriminator(), false)
                .addField("Reported", reported.getName() + "#" + reported.getDiscriminator(), false)
                .addField("Reason", reason, false)
                .addField("Timestamp", timestamp, false)
                .setColor(new Color(234, 255, 235))
                .build();
    }
}