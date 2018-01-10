package team.apix.discord.utils.api;

import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.vars.Lists;
import team.apix.discord.utils.vars.Messages;
import team.apix.discord.utils.vars.entites.Cooldown;
import team.apix.discord.utils.vars.entites.enums.Settings;
import team.apix.discord.utils.vars.entites.enums.SimpleRank;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;

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

    public Member getAsMember(User user){
        return user.getJDA().getGuildById(new BotAPI().getSettingsManager().getSetting(Settings.MAIN_GUILD_ID)).getMember(user);
    }

    public void throwCooldown(User user, PermissionManager pm, int delay) {
        if (!isCoolingdown(user))
            Lists.getGlobalCooldown().put(user.getIdLong(), new Cooldown(delay, System.currentTimeMillis()));
    }

    public void throwCooldown(User user, PermissionManager pm, String command, int delay) {
        if(pm.userRoleAtLeast(getAsMember(user), SimpleRank.ADMIN))
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

        if(Lists.getCommandCooldown().containsKey(user.getIdLong()))
            cooldowns = Lists.getCommandCooldown().get(user.getIdLong());

        cooldowns.add(new Cooldown(delay, System.currentTimeMillis(), command));

        Lists.getCommandCooldown().put(user.getIdLong(), cooldowns);
    }

    public String getCooldownMessage(User user, PermissionManager pm){
        if(!isCoolingdown(user))
            return Messages.NO_COOLDOWN;

        return String.format(Messages.ON_COOLDOWN, getCooldownDelay(user, pm), getCooldownDelay(user, pm) != 1 ? "s" : "");
    }

    public String getCooldownMessage(User user, PermissionManager pm, String command){
        if(!isCoolingdown(user, command))
            return Messages.NO_COOLDOWN;

        return String.format(Messages.ON_COM_COOLDOWN, getCooldownDelay(user, pm, command), getCooldownDelay(user, pm, command) != 1 ? "s" : "");
    }

    public long getCooldownDelay(User user, PermissionManager pm){
        if(!isCoolingdown(user))
            return 0;

        Cooldown cooldown = Lists.getGlobalCooldown().get(user.getIdLong());
        return ((cooldown.getSystime() / 1000) + cooldown.getDelay()) - (System.currentTimeMillis() / 1000);
    }

    public long getCooldownDelay(User user, PermissionManager pm, String command){
        if(!isCoolingdown(user, command))
            return 0;

        Cooldown cooldown = null;
        for (Cooldown cd: Lists.getCommandCooldown().get(user.getIdLong())){
            if(cd.getCommand() != null && cd.getCommand().equalsIgnoreCase(command)){
                cooldown = cd;
                break;
            }
        }

        assert cooldown != null;
        return ((cooldown.getSystime() / 1000) + cooldown.getDelay()) - (System.currentTimeMillis() / 1000);
    }

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
}
