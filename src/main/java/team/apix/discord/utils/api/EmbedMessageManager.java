package team.apix.discord.utils.api;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.vars.Lists;
import team.apix.discord.utils.vars.Messages;
import team.apix.discord.utils.vars.entites.Incident;
import team.apix.discord.utils.vars.entites.enums.RankingType;
import team.apix.discord.utils.vars.entites.enums.Settings;

import java.awt.*;
import java.util.*;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class EmbedMessageManager {
    private static EmbedMessageManager embedMessageManager = new EmbedMessageManager();

    public static EmbedMessageManager getEmbedMessageManager() {
        return embedMessageManager;
    }

    public MessageEmbed getNoPermission() {
        return new EmbedBuilder()
                .setColor(new Color(103, 161, 237))
                .setDescription(Messages.NO_PERMISSION)
                .build();
    }

    public MessageEmbed getNoComPermission() {
        return new EmbedBuilder()
                .setColor(new Color(103, 161, 237))
                .setDescription(Messages.NO_COM_PERMISSION)
                .build();
    }

    public MessageEmbed getAsDescription(String s) {
        return new EmbedBuilder()
                .setColor(new Color(103, 161, 237))
                .setDescription(s)
                .build();
    }

    public MessageEmbed getAsDescription(String s, Color color) {
        return new EmbedBuilder()
                .setColor(color)
                .setDescription(s)
                .build();
    }

    public MessageEmbed getAsDescription(String s, User user) {
        return new EmbedBuilder()
                .setColor(new Color(103, 161, 237))
                .setDescription(s)
                .setAuthor(user.getName(), null, user.getAvatarUrl())
                .build();
    }

    public MessageEmbed getAsDescription(String s, Color color, User user) {
        return new EmbedBuilder()
                .setColor(color)
                .setDescription(s)
                .setAuthor(user.getName(), null, user.getAvatarUrl())
                .build();
    }

    public MessageEmbed getUsage(String command) {
        return new EmbedBuilder()
                .setColor(new Color(103, 161, 237))
                .setDescription(String.format(Messages.INCORRECT_USAGE, command))
                .build();
    }

    public MessageEmbed getRankEmbed(BotAPI botAPI, User user) {
        HashMap<RankingType, Integer> userRanking = botAPI.getDatabaseManager().getUserRankings(user);
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(new Color(103, 161, 237));

        if (user == user.getJDA().getSelfUser()) {
            embedBuilder.setDescription(Messages.RANK_BRAGS[new Random().nextInt(Messages.RANK_BRAGS.length)]);
            return embedBuilder.build();
        } else if (user.isBot()) {
            embedBuilder.setDescription(Messages.RANK_BOTS[new Random().nextInt(Messages.RANK_BOTS.length)]);
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

        String guild = botAPI.getSettingsManager().getSetting(Settings.MAIN_GUILD_ID);
        String setting = botAPI.getSettingsManager().getSetting(Settings.RANKED_REWARDS);
        if (setting != null) {
            int rl = 0;
            String role = "";
            String[] ranked = setting.split(",");
            for (String s : ranked) {
                String[] ranking = s.split("-");
                if (Integer.parseInt(ranking[0]) > lvl && !user.getJDA().getGuildById(guild).getMember(user).getRoles().contains(user.getJDA().getRoleById(ranking[1]))) {
                    rl = Integer.parseInt(ranking[0]) - lvl;
                    role = ranking[1];
                    break;
                }
            }

            if (rl != 0)
                embedBuilder.setFooter(String.format("%d remaining level%s to get %s", rl, rl == 1 ? "" : "s",
                        user.getJDA().getGuildById(botAPI.getSettingsManager().getSetting(Settings.MAIN_GUILD_ID)).getRoleById(role).getName()),
                        "https://i.imgur.com/hrFDjAm.png");
        }

        return embedBuilder.build();
    }

    public MessageEmbed getRanksEmbed(BotAPI botAPI, User user) {
        try {
            HashMap<Long, HashMap<RankingType, Integer>> usersRanking = botAPI.getDatabaseManager().getUsersRanking(5);
            HashMap<RankingType, Integer> userRanking;
            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setColor(new Color(103, 161, 237));
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

                if (user.getJDA().getUserById(Long.valueOf(strings[0])) != null) {
                    embedBuilder.appendDescription(String.format("**%d**%s. %s\n", rnk, rnk == 1 ? "st" : rnk == 2 ? "nd" : rnk == 3 ? "rd" : "th", user.getJDA().getUserById(Long.valueOf(strings[0])).getAsMention()));
                    embedBuilder.appendDescription(String.format("`Lvl. %d` | `Exp. %d`\n", lvl, exp));
                }
            }

            return embedBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public MessageEmbed getInfo(MessageChannel channel, String uptime) {
        return new EmbedBuilder()
                .setAuthor("Information", null, channel.getJDA().getSelfUser().getAvatarUrl())
                .setDescription(Messages.BOT_INFO)
                .setFooter(String.format("Uptime: %s", uptime), null)
                .setColor(new Color(103, 161, 237))
                .build();
    }

    public MessageEmbed getAsInfo(MessageChannel messageChannel, String identifier, String description) {
        return new EmbedBuilder()
                .setAuthor(String.format("Information on %s", identifier), null, messageChannel.getJDA().getSelfUser().getAvatarUrl())
                .setDescription(description)
                .setColor(new Color(103, 161, 237))
                .build();
    }

    public MessageEmbed getDefaultHelp(User user) {
        EmbedBuilder defaultCommands = new EmbedBuilder();

        defaultCommands.setAuthor("Default Commands", null, user.getJDA().getSelfUser().getAvatarUrl())
                .addField("!help", "You know... :unamused: \n*You can use `!help (command)` to get an explanation on how to use that command :wink:*", false)
                .addField("!info", "Sends you an informal message about the discord and the server.\n **You MUST have PMs enabled for the server!**", false)
                .addField("!report", "Report a certain user.", false)
                .addField("!coins", "Displays the amount of coins you have.", false)
                .addField("!rank", "Displays your activity ranking among the server.", false)
                .addField("!leaderboard", "Displays the top `5` players on the ranking system.", false)
                .setColor(new Color(103, 161, 237));
        return defaultCommands.build();
    }

    public MessageEmbed getModerationHelp(User user) {
        EmbedBuilder sr_modCommands = new EmbedBuilder();

        sr_modCommands.setAuthor("Moderation Commands", null, user.getJDA().getSelfUser().getAvatarUrl())
                .addField("!mute", "Prevents a user from speaking in all channels.", false)
                .addField("!tempmute", "Prevents a user from speaking in all channels temporarily.", false)
                .addField("!unmute", "... u dumb?", false)
                .addField("!slowmode", "slows the chat", false)
                .setColor(new Color(103, 161, 237));
        return sr_modCommands.build();
    }

    public MessageEmbed getAdministrationhelp(User user) {
        EmbedBuilder jr_adminCommands = new EmbedBuilder();
        jr_adminCommands.setAuthor("Administration Commands", null, user.getJDA().getSelfUser().getAvatarUrl())
                .addField("!broadcast", "duuhhh", false)
                .addField("!clear", "Clears a set amount of messages.", false)
                .addField("!tempban", "Bans a specific user for a set amount of time.", false)
                .setColor(new Color(103, 161, 237));
        return jr_adminCommands.build();
    }

    public MessageEmbed getClearLimit() {
        return new EmbedBuilder()
                .setDescription("Discord limits message removal from 1 to 100 messages only.")
                .setColor(new Color(103, 161, 237))
                .build();
    }

    public MessageEmbed getClearSuccess(int amount, String s) {
        return new EmbedBuilder()
                .setDescription(String.format("Successfully cleared `%d` %s.", amount, s))
                .setColor(new Color(103, 161, 237))
                .build();
    }

    public MessageEmbed getUsage(User user, String command, String desc, String format, String aliases) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setAuthor(String.format("Usage of !%s", command), null, user.getJDA().getSelfUser().getAvatarUrl())
                .addField("Description", desc, false)
                .setColor(new Color(103, 161, 237));
        if (format != null)
            embedBuilder.addField("Format", String.format("!%s", format.replace("<command>", command).replace("<!command>", "!" + command)), false);
        if (aliases != null)
            embedBuilder.addField("Aliases", aliases, false);
        return embedBuilder.build();
    }

    public MessageEmbed getLeaveEmbed(User user) {
        return new EmbedBuilder()
                .setDescription(Messages.LEAVE[new Random().nextInt(Messages.LEAVE.length)].replace("%PLAYER%", String.format("**%s#%s**", user.getName(), user.getDiscriminator())))
                .setColor(new Color(103, 161, 237))
                .build();
    }

    public MessageEmbed getIncidentEmbed(JDA jda, Incident incident) {
        User issuer = jda.getUserById(incident.getU1());
        User issued = jda.getUserById(incident.getU2());
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setAuthor(String.format("Incident #%d", incident.getId()), null, jda.getSelfUser().getAvatarUrl())
                .addField("Issued By", String.format("%s • %s#%s", issuer.getAsMention(), issuer.getName(), issuer.getDiscriminator()), false)
                .addField("Issued On", String.format("%s • %s#%s", issued.getAsMention(), issued.getName(), issued.getDiscriminator()), false)
                .addField("Type", incident.getType(), false)
                .addField("Reason", incident.getReason(), false)
                .addField("Timestamp", incident.getTimestamp().toString(), false)
                .setColor(new Color(103, 161, 237));

        if (incident.getDelay() != 0)
            embedBuilder.addField("Extra", String.format("For %s", new BotAPI().getExtraUtils().getRemainingTime(incident.getDelay() / 1000)), false);

        return embedBuilder.build();
    }
}
