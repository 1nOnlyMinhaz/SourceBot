package team.apix.discord.libs.commands;

import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.api.ExtraUtils;
import team.apix.discord.utils.api.PermissionManager;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class ComUrbanDictionary implements CommandExecutor {
    @Command(aliases = {"ud", "urban", "dictionary", "define", "definition"}, description = "Search for a word on urban dictionary", async = true)
    public void onCommand(JDA jda, Guild guild, User user, MessageChannel messageChannel, Message message) {
        BotAPI botAPI = new BotAPI();
        PermissionManager pm = botAPI.getPermissionManager();
        ExtraUtils eu = botAPI.getExtraUtils();
        String command = "urban";

        if(eu.isCoolingdown(user, command)){
            botAPI.getMessageManager().sendMessage(messageChannel, eu.getCooldownMessage(user, pm, command));
            return;
        }else
            eu.throwCooldown(user, pm, command, 15);

        String[] args = message.getContentDisplay().split("\\s+");
        if(!(args.length > 1))
            return;

        args[0] = "";
        botAPI.getPrivateMessageManager().sendMessage(user, urbanGuild(args));
    }

    private MessageEmbed urbanGuild(String[] s) {
        StringBuilder strBuilder = new StringBuilder();
        for(String value : s) strBuilder.append(value);
        String newString = strBuilder.toString();
        String search = search(newString);
        if(search == null)
            return none();

        String[] strings = search.split("\\[tsc]");

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(new Color(141, 104, 153));

        embedBuilder.setTitle(String.format("Urban Dictionary - Definition of %s", strings[2]), strings[1]);

        embedBuilder.setDescription(strings[0]);
        embedBuilder.appendDescription("\n*Example:* " + strings[3]);

        embedBuilder.addField(":pencil:", String.format("`%s`", strings[4]), true);
        embedBuilder.addField(":thumbsup:", String.format("`%s`", strings[5]), true);
        embedBuilder.addField(":thumbsdown:", String.format("`%s`", strings[6]), true);

        return embedBuilder.build();
    }

    private MessageEmbed none() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(new Color(141, 104, 153));
        embedBuilder.setTitle("None was found, wanna search that? :wink:", null);
        return embedBuilder.build();
    }

    private String search(String search) {
        try {
            HttpResponse<JsonNode> response = Unirest.get("http://api.urbandictionary.com/v0/define?term=" + search)
                    .asJson();
            JSONArray list = response.getBody().getObject().getJSONArray("list");
            if(list.length() == 0) {
                return null;
            }
            JSONObject item = list.getJSONObject(0);
            return String.format("%s[tsc]%s[tsc]%s[tsc]%s[tsc]%s[tsc]%d[tsc]%d", item.getString("definition"), item.getString("permalink"), item.getString("word"), item.getString("example"), item.getString("author"), item.getInt("thumbs_up"), item.getInt("thumbs_down"));
        } catch (IllegalArgumentException ignored) {
            return null;
        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
            ignored.printStackTrace();
        }
        return null;
    }
}
