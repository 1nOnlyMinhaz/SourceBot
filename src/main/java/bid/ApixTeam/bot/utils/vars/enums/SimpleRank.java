package bid.ApixTeam.bot.utils.vars.enums;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public enum SimpleRank {
    JR_MOD("Junior Moderator", Settings.ROLES_JR_MOD),
    SR_MOD("Senior Moderator", Settings.ROLES_SR_MOD),
    JR_ADMIN("Junior Administrator", Settings.ROLES_JR_ADMIN),
    SR_ADMIN("Senior Administrator", Settings.ROLES_SR_ADMIN),
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
