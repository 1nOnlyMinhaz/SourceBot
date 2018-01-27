package team.apix.discord.utils.vars;

import java.util.HashMap;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class Levels extends Lists {
    public Levels() {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int lvl = 0; lvl <= 101; lvl++)
            hashMap.put(lvl, 5 * (lvl * lvl) + 55 * lvl + 100);
        Lists.setLevelsMaxExp(hashMap);
    }
}
