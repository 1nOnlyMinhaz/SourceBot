package bid.ApixTeam.bot.utils.vars;

import java.util.HashMap;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class Levels extends Lists {
    public Levels() {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(0, 99);

        for (int i = 1; i <= 116; i++) {
            if (i <= 11)
                hashMap.put(i, i * 115);
            else if (i <= 15)
                hashMap.put(i, i * 130);
            else if (i <= 20)
                hashMap.put(i, i * 159);
            else if (i <= 25)
                hashMap.put(i, i * 172);
            else if (i <= 30)
                hashMap.put(i, i * 190);
            else if (i <= 35)
                hashMap.put(i, i * 230);
            else if (i <= 40)
                hashMap.put(i, i * 252);
            else if (i <= 45)
                hashMap.put(i, i * 287);
            else if (i <= 50)
                hashMap.put(i, i * 330);
            else if (i <= 55)
                hashMap.put(i, i * 368);
            else if (i <= 60)
                hashMap.put(i, i * 398);
            else if (i <= 65)
                hashMap.put(i, i * 435);
            else if (i <= 70)
                hashMap.put(i, i * 480);
            else if (i <= 75)
                hashMap.put(i, i * 510);
            else if (i <= 80)
                hashMap.put(i, i * 545);
            else if (i <= 85)
                hashMap.put(i, i * 586);
            else if (i <= 90)
                hashMap.put(i, i * 630);
            else if (i <= 95)
                hashMap.put(i, i * 675);
            else if (i <= 100)
                hashMap.put(i, i * 720);
            else if (i <= 105)
                hashMap.put(i, i * 892);
            else if (i <= 110)
                hashMap.put(i, i * 932);
            else if (i <= 115)
                hashMap.put(i, i * 1080);
        }

        Lists.setLevelsMaxExp(hashMap);
    }
}
