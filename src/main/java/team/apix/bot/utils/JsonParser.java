package team.apix.bot.utils;

import team.apix.bot.utils.api.DatabaseManager;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class JsonParser {

    public static void main(String[] args) {
        BotAPI botAPI = new BotAPI();
        DatabaseManager db = botAPI.getDatabaseManager();

        try {
            JSONObject jsonObj = new JSONObject(getMee6());

            JSONArray ja_data = jsonObj.getJSONArray("players");
            int length = ja_data.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = ja_data.getJSONObject(i);

                String username = (String) jsonObject.get("name");
                int discriminator = Integer.parseInt(String.valueOf(jsonObject.get("discriminator")));
                String uuid = (String) jsonObject.get("id");
                int level = (int) jsonObject.get("lvl");
                int xp = (int) jsonObject.get("xp");
                int total_xp = (int) jsonObject.get("total_xp");

                System.out.printf("[%s; %s#%s] = [level: %d ; xp: %d ; total xp: %d]%n", uuid, username, discriminator, level, xp, total_xp);
                db.createUserPreExistingRanking(Long.parseLong(uuid), level, xp, total_xp);

                //  ALTER TABLE table_name AUTO_INCREMENT = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getMee6(){
        return "{\n" +
                "  \"players\": [\n" +
                "    {\n" +
                "      \"avatar\": \"2ba117f21a254b7d25e435d5f2197400\", \n" +
                "      \"discriminator\": \"6751\", \n" +
                "      \"id\": \"170564495297478656\", \n" +
                "      \"lvl\": 33, \n" +
                "      \"lvl_xp\": 7195, \n" +
                "      \"name\": \"Alw7SHxD\", \n" +
                "      \"total_xp\": 88746, \n" +
                "      \"xp\": 1846, \n" +
                "      \"xp_percent\": 25\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"f402b04bb0ae01c0272c463d16149285\", \n" +
                "      \"discriminator\": \"9725\", \n" +
                "      \"id\": \"265801594875019264\", \n" +
                "      \"lvl\": 32, \n" +
                "      \"lvl_xp\": 6820, \n" +
                "      \"name\": \"RedNyanCat\", \n" +
                "      \"total_xp\": 81359, \n" +
                "      \"xp\": 1279, \n" +
                "      \"xp_percent\": 18\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"bd7a5ed7e8223a7d20d620d33a56e6db\", \n" +
                "      \"discriminator\": \"4814\", \n" +
                "      \"id\": \"281673659834302464\", \n" +
                "      \"lvl\": 30, \n" +
                "      \"lvl_xp\": 6100, \n" +
                "      \"name\": \"ramidzkh\", \n" +
                "      \"total_xp\": 70902, \n" +
                "      \"xp\": 3377, \n" +
                "      \"xp_percent\": 55\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"667af5642cd97bf51bd65d6a0da039d6\", \n" +
                "      \"discriminator\": \"6731\", \n" +
                "      \"id\": \"343078058535944193\", \n" +
                "      \"lvl\": 30, \n" +
                "      \"lvl_xp\": 6100, \n" +
                "      \"name\": \"TcpAckFrequency DEPRECATED\", \n" +
                "      \"total_xp\": 70625, \n" +
                "      \"xp\": 3100, \n" +
                "      \"xp_percent\": 50\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"73258b4ecb8250431a2cb093e9ee4dcb\", \n" +
                "      \"discriminator\": \"6874\", \n" +
                "      \"id\": \"202943202251112448\", \n" +
                "      \"lvl\": 29, \n" +
                "      \"lvl_xp\": 5755, \n" +
                "      \"name\": \"Andrewboy159\", \n" +
                "      \"total_xp\": 66837, \n" +
                "      \"xp\": 5067, \n" +
                "      \"xp_percent\": 88\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"0cc8342906efeabd581adbadf92da3cd\", \n" +
                "      \"discriminator\": \"0438\", \n" +
                "      \"id\": \"288327724702105611\", \n" +
                "      \"lvl\": 29, \n" +
                "      \"lvl_xp\": 5755, \n" +
                "      \"name\": \"Ricky12Awesome\", \n" +
                "      \"total_xp\": 65095, \n" +
                "      \"xp\": 3325, \n" +
                "      \"xp_percent\": 57\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"c5fef14d2c4e8fde8c41ae61fe4db400\", \n" +
                "      \"discriminator\": \"9152\", \n" +
                "      \"id\": \"232188192567066624\", \n" +
                "      \"lvl\": 28, \n" +
                "      \"lvl_xp\": 5420, \n" +
                "      \"name\": \"TheForbiddenAi\", \n" +
                "      \"total_xp\": 57000, \n" +
                "      \"xp\": 650, \n" +
                "      \"xp_percent\": 11\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"7d2b42a3441abf97272375085bbb872d\", \n" +
                "      \"discriminator\": \"7255\", \n" +
                "      \"id\": \"151779912431173632\", \n" +
                "      \"lvl\": 25, \n" +
                "      \"lvl_xp\": 4475, \n" +
                "      \"name\": \"Hwiggy\", \n" +
                "      \"total_xp\": 45049, \n" +
                "      \"xp\": 3049, \n" +
                "      \"xp_percent\": 68\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"0630a0378a93e15c922e7058ca9b762a\", \n" +
                "      \"discriminator\": \"9048\", \n" +
                "      \"id\": \"93789440094248960\", \n" +
                "      \"lvl\": 23, \n" +
                "      \"lvl_xp\": 3895, \n" +
                "      \"name\": \"TheDoc\", \n" +
                "      \"total_xp\": 36364, \n" +
                "      \"xp\": 2439, \n" +
                "      \"xp_percent\": 62\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"7dc49dcab275925f3d5a6684539fac61\", \n" +
                "      \"discriminator\": \"9619\", \n" +
                "      \"id\": \"204156692760494080\", \n" +
                "      \"lvl\": 23, \n" +
                "      \"lvl_xp\": 3895, \n" +
                "      \"name\": \"SebastiaanYN\", \n" +
                "      \"total_xp\": 36128, \n" +
                "      \"xp\": 2203, \n" +
                "      \"xp_percent\": 56\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"087ef2bd8a8647f9c9c4df16d50d2555\", \n" +
                "      \"discriminator\": \"4858\", \n" +
                "      \"id\": \"211459080860991488\", \n" +
                "      \"lvl\": 22, \n" +
                "      \"lvl_xp\": 3620, \n" +
                "      \"name\": \"Reflxction v2.7\", \n" +
                "      \"total_xp\": 31095, \n" +
                "      \"xp\": 790, \n" +
                "      \"xp_percent\": 21\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"a09e28b71bbf211a36c41577432fd495\", \n" +
                "      \"discriminator\": \"2430\", \n" +
                "      \"id\": \"234074387467206656\", \n" +
                "      \"lvl\": 22, \n" +
                "      \"lvl_xp\": 3620, \n" +
                "      \"name\": \"Plump_Orange\", \n" +
                "      \"total_xp\": 30928, \n" +
                "      \"xp\": 623, \n" +
                "      \"xp_percent\": 17\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"96e3d174ac61ff33105e59ebf4c9cada\", \n" +
                "      \"discriminator\": \"7920\", \n" +
                "      \"id\": \"149032550797410304\", \n" +
                "      \"lvl\": 21, \n" +
                "      \"lvl_xp\": 3355, \n" +
                "      \"name\": \"Deathkiller || Developer\", \n" +
                "      \"total_xp\": 29888, \n" +
                "      \"xp\": 2938, \n" +
                "      \"xp_percent\": 87\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"e922495074e55107f8c977a7734d7cce\", \n" +
                "      \"discriminator\": \"4067\", \n" +
                "      \"id\": \"148214019067478017\", \n" +
                "      \"lvl\": 21, \n" +
                "      \"lvl_xp\": 3355, \n" +
                "      \"name\": \"David.\", \n" +
                "      \"total_xp\": 28389, \n" +
                "      \"xp\": 1439, \n" +
                "      \"xp_percent\": 42\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"3930d6c21f6bd43608a17a016e5ca1eb\", \n" +
                "      \"discriminator\": \"0330\", \n" +
                "      \"id\": \"136715258134790144\", \n" +
                "      \"lvl\": 21, \n" +
                "      \"lvl_xp\": 3355, \n" +
                "      \"name\": \"A Nomar\", \n" +
                "      \"total_xp\": 27629, \n" +
                "      \"xp\": 679, \n" +
                "      \"xp_percent\": 20\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 7484, \n" +
                "      \"id\": \"305665281022361620\", \n" +
                "      \"lvl\": 20, \n" +
                "      \"lvl_xp\": 3100, \n" +
                "      \"name\": \"HacksMuch\", \n" +
                "      \"total_xp\": 26428, \n" +
                "      \"xp\": 2578, \n" +
                "      \"xp_percent\": 83\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"1626cb518c1ed2e66bfdb51e29f532fd\", \n" +
                "      \"discriminator\": \"4603\", \n" +
                "      \"id\": \"127529580604030977\", \n" +
                "      \"lvl\": 20, \n" +
                "      \"lvl_xp\": 3100, \n" +
                "      \"name\": \"DarkSeraphim\", \n" +
                "      \"total_xp\": 26182, \n" +
                "      \"xp\": 2332, \n" +
                "      \"xp_percent\": 75\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 1506, \n" +
                "      \"id\": \"237228488963260417\", \n" +
                "      \"lvl\": 20, \n" +
                "      \"lvl_xp\": 3100, \n" +
                "      \"name\": \"NotBaiting\", \n" +
                "      \"total_xp\": 24771, \n" +
                "      \"xp\": 921, \n" +
                "      \"xp_percent\": 29\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"a_3d036b895069c08f3390d303a5c4f135\", \n" +
                "      \"discriminator\": \"2511\", \n" +
                "      \"id\": \"142895980403097600\", \n" +
                "      \"lvl\": 19, \n" +
                "      \"lvl_xp\": 2855, \n" +
                "      \"name\": \"Gianluca\", \n" +
                "      \"total_xp\": 22219, \n" +
                "      \"xp\": 1224, \n" +
                "      \"xp_percent\": 42\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 6826, \n" +
                "      \"id\": \"159868222605099008\", \n" +
                "      \"lvl\": 19, \n" +
                "      \"lvl_xp\": 2855, \n" +
                "      \"name\": \"Exerosis\", \n" +
                "      \"total_xp\": 22007, \n" +
                "      \"xp\": 1012, \n" +
                "      \"xp_percent\": 35\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"fe68a2244f566035d55e86658098e5b4\", \n" +
                "      \"discriminator\": \"1518\", \n" +
                "      \"id\": \"207596013547028480\", \n" +
                "      \"lvl\": 18, \n" +
                "      \"lvl_xp\": 2620, \n" +
                "      \"name\": \"SatproMC\", \n" +
                "      \"total_xp\": 20796, \n" +
                "      \"xp\": 2421, \n" +
                "      \"xp_percent\": 92\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"85cfbc09177f4e56f292523007a329b4\", \n" +
                "      \"discriminator\": \"3188\", \n" +
                "      \"id\": \"206504892091727872\", \n" +
                "      \"lvl\": 17, \n" +
                "      \"lvl_xp\": 2395, \n" +
                "      \"name\": \"Laura\\u2665Russels\", \n" +
                "      \"total_xp\": 16374, \n" +
                "      \"xp\": 394, \n" +
                "      \"xp_percent\": 16\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"883043ac361cfe2c61a9f015c5d84543\", \n" +
                "      \"discriminator\": \"4020\", \n" +
                "      \"id\": \"319754398505107456\", \n" +
                "      \"lvl\": 16, \n" +
                "      \"lvl_xp\": 2180, \n" +
                "      \"name\": \"JustWesley\", \n" +
                "      \"total_xp\": 15634, \n" +
                "      \"xp\": 1834, \n" +
                "      \"xp_percent\": 84\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"40982f165b9fa0225c799e02b851947d\", \n" +
                "      \"discriminator\": \"0585\", \n" +
                "      \"id\": \"341998965660712971\", \n" +
                "      \"lvl\": 16, \n" +
                "      \"lvl_xp\": 2180, \n" +
                "      \"name\": \"Emily\", \n" +
                "      \"total_xp\": 14946, \n" +
                "      \"xp\": 1146, \n" +
                "      \"xp_percent\": 52\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"5d77cb9408ae0c508b522ee6a7a69643\", \n" +
                "      \"discriminator\": \"6410\", \n" +
                "      \"id\": \"178657593030475776\", \n" +
                "      \"lvl\": 16, \n" +
                "      \"lvl_xp\": 2180, \n" +
                "      \"name\": \"Ned\", \n" +
                "      \"total_xp\": 14679, \n" +
                "      \"xp\": 879, \n" +
                "      \"xp_percent\": 40\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"ccbbceeefd2fe6c9d652fe639e0406d1\", \n" +
                "      \"discriminator\": \"0212\", \n" +
                "      \"id\": \"137228395137335296\", \n" +
                "      \"lvl\": 16, \n" +
                "      \"lvl_xp\": 2180, \n" +
                "      \"name\": \"False\", \n" +
                "      \"total_xp\": 14201, \n" +
                "      \"xp\": 401, \n" +
                "      \"xp_percent\": 18\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"eea1111b8d1dd71db12e2988c0c807fc\", \n" +
                "      \"discriminator\": \"0047\", \n" +
                "      \"id\": \"266315409735548928\", \n" +
                "      \"lvl\": 15, \n" +
                "      \"lvl_xp\": 1975, \n" +
                "      \"name\": \"By_Jack\", \n" +
                "      \"total_xp\": 13202, \n" +
                "      \"xp\": 1377, \n" +
                "      \"xp_percent\": 69\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"0411e337d734dce3f4c74cc43a86e44b\", \n" +
                "      \"discriminator\": \"2099\", \n" +
                "      \"id\": \"274277298721783818\", \n" +
                "      \"lvl\": 15, \n" +
                "      \"lvl_xp\": 1975, \n" +
                "      \"name\": \"SimonM\", \n" +
                "      \"total_xp\": 12824, \n" +
                "      \"xp\": 999, \n" +
                "      \"xp_percent\": 50\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"1495ae41593665e29f683d63d502c600\", \n" +
                "      \"discriminator\": \"7801\", \n" +
                "      \"id\": \"290921387655430144\", \n" +
                "      \"lvl\": 15, \n" +
                "      \"lvl_xp\": 1975, \n" +
                "      \"name\": \"VRCube | Cubxity\", \n" +
                "      \"total_xp\": 12591, \n" +
                "      \"xp\": 766, \n" +
                "      \"xp_percent\": 38\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"62de0de594a553aa9edf61c996b50584\", \n" +
                "      \"discriminator\": \"4131\", \n" +
                "      \"id\": \"206927386367885312\", \n" +
                "      \"lvl\": 14, \n" +
                "      \"lvl_xp\": 1780, \n" +
                "      \"name\": \"Austin\", \n" +
                "      \"total_xp\": 11107, \n" +
                "      \"xp\": 1062, \n" +
                "      \"xp_percent\": 59\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 2538, \n" +
                "      \"id\": \"164036694612377601\", \n" +
                "      \"lvl\": 14, \n" +
                "      \"lvl_xp\": 1780, \n" +
                "      \"name\": \"pixar02\", \n" +
                "      \"total_xp\": 11049, \n" +
                "      \"xp\": 1004, \n" +
                "      \"xp_percent\": 56\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 9135, \n" +
                "      \"id\": \"309361141149335573\", \n" +
                "      \"lvl\": 14, \n" +
                "      \"lvl_xp\": 1780, \n" +
                "      \"name\": \"Teun\", \n" +
                "      \"total_xp\": 10449, \n" +
                "      \"xp\": 404, \n" +
                "      \"xp_percent\": 22\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 8645, \n" +
                "      \"id\": \"174010057988636672\", \n" +
                "      \"lvl\": 13, \n" +
                "      \"lvl_xp\": 1595, \n" +
                "      \"name\": \"CMTFrosty\", \n" +
                "      \"total_xp\": 10030, \n" +
                "      \"xp\": 1580, \n" +
                "      \"xp_percent\": 99\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"275b3a22559cf8f52f24d3d9672c87c1\", \n" +
                "      \"discriminator\": \"0522\", \n" +
                "      \"id\": \"231459866630291459\", \n" +
                "      \"lvl\": 13, \n" +
                "      \"lvl_xp\": 1595, \n" +
                "      \"name\": \"ToxicMushroom\", \n" +
                "      \"total_xp\": 9856, \n" +
                "      \"xp\": 1406, \n" +
                "      \"xp_percent\": 88\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"04e50494c8ca23d8dc447ce0030c5aad\", \n" +
                "      \"discriminator\": \"5735\", \n" +
                "      \"id\": \"161186477924024320\", \n" +
                "      \"lvl\": 13, \n" +
                "      \"lvl_xp\": 1595, \n" +
                "      \"name\": \"Kyrix\", \n" +
                "      \"total_xp\": 9627, \n" +
                "      \"xp\": 1177, \n" +
                "      \"xp_percent\": 73\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 966, \n" +
                "      \"id\": \"319535368992980994\", \n" +
                "      \"lvl\": 13, \n" +
                "      \"lvl_xp\": 1595, \n" +
                "      \"name\": \"SchaapieTV\", \n" +
                "      \"total_xp\": 9010, \n" +
                "      \"xp\": 560, \n" +
                "      \"xp_percent\": 35\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 2264, \n" +
                "      \"id\": \"256749931212177410\", \n" +
                "      \"lvl\": 13, \n" +
                "      \"lvl_xp\": 1595, \n" +
                "      \"name\": \"Santa Droei\", \n" +
                "      \"total_xp\": 8967, \n" +
                "      \"xp\": 517, \n" +
                "      \"xp_percent\": 32\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": \"3987\", \n" +
                "      \"id\": \"226904611292839936\", \n" +
                "      \"lvl\": 13, \n" +
                "      \"lvl_xp\": 1595, \n" +
                "      \"name\": \"T\\u00f6te dich selbst\", \n" +
                "      \"total_xp\": 8871, \n" +
                "      \"xp\": 421, \n" +
                "      \"xp_percent\": 26\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"59410852673af2778fb4625e08e92468\", \n" +
                "      \"discriminator\": \"3042\", \n" +
                "      \"id\": \"178806549710503936\", \n" +
                "      \"lvl\": 13, \n" +
                "      \"lvl_xp\": 1595, \n" +
                "      \"name\": \"!  Ender \\ud83c\\udff9\", \n" +
                "      \"total_xp\": 8776, \n" +
                "      \"xp\": 326, \n" +
                "      \"xp_percent\": 20\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"6311f7d032b9c4021fda426305e5849a\", \n" +
                "      \"discriminator\": \"9035\", \n" +
                "      \"id\": \"144285736277901312\", \n" +
                "      \"lvl\": 12, \n" +
                "      \"lvl_xp\": 1420, \n" +
                "      \"name\": \"Daeshan\", \n" +
                "      \"total_xp\": 8080, \n" +
                "      \"xp\": 1050, \n" +
                "      \"xp_percent\": 73\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 6801, \n" +
                "      \"id\": \"277873046629646356\", \n" +
                "      \"lvl\": 12, \n" +
                "      \"lvl_xp\": 1420, \n" +
                "      \"name\": \"Tetrah\", \n" +
                "      \"total_xp\": 7943, \n" +
                "      \"xp\": 913, \n" +
                "      \"xp_percent\": 64\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"53fb62520bfc4ae1f5bcb9c78fbe8da2\", \n" +
                "      \"discriminator\": \"5438\", \n" +
                "      \"id\": \"176900607666421760\", \n" +
                "      \"lvl\": 12, \n" +
                "      \"lvl_xp\": 1420, \n" +
                "      \"name\": \"logon1027\", \n" +
                "      \"total_xp\": 7225, \n" +
                "      \"xp\": 195, \n" +
                "      \"xp_percent\": 13\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"5444cd6d69299670609a2d4b8ec6a26d\", \n" +
                "      \"discriminator\": \"0448\", \n" +
                "      \"id\": \"325576647531298816\", \n" +
                "      \"lvl\": 12, \n" +
                "      \"lvl_xp\": 1420, \n" +
                "      \"name\": \"Womas_the_programmer\", \n" +
                "      \"total_xp\": 7133, \n" +
                "      \"xp\": 103, \n" +
                "      \"xp_percent\": 7\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": \"4173\", \n" +
                "      \"id\": \"210096179524927488\", \n" +
                "      \"lvl\": 11, \n" +
                "      \"lvl_xp\": 1255, \n" +
                "      \"name\": \"DeanGamesALot\", \n" +
                "      \"total_xp\": 6833, \n" +
                "      \"xp\": 1058, \n" +
                "      \"xp_percent\": 84\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"3af2041a99aaa0663728ea1d2b3ccea4\", \n" +
                "      \"discriminator\": \"1262\", \n" +
                "      \"id\": \"169903950512783361\", \n" +
                "      \"lvl\": 11, \n" +
                "      \"lvl_xp\": 1255, \n" +
                "      \"name\": \"TheTomTom3901\", \n" +
                "      \"total_xp\": 6632, \n" +
                "      \"xp\": 857, \n" +
                "      \"xp_percent\": 68\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 8780, \n" +
                "      \"id\": \"214909795231203328\", \n" +
                "      \"lvl\": 11, \n" +
                "      \"lvl_xp\": 1255, \n" +
                "      \"name\": \"kadeska23\", \n" +
                "      \"total_xp\": 6459, \n" +
                "      \"xp\": 684, \n" +
                "      \"xp_percent\": 54\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 4322, \n" +
                "      \"id\": \"320628502623813642\", \n" +
                "      \"lvl\": 11, \n" +
                "      \"lvl_xp\": 1255, \n" +
                "      \"name\": \"FlavorHex\", \n" +
                "      \"total_xp\": 6376, \n" +
                "      \"xp\": 601, \n" +
                "      \"xp_percent\": 47\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 5481, \n" +
                "      \"id\": \"115090410849828865\", \n" +
                "      \"lvl\": 11, \n" +
                "      \"lvl_xp\": 1255, \n" +
                "      \"name\": \"Okx\", \n" +
                "      \"total_xp\": 6165, \n" +
                "      \"xp\": 390, \n" +
                "      \"xp_percent\": 31\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"99d5e50a50bbd8e494775edff409415e\", \n" +
                "      \"discriminator\": \"1933\", \n" +
                "      \"id\": \"171915534164557824\", \n" +
                "      \"lvl\": 11, \n" +
                "      \"lvl_xp\": 1255, \n" +
                "      \"name\": \"And\\u00farilUnlogic\", \n" +
                "      \"total_xp\": 5965, \n" +
                "      \"xp\": 190, \n" +
                "      \"xp_percent\": 15\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 4305, \n" +
                "      \"id\": \"250971412272119808\", \n" +
                "      \"lvl\": 10, \n" +
                "      \"lvl_xp\": 1100, \n" +
                "      \"name\": \"Marcus\", \n" +
                "      \"total_xp\": 5739, \n" +
                "      \"xp\": 1064, \n" +
                "      \"xp_percent\": 96\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 3173, \n" +
                "      \"id\": \"247709019958018048\", \n" +
                "      \"lvl\": 10, \n" +
                "      \"lvl_xp\": 1100, \n" +
                "      \"name\": \"Morio\", \n" +
                "      \"total_xp\": 5457, \n" +
                "      \"xp\": 782, \n" +
                "      \"xp_percent\": 71\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"28d1be6df24d6704cfe282e1f0846137\", \n" +
                "      \"discriminator\": \"3532\", \n" +
                "      \"id\": \"378616437880913920\", \n" +
                "      \"lvl\": 10, \n" +
                "      \"lvl_xp\": 1100, \n" +
                "      \"name\": \"5568\", \n" +
                "      \"total_xp\": 5445, \n" +
                "      \"xp\": 770, \n" +
                "      \"xp_percent\": 70\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 5281, \n" +
                "      \"id\": \"312322073467027458\", \n" +
                "      \"lvl\": 10, \n" +
                "      \"lvl_xp\": 1100, \n" +
                "      \"name\": \"xPenguinx\\u1d48\\u1d49\\u1d5b\\u1d49\\u02e1\\u1d52\\u1d56\\u1d49\\u02b3\", \n" +
                "      \"total_xp\": 5352, \n" +
                "      \"xp\": 677, \n" +
                "      \"xp_percent\": 61\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 7046, \n" +
                "      \"id\": \"147282125983318016\", \n" +
                "      \"lvl\": 10, \n" +
                "      \"lvl_xp\": 1100, \n" +
                "      \"name\": \"ItsGwnMikai\", \n" +
                "      \"total_xp\": 5284, \n" +
                "      \"xp\": 609, \n" +
                "      \"xp_percent\": 55\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 4044, \n" +
                "      \"id\": \"280421552988749827\", \n" +
                "      \"lvl\": 10, \n" +
                "      \"lvl_xp\": 1100, \n" +
                "      \"name\": \"Natsu\", \n" +
                "      \"total_xp\": 5250, \n" +
                "      \"xp\": 575, \n" +
                "      \"xp_percent\": 52\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 5885, \n" +
                "      \"id\": \"366183948927238147\", \n" +
                "      \"lvl\": 10, \n" +
                "      \"lvl_xp\": 1100, \n" +
                "      \"name\": \"Haskixon - CEO of IDT\", \n" +
                "      \"total_xp\": 5224, \n" +
                "      \"xp\": 549, \n" +
                "      \"xp_percent\": 49\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 1893, \n" +
                "      \"id\": \"270181951615401984\", \n" +
                "      \"lvl\": 10, \n" +
                "      \"lvl_xp\": 1100, \n" +
                "      \"name\": \"iDarkyy\", \n" +
                "      \"total_xp\": 4978, \n" +
                "      \"xp\": 303, \n" +
                "      \"xp_percent\": 27\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"51135dd6093ceefa37167ec176128312\", \n" +
                "      \"discriminator\": \"1888\", \n" +
                "      \"id\": \"241938871925866496\", \n" +
                "      \"lvl\": 10, \n" +
                "      \"lvl_xp\": 1100, \n" +
                "      \"name\": \"Jack The Ripper\", \n" +
                "      \"total_xp\": 4954, \n" +
                "      \"xp\": 279, \n" +
                "      \"xp_percent\": 25\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"8dcd2f166c0a39ecdc676653f0b1f045\", \n" +
                "      \"discriminator\": \"4886\", \n" +
                "      \"id\": \"195895802579779584\", \n" +
                "      \"lvl\": 10, \n" +
                "      \"lvl_xp\": 1100, \n" +
                "      \"name\": \"CodedByYou\", \n" +
                "      \"total_xp\": 4917, \n" +
                "      \"xp\": 242, \n" +
                "      \"xp_percent\": 22\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 2507, \n" +
                "      \"id\": \"200272731332280321\", \n" +
                "      \"lvl\": 10, \n" +
                "      \"lvl_xp\": 1100, \n" +
                "      \"name\": \"ClickToAccept\", \n" +
                "      \"total_xp\": 4916, \n" +
                "      \"xp\": 241, \n" +
                "      \"xp_percent\": 21\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 9638, \n" +
                "      \"id\": \"220902856142749696\", \n" +
                "      \"lvl\": 10, \n" +
                "      \"lvl_xp\": 1100, \n" +
                "      \"name\": \"Ramon\", \n" +
                "      \"total_xp\": 4840, \n" +
                "      \"xp\": 165, \n" +
                "      \"xp_percent\": 15\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 2628, \n" +
                "      \"id\": \"201762452487012352\", \n" +
                "      \"lvl\": 9, \n" +
                "      \"lvl_xp\": 955, \n" +
                "      \"name\": \"OhhhZenix\", \n" +
                "      \"total_xp\": 4339, \n" +
                "      \"xp\": 619, \n" +
                "      \"xp_percent\": 64\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"894fc34bd10dc9fd855fa2c4a436872a\", \n" +
                "      \"discriminator\": \"8239\", \n" +
                "      \"id\": \"223119799478124544\", \n" +
                "      \"lvl\": 9, \n" +
                "      \"lvl_xp\": 955, \n" +
                "      \"name\": \"PluginerTR\", \n" +
                "      \"total_xp\": 4296, \n" +
                "      \"xp\": 576, \n" +
                "      \"xp_percent\": 60\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 3183, \n" +
                "      \"id\": \"203053198003404802\", \n" +
                "      \"lvl\": 9, \n" +
                "      \"lvl_xp\": 955, \n" +
                "      \"name\": \"sebastianpc\", \n" +
                "      \"total_xp\": 4257, \n" +
                "      \"xp\": 537, \n" +
                "      \"xp_percent\": 56\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"161e7a0eeec88f3c3fa2f5a99d59d96d\", \n" +
                "      \"discriminator\": \"8593\", \n" +
                "      \"id\": \"221954404868161537\", \n" +
                "      \"lvl\": 9, \n" +
                "      \"lvl_xp\": 955, \n" +
                "      \"name\": \"Doomfull\", \n" +
                "      \"total_xp\": 4252, \n" +
                "      \"xp\": 532, \n" +
                "      \"xp_percent\": 55\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 5655, \n" +
                "      \"id\": \"202944101333729280\", \n" +
                "      \"lvl\": 9, \n" +
                "      \"lvl_xp\": 955, \n" +
                "      \"name\": \"Savvy\", \n" +
                "      \"total_xp\": 4158, \n" +
                "      \"xp\": 438, \n" +
                "      \"xp_percent\": 45\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"244542e670dc439329a8f5818b94533f\", \n" +
                "      \"discriminator\": \"9602\", \n" +
                "      \"id\": \"197739642559266816\", \n" +
                "      \"lvl\": 9, \n" +
                "      \"lvl_xp\": 955, \n" +
                "      \"name\": \"Xyphn\", \n" +
                "      \"total_xp\": 4085, \n" +
                "      \"xp\": 365, \n" +
                "      \"xp_percent\": 38\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": \"4101\", \n" +
                "      \"id\": \"228635513752518656\", \n" +
                "      \"lvl\": 9, \n" +
                "      \"lvl_xp\": 955, \n" +
                "      \"name\": \"NarutoUzumaki22\", \n" +
                "      \"total_xp\": 4074, \n" +
                "      \"xp\": 354, \n" +
                "      \"xp_percent\": 37\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"f524898efbf6d4640f636a9207e940cf\", \n" +
                "      \"discriminator\": \"3663\", \n" +
                "      \"id\": \"256493015462313984\", \n" +
                "      \"lvl\": 9, \n" +
                "      \"lvl_xp\": 955, \n" +
                "      \"name\": \"TrueParrot\", \n" +
                "      \"total_xp\": 4009, \n" +
                "      \"xp\": 289, \n" +
                "      \"xp_percent\": 30\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"533fe4c7a4f73e1b8155189caa280613\", \n" +
                "      \"discriminator\": \"8351\", \n" +
                "      \"id\": \"384502625523204097\", \n" +
                "      \"lvl\": 9, \n" +
                "      \"lvl_xp\": 955, \n" +
                "      \"name\": \"</> Josh </>\", \n" +
                "      \"total_xp\": 3960, \n" +
                "      \"xp\": 240, \n" +
                "      \"xp_percent\": 25\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"534410ad958e67629700c2a4aec75da6\", \n" +
                "      \"discriminator\": \"7895\", \n" +
                "      \"id\": \"244240687233499136\", \n" +
                "      \"lvl\": 9, \n" +
                "      \"lvl_xp\": 955, \n" +
                "      \"name\": \"Nathan\", \n" +
                "      \"total_xp\": 3879, \n" +
                "      \"xp\": 159, \n" +
                "      \"xp_percent\": 16\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 4749, \n" +
                "      \"id\": \"328967321450446849\", \n" +
                "      \"lvl\": 9, \n" +
                "      \"lvl_xp\": 955, \n" +
                "      \"name\": \"DarknessBot\", \n" +
                "      \"total_xp\": 3813, \n" +
                "      \"xp\": 93, \n" +
                "      \"xp_percent\": 9\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 2296, \n" +
                "      \"id\": \"160269653136900096\", \n" +
                "      \"lvl\": 9, \n" +
                "      \"lvl_xp\": 955, \n" +
                "      \"name\": \"CoachAsh\", \n" +
                "      \"total_xp\": 3773, \n" +
                "      \"xp\": 53, \n" +
                "      \"xp_percent\": 5\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 3801, \n" +
                "      \"id\": \"184002931987578880\", \n" +
                "      \"lvl\": 8, \n" +
                "      \"lvl_xp\": 820, \n" +
                "      \"name\": \"\\u26a1 Pyro \\u26a1\", \n" +
                "      \"total_xp\": 3528, \n" +
                "      \"xp\": 628, \n" +
                "      \"xp_percent\": 76\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"e6690c746b783275541d87093421cc33\", \n" +
                "      \"discriminator\": \"0529\", \n" +
                "      \"id\": \"225806118943719425\", \n" +
                "      \"lvl\": 8, \n" +
                "      \"lvl_xp\": 820, \n" +
                "      \"name\": \"\\ud83c\\udf85\\ud83c\\udffb BlazingTide\", \n" +
                "      \"total_xp\": 3522, \n" +
                "      \"xp\": 622, \n" +
                "      \"xp_percent\": 75\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 3319, \n" +
                "      \"id\": \"246817275385085952\", \n" +
                "      \"lvl\": 8, \n" +
                "      \"lvl_xp\": 820, \n" +
                "      \"name\": \"DonkeyAss\", \n" +
                "      \"total_xp\": 3334, \n" +
                "      \"xp\": 434, \n" +
                "      \"xp_percent\": 52\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 6871, \n" +
                "      \"id\": \"151571608102764545\", \n" +
                "      \"lvl\": 8, \n" +
                "      \"lvl_xp\": 820, \n" +
                "      \"name\": \"ItsLordVamp(Dilza)\", \n" +
                "      \"total_xp\": 3333, \n" +
                "      \"xp\": 433, \n" +
                "      \"xp_percent\": 52\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 2387, \n" +
                "      \"id\": \"262482638055538690\", \n" +
                "      \"lvl\": 8, \n" +
                "      \"lvl_xp\": 820, \n" +
                "      \"name\": \"Daniel_Tex\", \n" +
                "      \"total_xp\": 3293, \n" +
                "      \"xp\": 393, \n" +
                "      \"xp_percent\": 47\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"13208b5e7d96b8df042baf955961a744\", \n" +
                "      \"discriminator\": \"6858\", \n" +
                "      \"id\": \"280026224682729473\", \n" +
                "      \"lvl\": 8, \n" +
                "      \"lvl_xp\": 820, \n" +
                "      \"name\": \"SunnyLo\", \n" +
                "      \"total_xp\": 3247, \n" +
                "      \"xp\": 347, \n" +
                "      \"xp_percent\": 42\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"335a3e24de83ac1a0fe1a5c69297f636\", \n" +
                "      \"discriminator\": \"3808\", \n" +
                "      \"id\": \"349897828107026432\", \n" +
                "      \"lvl\": 8, \n" +
                "      \"lvl_xp\": 820, \n" +
                "      \"name\": \"Higos\", \n" +
                "      \"total_xp\": 3183, \n" +
                "      \"xp\": 283, \n" +
                "      \"xp_percent\": 34\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 2636, \n" +
                "      \"id\": \"260928347779891211\", \n" +
                "      \"lvl\": 8, \n" +
                "      \"lvl_xp\": 820, \n" +
                "      \"name\": \"Animal\", \n" +
                "      \"total_xp\": 3140, \n" +
                "      \"xp\": 240, \n" +
                "      \"xp_percent\": 29\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 6037, \n" +
                "      \"id\": \"223558250648305664\", \n" +
                "      \"lvl\": 8, \n" +
                "      \"lvl_xp\": 820, \n" +
                "      \"name\": \"Drawfull\", \n" +
                "      \"total_xp\": 3036, \n" +
                "      \"xp\": 136, \n" +
                "      \"xp_percent\": 16\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 6042, \n" +
                "      \"id\": \"152983050098180096\", \n" +
                "      \"lvl\": 7, \n" +
                "      \"lvl_xp\": 695, \n" +
                "      \"name\": \"master3clipse\", \n" +
                "      \"total_xp\": 2765, \n" +
                "      \"xp\": 560, \n" +
                "      \"xp_percent\": 80\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 6722, \n" +
                "      \"id\": \"198264861841555467\", \n" +
                "      \"lvl\": 7, \n" +
                "      \"lvl_xp\": 695, \n" +
                "      \"name\": \"Aussie\", \n" +
                "      \"total_xp\": 2746, \n" +
                "      \"xp\": 541, \n" +
                "      \"xp_percent\": 77\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 3416, \n" +
                "      \"id\": \"248138325913763841\", \n" +
                "      \"lvl\": 7, \n" +
                "      \"lvl_xp\": 695, \n" +
                "      \"name\": \"Firozovich\", \n" +
                "      \"total_xp\": 2742, \n" +
                "      \"xp\": 537, \n" +
                "      \"xp_percent\": 77\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 9976, \n" +
                "      \"id\": \"210439389585604608\", \n" +
                "      \"lvl\": 7, \n" +
                "      \"lvl_xp\": 695, \n" +
                "      \"name\": \"Dillen\", \n" +
                "      \"total_xp\": 2654, \n" +
                "      \"xp\": 449, \n" +
                "      \"xp_percent\": 64\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 1568, \n" +
                "      \"id\": \"199242722861776897\", \n" +
                "      \"lvl\": 7, \n" +
                "      \"lvl_xp\": 695, \n" +
                "      \"name\": \"Andr\\u00e9 Vaz\", \n" +
                "      \"total_xp\": 2615, \n" +
                "      \"xp\": 410, \n" +
                "      \"xp_percent\": 58\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 6549, \n" +
                "      \"id\": \"206980305620303884\", \n" +
                "      \"lvl\": 7, \n" +
                "      \"lvl_xp\": 695, \n" +
                "      \"name\": \"skierdude\", \n" +
                "      \"total_xp\": 2558, \n" +
                "      \"xp\": 353, \n" +
                "      \"xp_percent\": 50\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 8996, \n" +
                "      \"id\": \"208384075663147008\", \n" +
                "      \"lvl\": 7, \n" +
                "      \"lvl_xp\": 695, \n" +
                "      \"name\": \"Plazy\", \n" +
                "      \"total_xp\": 2478, \n" +
                "      \"xp\": 273, \n" +
                "      \"xp_percent\": 39\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": \"2226\", \n" +
                "      \"id\": \"317139215685255170\", \n" +
                "      \"lvl\": 7, \n" +
                "      \"lvl_xp\": 695, \n" +
                "      \"name\": \"DeDose\", \n" +
                "      \"total_xp\": 2404, \n" +
                "      \"xp\": 199, \n" +
                "      \"xp_percent\": 28\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 3395, \n" +
                "      \"id\": \"193730796660588544\", \n" +
                "      \"lvl\": 7, \n" +
                "      \"lvl_xp\": 695, \n" +
                "      \"name\": \"bramhaag\", \n" +
                "      \"total_xp\": 2290, \n" +
                "      \"xp\": 85, \n" +
                "      \"xp_percent\": 12\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 3518, \n" +
                "      \"id\": \"146096009246670859\", \n" +
                "      \"lvl\": 7, \n" +
                "      \"lvl_xp\": 695, \n" +
                "      \"name\": \"Joey\", \n" +
                "      \"total_xp\": 2279, \n" +
                "      \"xp\": 74, \n" +
                "      \"xp_percent\": 10\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 261, \n" +
                "      \"id\": \"198876882828001280\", \n" +
                "      \"lvl\": 7, \n" +
                "      \"lvl_xp\": 695, \n" +
                "      \"name\": \"Will\", \n" +
                "      \"total_xp\": 2279, \n" +
                "      \"xp\": 74, \n" +
                "      \"xp_percent\": 10\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 787, \n" +
                "      \"id\": \"256070076111781888\", \n" +
                "      \"lvl\": 7, \n" +
                "      \"lvl_xp\": 695, \n" +
                "      \"name\": \"El Emi\", \n" +
                "      \"total_xp\": 2238, \n" +
                "      \"xp\": 33, \n" +
                "      \"xp_percent\": 4\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 9480, \n" +
                "      \"id\": \"381973428732952576\", \n" +
                "      \"lvl\": 6, \n" +
                "      \"lvl_xp\": 580, \n" +
                "      \"name\": \"Vertmix\", \n" +
                "      \"total_xp\": 2179, \n" +
                "      \"xp\": 554, \n" +
                "      \"xp_percent\": 95\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": \"a_2326b50492b18fb2a0eed447851bedea\", \n" +
                "      \"discriminator\": \"2454\", \n" +
                "      \"id\": \"232549284124295168\", \n" +
                "      \"lvl\": 6, \n" +
                "      \"lvl_xp\": 580, \n" +
                "      \"name\": \"Auki\", \n" +
                "      \"total_xp\": 2134, \n" +
                "      \"xp\": 509, \n" +
                "      \"xp_percent\": 87\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 7582, \n" +
                "      \"id\": \"319537801773318145\", \n" +
                "      \"lvl\": 6, \n" +
                "      \"lvl_xp\": 580, \n" +
                "      \"name\": \"Mathias\", \n" +
                "      \"total_xp\": 2115, \n" +
                "      \"xp\": 490, \n" +
                "      \"xp_percent\": 84\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 3134, \n" +
                "      \"id\": \"327594538342285323\", \n" +
                "      \"lvl\": 6, \n" +
                "      \"lvl_xp\": 580, \n" +
                "      \"name\": \"epiczun\", \n" +
                "      \"total_xp\": 2075, \n" +
                "      \"xp\": 450, \n" +
                "      \"xp_percent\": 77\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 1612, \n" +
                "      \"id\": \"262367127699193856\", \n" +
                "      \"lvl\": 6, \n" +
                "      \"lvl_xp\": 580, \n" +
                "      \"name\": \"Marcus_Roman\", \n" +
                "      \"total_xp\": 2038, \n" +
                "      \"xp\": 413, \n" +
                "      \"xp_percent\": 71\n" +
                "    }, \n" +
                "    {\n" +
                "      \"avatar\": null, \n" +
                "      \"discriminator\": 7725, \n" +
                "      \"id\": \"280683796020330497\", \n" +
                "      \"lvl\": 6, \n" +
                "      \"lvl_xp\": 580, \n" +
                "      \"name\": \"FatalHorizon\", \n" +
                "      \"total_xp\": 2021, \n" +
                "      \"xp\": 396, \n" +
                "      \"xp_percent\": 68\n" +
                "    }\n" +
                "  ], \n" +
                "  \"reward_roles\": {\n" +
                "    \"15\": [\n" +
                "      {\n" +
                "        \"color\": \"ffec1f\", \n" +
                "        \"hoist\": true, \n" +
                "        \"id\": \"266309331249659904\", \n" +
                "        \"managed\": false, \n" +
                "        \"mentionable\": false, \n" +
                "        \"name\": \"VIP\", \n" +
                "        \"permissions\": 3476545, \n" +
                "        \"position\": 5\n" +
                "      }\n" +
                "    ], \n" +
                "    \"30\": [\n" +
                "      {\n" +
                "        \"color\": \"e67e22\", \n" +
                "        \"hoist\": true, \n" +
                "        \"id\": \"310173984249348107\", \n" +
                "        \"managed\": false, \n" +
                "        \"mentionable\": false, \n" +
                "        \"name\": \"VIP+\", \n" +
                "        \"permissions\": 70585409, \n" +
                "        \"position\": 6\n" +
                "      }\n" +
                "    ], \n" +
                "    \"5\": [\n" +
                "      {\n" +
                "        \"color\": \"c71f76\", \n" +
                "        \"hoist\": true, \n" +
                "        \"id\": \"324693458931548160\", \n" +
                "        \"managed\": false, \n" +
                "        \"mentionable\": false, \n" +
                "        \"name\": \"Member\", \n" +
                "        \"permissions\": 3474433, \n" +
                "        \"position\": 4\n" +
                "      }\n" +
                "    ]\n" +
                "  }, \n" +
                "  \"server\": {\n" +
                "    \"icon\": \"e209d612e06c945c0ad18eee50a9845a\", \n" +
                "    \"id\": \"265499275088232448\", \n" +
                "    \"name\": \"{Source Official Discord}\"\n" +
                "  }\n" +
                "}";
    }
}