package bid.ApixTeam.bot.utils.api;

import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.enums.SimpleRank;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class PermissionManager {
    private static PermissionManager permissionManager = new PermissionManager();

    public static PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public boolean userAtLeast(User user, SimpleRank simpleRank) {
        SimpleRank rank = Lists.getUserPermissions().get(user.getIdLong());
        return rank != null && rank.isAtLeast(simpleRank);
    }

    public boolean userHigherThan(User user, SimpleRank simpleRank) {
        SimpleRank rank = Lists.getUserPermissions().get(user.getIdLong());
        return rank != null && rank.isHigherThan(simpleRank);
    }

    public boolean userLowerThan(User user, SimpleRank simpleRank) {
        SimpleRank rank = Lists.getUserPermissions().get(user.getIdLong());
        return rank == null || rank.isLowerThan(simpleRank);
    }

    public boolean roleAtLeast(Role role, SimpleRank simpleRank) {
        SimpleRank rank = Lists.getRolePermissions().get(role.getIdLong());
        return rank != null && rank.isAtLeast(simpleRank);
    }

    public boolean roleHigherThan(Role role, SimpleRank simpleRank) {
        SimpleRank rank = Lists.getRolePermissions().get(role.getIdLong());
        return rank != null && rank.isHigherThan(simpleRank);
    }

    public boolean roleLowerThan(Role role, SimpleRank simpleRank) {
        SimpleRank rank = Lists.getRolePermissions().get(role.getIdLong());
        return rank == null || rank.isLowerThan(simpleRank);

    }
}
