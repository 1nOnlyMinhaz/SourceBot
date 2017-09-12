package bid.ApixTeam.bot.utils.vars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class Lists {
    private static ConcurrentHashMap<Long, Integer> userRankingCooldown = new ConcurrentHashMap<>();
    private static ArrayList<Long> userRankinCooldown = new ArrayList<>();
    private static HashMap<Integer, Integer> levelsMaxExp = new HashMap<>();

    public static ConcurrentHashMap<Long, Integer> getUserRankingCooldown() {
        return userRankingCooldown;
    }

    public static ArrayList<Long> getUserRankinCooldown() {
        return userRankinCooldown;
    }

    public static HashMap<Integer, Integer> getLevelsMaxExp() {
        return levelsMaxExp;
    }

    static void setLevelsMaxExp(HashMap<Integer, Integer> hashMap){
        levelsMaxExp = hashMap;
    }
}
