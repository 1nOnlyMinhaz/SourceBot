package bid.ApixTeam.bot.utils.vars;

import bid.ApixTeam.bot.utils.vars.entites.Announcement;
import bid.ApixTeam.bot.utils.vars.entites.Incident;
import bid.ApixTeam.bot.utils.vars.entites.enums.RankingType;
import bid.ApixTeam.bot.utils.vars.entites.enums.Settings;
import bid.ApixTeam.bot.utils.vars.entites.enums.SimpleRank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class Lists {
    private static ArrayList<String> commands = new ArrayList<>();
    private static ConcurrentHashMap<Long, Long> userRankingCooldown = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Long, HashMap<RankingType, Integer>> userLevels = new ConcurrentHashMap<>();
    private static HashMap<Integer, Integer> levelsMaxExp = new HashMap<>();
    private static HashMap<Long, SimpleRank> userPermissions = new HashMap<>();
    private static HashMap<Long, SimpleRank> rolePermissions = new HashMap<>();
    private static HashMap<Settings, String> settings = new HashMap<>();
    private static ArrayList<Long> users = new ArrayList<>();
    private static ArrayList<Long> mutedUsers = new ArrayList<>();
    private static HashMap<Long, Integer> slowmodeChannelCooldown = new HashMap<>();
    private static ConcurrentHashMap<String, Long> slowmodeUserCooldown = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer, Announcement> Announcements = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer, Incident> IncidentLog = new ConcurrentHashMap<>();
    private static int lastIncident;

    public static int getLastIncident() {
        return lastIncident;
    }

    public static void setLastIncident(int lastIncident) {
        Lists.lastIncident = lastIncident;
    }

    public static ArrayList<String> getCommands() {
        return commands;
    }

    public static ConcurrentHashMap<Long, Long> getUserRankingCooldown() {
        return userRankingCooldown;
    }

    public static ConcurrentHashMap<Long, HashMap<RankingType, Integer>> getUserLevels() {
        return userLevels;
    }

    public static HashMap<Integer, Integer> getLevelsMaxExp() {
        return levelsMaxExp;
    }

    static void setLevelsMaxExp(HashMap<Integer, Integer> hashMap) {
        levelsMaxExp = hashMap;
    }

    public static HashMap<Long, SimpleRank> getUserPermissions() {
        return userPermissions;
    }

    public static HashMap<Long, SimpleRank> getRolePermissions() {
        return rolePermissions;
    }

    public static HashMap<Settings, String> getSettings() {
        return settings;
    }

    public static ArrayList<Long> getUsers() {
        return users;
    }

    public static ArrayList<Long> getMutedUsers() {
        return mutedUsers;
    }

    public static HashMap<Long, Integer> getSlowmodeChannelCooldown() {
        return slowmodeChannelCooldown;
    }

    public static ConcurrentHashMap<String, Long> getSlowmodeUserCooldown() {
        return slowmodeUserCooldown;
    }

    public static ConcurrentHashMap<Integer, Announcement> getAnnouncements() {
        return Announcements;
    }

    public static ConcurrentHashMap<Integer, Incident> getIncidentLog() {
        return IncidentLog;
    }
}
