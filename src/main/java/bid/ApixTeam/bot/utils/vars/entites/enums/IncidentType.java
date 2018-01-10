package bid.ApixTeam.bot.utils.vars.entites.enums;

/**
 * Source-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public enum IncidentType {
    REPORT("report"),
    WARNING("warn"),
    MUTE("mute"),
    TEMP_MUTE("temp_mute"),
    KICK("kick"),
    TEMP_BAN("temp_ban"),
    BAN("ban");

    final String string;

    IncidentType(String string) {
        this.string = string;
    }

    public IncidentType getType(String search){
        for (IncidentType incidentType : values())
            if (incidentType.string.equalsIgnoreCase(search)) return incidentType;
        return null;
    }

    public String getString(){
        return string;
    }
}
