package team.apix.discord.utils.vars.entites.enums;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public enum Settings {
    MAIN_GUILD_ID("default-guild-id"),
    CHAN_RANK_CHECK("channel-rank-check"),
    CHAN_SLOWMODE("channel-slowmode"),
    CHAN_WELCOME("channel-welcome"),
    CHAN_LOGS("channel-logger"),
    CHAN_INCIDENTS("channel-incidents"),
    CHAN_REPORTS("channel-reporter"),
    CHAN_ADMIN("channel-administration"),
    CHAN_MEMES("channel-mmeez"),
    CHAN_COMMANDS("channel-commands"),
    CHAN_SCHANGELOG("channel-schangelog"),
    ROLES_MUTED("permission-muted"),
    ROLES_MOD("permission-mod"),
    ROLES_ADMIN("permission-admin"),
    ROLES_CHIEF_ADMIN("permission-chief-admin"),
    ROLES_BOT_ADMIN("permission-bot-admin"),
    PROFANITY_LIST("profanity-list"),
    RANKED_REWARDS("ranked-rewards"),
    RANKED_IGNORED("channel-ignored"),
    BALANCE_MAX("balance-max");

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
