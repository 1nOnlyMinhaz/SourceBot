package bid.ApixTeam.bot.utils.vars.enums;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public enum PermissionsG {
    JR_MOD("Junior Moderator"),
    SR_MOD("Senior Moderator"),
    JR_ADMIN("Junior Administrator"),
    SR_ADMIN("Senior Administrator");

    final String description;

    PermissionsG(String desc) {
        this.description = desc;
    }

    public static PermissionsG getPermission(String search) {
        for (PermissionsG permissions : values())
            if (permissions.name().equalsIgnoreCase(search)) return permissions;
        return null;
    }

    public boolean isAtLeast(PermissionsG permissions) {
        return this.ordinal() >= permissions.ordinal();
    }

    public boolean isHigherThan(PermissionsG permissions) {
        return this.ordinal() > permissions.ordinal();
    }

    public boolean isLowerThan(PermissionsG permissions) {
        return this.ordinal() < permissions.ordinal();
    }

    public String getDescription() {
        return description;
    }
}
