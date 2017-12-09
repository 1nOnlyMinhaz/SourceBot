package bid.ApixTeam.bot.utils.vars.enums;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public enum Settings {
    MAIN_GUILD_ID("default-guild-id"),
    CHAN_RANK_CHECK("channel-rank-check"),
    CHAN_SLOWMODE("channel-slowmode"),
    CHAN_WELCOME("channel-welcome"),
    CHAN_LOGS("channel-logger"),
    CHAN_REPORTS("channel-reporter"),
    CHAN_ADMIN("channel-administration"),
    CHAN_MEMES("channel-mmeez"),
    ROLES_MUTED("permission-muted"),
    ROLES_JR_MOD("permission-jr-mod"),
    ROLES_SR_MOD("permission-sr-mod"),
    ROLES_JR_ADMIN("permission-jr-admin"),
    ROLES_SR_ADMIN("permission-sr-admin"),
    ROLES_CHIEF_ADMIN("permission-chief-admin"),
    ROLES_BOT_ADMIN("permission-bot-admin"),
    PROFANITY_LIST("profanity-list"),
    RANKED_REWARDS("ranked-rewards");

    final String option;

    Settings(String s) {
        this.option = s;
    }

    public static Settings getSetting(String search) {
        for (Settings settings : values())
            if (settings.option.equalsIgnoreCase(search)) return settings;
        return null;
    }

    public String getOption() {
        return option;
    }
}
