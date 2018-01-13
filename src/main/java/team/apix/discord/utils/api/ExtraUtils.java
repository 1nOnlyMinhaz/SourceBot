package team.apix.discord.utils.api;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.vars.Lists;
import team.apix.discord.utils.vars.Messages;
import team.apix.discord.utils.vars.entites.Cooldown;
import team.apix.discord.utils.vars.entites.enums.Settings;
import team.apix.discord.utils.vars.entites.enums.SimpleRank;

import java.time.Duration;
import java.util.ArrayList;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class ExtraUtils {
    private static ExtraUtils extraUtils = new ExtraUtils();

    public static ExtraUtils getExtraUtils() {
        return extraUtils;
    }

    private final long secondsInMilli = 1000;
    private final long minutesInMilli = secondsInMilli * 60;
    private final long hoursInMilli = minutesInMilli * 60;
    private final long daysInMilli = hoursInMilli * 24;
    private final long weeksInMilli = daysInMilli * 7;
    private final long monthsInMilli = daysInMilli * 30;
    private final long yearsInMilli = daysInMilli * 365;

    public boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    public boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }

    public Member getAsMember(User user) {
        return user.getJDA().getGuildById(new BotAPI().getSettingsManager().getSetting(Settings.MAIN_GUILD_ID)).getMember(user);
    }

    @Deprecated
    public void throwCooldown(User user, PermissionManager pm, int delay) {
        if (!isCoolingdown(user))
            Lists.getGlobalCooldown().put(user.getIdLong(), new Cooldown(delay, System.currentTimeMillis()));
    }

    public void throwCooldown(User user, PermissionManager pm, String command, int delay) {
        if (pm.userRoleAtLeast(getAsMember(user), SimpleRank.ADMIN))
            return;

        ArrayList<Cooldown> cooldowns = new ArrayList<>();
        if (isCoolingdown(user, command)) {
            ArrayList<Cooldown> cld = Lists.getCommandCooldown().get(user.getIdLong());
            for (Cooldown cd : cld) {
                if (cd.getCommand() != null && cd.getCommand().equalsIgnoreCase(command)) {
                    cld.remove(cd);
                    break;
                }
            }

            Lists.getCommandCooldown().put(user.getIdLong(), cld);
            cooldowns = cld;
        }

        if (Lists.getCommandCooldown().containsKey(user.getIdLong()))
            cooldowns = Lists.getCommandCooldown().get(user.getIdLong());

        cooldowns.add(new Cooldown(delay, System.currentTimeMillis(), command));

        Lists.getCommandCooldown().put(user.getIdLong(), cooldowns);
    }

    @Deprecated
    public String getCooldownMessage(User user) {
        if (!isCoolingdown(user))
            return Messages.NO_COOLDOWN;

        return String.format(Messages.ON_COOLDOWN, getRemainingTime(getCooldownDelay(user)));
    }

    public String getCooldownMessage(User user, String command) {
        if (!isCoolingdown(user, command))
            return Messages.NO_COOLDOWN;

        return String.format(Messages.ON_COM_COOLDOWN, getRemainingTime(getCooldownDelay(user, command)));
    }

    @Deprecated
    public long getCooldownDelay(User user) {
        if (!isCoolingdown(user))
            return 0;

        Cooldown cooldown = Lists.getGlobalCooldown().get(user.getIdLong());
        return ((cooldown.getSystime() / 1000) + cooldown.getDelay()) - (System.currentTimeMillis() / 1000);
    }

    public long getCooldownDelay(User user, String command) {
        if (!isCoolingdown(user, command))
            return 0;

        Cooldown cooldown = null;
        for (Cooldown cd : Lists.getCommandCooldown().get(user.getIdLong())) {
            if (cd.getCommand() != null && cd.getCommand().equalsIgnoreCase(command)) {
                cooldown = cd;
                break;
            }
        }

        assert cooldown != null;
        return ((cooldown.getSystime() / 1000) + cooldown.getDelay()) - (System.currentTimeMillis() / 1000);
    }

    @Deprecated
    public long getCooldownSystime(User user) {
        if (!isCoolingdown(user))
            return 0;

        Cooldown cooldown = Lists.getGlobalCooldown().get(user.getIdLong());
        return cooldown.getSystime();
    }

    public long getCooldownSystime(User user, String command) {
        if (!isCoolingdown(user, command))
            return 0;

        Cooldown cooldown = null;
        for (Cooldown cd : Lists.getCommandCooldown().get(user.getIdLong())) {
            if (cd.getCommand() != null && cd.getCommand().equalsIgnoreCase(command)) {
                cooldown = cd;
                break;
            }
        }

        assert cooldown != null;
        return cooldown.getSystime();
    }

    @Deprecated
    public boolean isCoolingdown(User user) {
        Cooldown cooldown = Lists.getGlobalCooldown().get(user.getIdLong());
        if (cooldown == null)
            return false;

        long seconds = ((cooldown.getSystime() / 1000) + cooldown.getDelay()) - (System.currentTimeMillis() / 1000);
        if (seconds > 0) return true;
        else {
            Lists.getGlobalCooldown().remove(user.getIdLong());
            return false;
        }
    }

    public boolean isCoolingdown(User user, String command) {
        ArrayList<Cooldown> cooldowns = Lists.getCommandCooldown().get(user.getIdLong());
        if (cooldowns == null || cooldowns.isEmpty())
            return false;

        Cooldown cooldown = null;
        for (Cooldown cl : cooldowns) {
            if (cl.getCommand() != null && cl.getCommand().equalsIgnoreCase(command)) {
                cooldown = cl;
                break;
            }
        }

        if (cooldown == null)
            return false;

        long seconds = ((cooldown.getSystime() / 1000) + cooldown.getDelay()) - (System.currentTimeMillis() / 1000);
        if (seconds > 0) return true;
        else {
            cooldowns.remove(cooldown);
            Lists.getCommandCooldown().put(user.getIdLong(), cooldowns);
            return false;
        }
    }

    public String getElapsedTime(long startTime, long endTime) {
        long difference = endTime - startTime;

        long elapsedYears = difference / yearsInMilli;
        difference = difference % yearsInMilli;

        long elapsedMonths = difference / monthsInMilli;
        difference = difference % monthsInMilli;

        long elapsedWeeks = difference / weeksInMilli;
        difference = difference % weeksInMilli;

        long elapsedDays = difference / daysInMilli;
        difference = difference % daysInMilli;

        long elapsedHours = difference / hoursInMilli;
        difference = difference % hoursInMilli;

        long elapsedMinutes = difference / minutesInMilli;
        difference = difference % minutesInMilli;

        long elapsedSeconds = difference / secondsInMilli;

        ArrayList<String> arrayList = new ArrayList<>();
        String s = "just started.";
        if (elapsedYears != 0)
            arrayList.add(String.format("%d year%s", elapsedYears, elapsedYears != 1 ? "s" : ""));
        if (elapsedMonths != 0)
            arrayList.add(String.format("%d month%s", elapsedMonths, elapsedMonths != 1 ? "s" : ""));
        if (elapsedWeeks != 0)
            arrayList.add(String.format("%d week%s", elapsedWeeks, elapsedWeeks != 1 ? "s" : ""));
        if (elapsedDays != 0)
            arrayList.add(String.format("%d day%s", elapsedDays, elapsedDays != 1 ? "s" : ""));
        if (elapsedHours != 0)
            arrayList.add(String.format("%d hour%s", elapsedHours, elapsedHours != 1 ? "s" : ""));
        if (elapsedMinutes != 0)
            arrayList.add(String.format("%d minute%s", elapsedMinutes, elapsedMinutes != 1 ? "s" : ""));
        if (elapsedSeconds != 0)
            arrayList.add(String.format("%d second%s", elapsedSeconds, elapsedSeconds != 1 ? "s" : ""));

        if (!arrayList.isEmpty())
            s = separate(arrayList);

        return s;
    }

    public String getRemainingTime(long time) {
        Duration duration = Duration.ofSeconds(time);
        ArrayList<String> arrayList = new ArrayList<>();
        long days = duration.toDays();
        duration = duration.minusDays(days);
        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);
        long seconds = duration.getSeconds();

        if (days != 0)
            arrayList.add(String.format("%d day%s", days, days != 1 ? "s" : ""));
        if (hours != 0)
            arrayList.add(String.format("%d hour%s", hours, hours != 1 ? "s" : ""));
        if (minutes != 0)
            arrayList.add(String.format("%d minute%s", minutes, minutes != 1 ? "s" : ""));
        if (seconds != 0)
            arrayList.add(String.format("%d second%s", seconds, seconds != 1 ? "s" : ""));

        String s = "2 nanoseconds :joy:";
        if (!arrayList.isEmpty())
            s = separate(arrayList);

        return s;
    }

    public String separate(ArrayList<String> arrayList) {
        String s;
        if (arrayList.size() == 2)
            s = String.join(" and ", arrayList);
        else if (arrayList.size() > 2) {
            s = String.join(", ", arrayList);
            String st = s.substring(0, s.lastIndexOf(","));
            if (!st.contains(arrayList.get(arrayList.size() - 1))) {
                st += " and " + arrayList.get(arrayList.size() - 1);
                s = st;
            }
        } else s = String.join(", ", arrayList);
        return s;
    }

    public boolean siqc(String[] strings, int length, int index, String string){
        return strings.length == length && strings[index].equalsIgnoreCase(string);
    }

    public boolean cooldown(BotAPI botAPI, MessageChannel messageChannel, User user, String command, int delay){
        if(isCoolingdown(user, command)){
            botAPI.getMessageManager().sendMessage(messageChannel, getCooldownMessage(user, command));
            return true;
        }else
            throwCooldown(user, botAPI.getPermissionManager(), command, delay);

        return false;
    }

    public boolean isntInChannel(MessageChannel messageChannel, SettingsManager sm, Settings settings){
        return ((sm.getSetting(settings) != null && !sm.getSetting(settings).equals(messageChannel.getId()) && messageChannel.getType().isGuild())
                && (sm.getSetting(Settings.CHAN_ADMIN) != null && !sm.getSetting(Settings.CHAN_ADMIN).equals(messageChannel.getId()) && messageChannel.getType().isGuild()));
    }
}
