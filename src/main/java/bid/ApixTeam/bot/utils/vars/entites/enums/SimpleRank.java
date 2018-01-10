package bid.ApixTeam.bot.utils.vars.entites.enums;

/**
 * Source-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public enum SimpleRank {
    MUTED("Muted", Settings.ROLES_MUTED),
    MOD("Moderator", Settings.ROLES_MOD),
    ADMIN("Administrator", Settings.ROLES_ADMIN),
    CHIEF_ADMIN("Chief Administrator", Settings.ROLES_CHIEF_ADMIN),
    BOT_ADMIN("Bot Administrator", Settings.ROLES_BOT_ADMIN);

    final String description;
    final Settings settings;

    SimpleRank(String desc, Settings settings) {
        this.description = desc;
        this.settings = settings;
    }

    public static SimpleRank getRank(String search) {
        for (SimpleRank rank : values())
            if (rank.description.equalsIgnoreCase(search)) return rank;
        return null;
    }

    public static SimpleRank getRankBySetting(Settings settings) {
        for (SimpleRank rank : values())
            if (rank.settings.equals(settings)) return rank;
        return null;
    }

    public boolean isAtLeast(SimpleRank permissions) {
        return this.ordinal() >= permissions.ordinal();
    }

    public boolean isHigherThan(SimpleRank permissions) {
        return this.ordinal() > permissions.ordinal();
    }

    public boolean isLowerThan(SimpleRank permissions) {
        return this.ordinal() < permissions.ordinal();
    }

    public String getDescription() {
        return description;
    }

    public Settings getSettings() {
        return settings;
    }
}
