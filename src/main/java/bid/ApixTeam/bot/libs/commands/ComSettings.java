package bid.ApixTeam.bot.libs.commands;

import bid.ApixTeam.bot.utils.BotAPI;
import bid.ApixTeam.bot.utils.api.EmbedMessageManager;
import bid.ApixTeam.bot.utils.api.PermissionManager;
import bid.ApixTeam.bot.utils.api.SettingsManager;
import bid.ApixTeam.bot.utils.vars.Lists;
import bid.ApixTeam.bot.utils.vars.entites.enums.Settings;
import bid.ApixTeam.bot.utils.vars.entites.enums.SimpleRank;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.webhook.WebhookClient;
import net.dv8tion.jda.webhook.WebhookClientBuilder;
import net.dv8tion.jda.webhook.WebhookMessageBuilder;

import java.awt.*;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class ComSettings implements CommandExecutor {
    @Command(aliases = "settings", async = true, privateMessages = false)
    public void onCommand(Guild guild, User user, MessageChannel messageChannel, Message message, String[] strings) {
        BotAPI botAPI = new BotAPI();
        PermissionManager pm = botAPI.getPermissionManager();
        EmbedMessageManager em = botAPI.getEmbedMessageManager();
        SettingsManager sm = botAPI.getSettingsManager();

        if(sm.isSet(Settings.CHAN_ADMIN) && !sm.getSetting(Settings.CHAN_ADMIN).equals(messageChannel.getId()))
            return;

        if(!pm.userRoleAtLeast(guild.getMember(user), SimpleRank.ADMIN) || !pm.userAtLeast(user, SimpleRank.BOT_ADMIN))
            return;

        try {
            if(strings[0].equalsIgnoreCase("tools")) {
                System.out.println("debug1");
                if(strings[1].equalsIgnoreCase("profanity")) {
                    System.out.println("debug2");
                    if(strings.length != 4) {
                        System.out.println("debug3");
                        botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription("Incorrect usage! `!settings tools profanity add|remove {word}`"));
                        return;
                    }
                    if(strings[2].equalsIgnoreCase("add")) {
                        System.out.println("debug4");
                        if(Lists.getProfanityList().contains(strings[3])) {
                            System.out.println("debug5");
                            botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription("That word is already added!"));
                            return;
                        }
                        System.out.println("debug6");
                        Lists.getProfanityList().add(strings[3]);
                        botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription(String.format("You have successfully added %s to the profanity word list!", strings[3])));
                        return;
                    }


                    if(strings[2].equalsIgnoreCase("remove")) {
                        System.out.println("debug7");
                        if(!Lists.getProfanityList().contains(strings[3])) {
                            System.out.println("debug8");
                            botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription("That word is not added!"));
                            return;
                        }
                        System.out.println("debug9");
                        Lists.getProfanityList().remove(strings[3]);
                        botAPI.getMessageManager().sendMessage(messageChannel, botAPI.getEmbedMessageManager().getAsDescription(String.format("You have successfully removed %s from the profanity word list!", strings[3])));
                    }
                }

            } else if(strings[0].equalsIgnoreCase("bot")) {
                if(!pm.userAtLeast(user, SimpleRank.BOT_ADMIN))
                    return;

                if(strings[1].equalsIgnoreCase("set")) {
                    if(strings[2].equalsIgnoreCase("role") && message.getMentionedRoles().size() == 1) {
                        if(strings.length != 5)
                            return;

                        if(strings[4].equalsIgnoreCase("muted"))
                            setRolePermission(botAPI, em, pm, messageChannel, message, SimpleRank.MUTED);
                        else if(strings[4].equalsIgnoreCase("mod"))
                            setRolePermission(botAPI, em, pm, messageChannel, message, SimpleRank.MOD);
                        else if(strings[4].equalsIgnoreCase("admin"))
                            setRolePermission(botAPI, em, pm, messageChannel, message, SimpleRank.ADMIN);
                        else if(strings[4].equalsIgnoreCase("chief"))
                            setRolePermission(botAPI, em, pm, messageChannel, message, SimpleRank.CHIEF_ADMIN);
                    } else if(strings[2].equalsIgnoreCase("channel") && message.getMentionedChannels().size() == 1) {
                        if(strings.length != 5)
                            return;

                        if(strings[4].equalsIgnoreCase("welcome"))
                            setChannelType(botAPI, sm, em, messageChannel, message, Settings.CHAN_WELCOME);
                        else if(strings[4].equalsIgnoreCase("rank"))
                            setChannelType(botAPI, sm, em, messageChannel, message, Settings.CHAN_RANK_CHECK);
                        else if(strings[4].equalsIgnoreCase("logs"))
                            setChannelType(botAPI, sm, em, messageChannel, message, Settings.CHAN_LOGS);
                        else if(strings[4].equalsIgnoreCase("mmeez"))
                            setChannelType(botAPI, sm, em, messageChannel, message, Settings.CHAN_MEMES);
                        else if(strings[4].equalsIgnoreCase("reports"))
                            setChannelType(botAPI, sm, em, messageChannel, message, Settings.CHAN_REPORTS);
                        else if(strings[4].equalsIgnoreCase("admin"))
                            setChannelType(botAPI, sm, em, messageChannel, message, Settings.CHAN_ADMIN);
                    }
                } else if(strings[1].equalsIgnoreCase("check")) {
                    if(strings[2].equalsIgnoreCase("user") && message.getMentionedUsers().size() == 1)
                        checkUserPermission(botAPI, em, pm, messageChannel, message);
                    else if(strings[2].equalsIgnoreCase("role") && message.getMentionedRoles().size() == 1)
                        checkRolePermission(botAPI, em, pm, messageChannel, message);
                } else if(strings[1].equalsIgnoreCase("rankup")) {
                    if(strings[2].equalsIgnoreCase("update"))
                        sm.retrieveOnce(Settings.RANKED_REWARDS);

                    if(strings.length != 4 && message.getMentionedRoles().size() != 1)
                        return;

                    int level = Integer.parseInt(strings[3]);

                    if(strings[2].equalsIgnoreCase("set"))
                        setRankupRole(botAPI, sm, em, messageChannel, message, level);
                    else if(strings[2].equalsIgnoreCase("remove"))
                        botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription("This feature is deprecated, and no longer works.", Color.RED));
                } else if(strings[1].equalsIgnoreCase("ptr")) {
                    try {
                        Webhook webhook = null;
                        for(Webhook wh : messageChannel.getJDA().getTextChannelById(messageChannel.getId()).getWebhooks().complete()) {
                            if(!wh.getName().equalsIgnoreCase("TSC-Bot"))
                                continue;

                            webhook = wh;
                            break;
                        }

                        if(webhook == null)
                            return;
                        WebhookClientBuilder builder = webhook.newClient();
                        WebhookClient client = builder.build();
                        WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder();
                        messageBuilder.setContent("This is a testing content");
                        messageBuilder.addEmbeds(em.getAsDescription("1st embed"), em.getAsDescription("2nd embed"))
                                .setUsername("Some weird bleach")
                                .setAvatarUrl(user.getJDA().getSelfUser().getAvatarUrl());
                        client.send(messageBuilder.build());
                        client.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else
                botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription("erR0r 0x11", Color.RED));

        } catch (
                ArrayIndexOutOfBoundsException e)

        {
            botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription("erR0r 0x01", Color.RED));
        } catch (
                Exception e)

        {
            e.printStackTrace();
        }

    }

    private void checkUserPermission(BotAPI botAPI, EmbedMessageManager em, PermissionManager pm, MessageChannel messageChannel, Message message) {
        User user = message.getMentionedUsers().get(0);
        Member member = message.getGuild().getMember(user);
        for(SimpleRank sr : SimpleRank.values()) {
            if(sr.isLowerThan(SimpleRank.MOD) || sr.isHigherThan(SimpleRank.CHIEF_ADMIN))
                continue;
            botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription(String.format(":thinking: **%s** to **%s**? `AtLeast`:`%s`, `Higher`:`%s`, `Lower`:`%s`", user.getName(), sr.getDescription(), pm.userRoleAtLeast(member, sr), pm.userRoleHigherThan(member, sr), pm.userRoleLowerThan(member, sr)), Color.DARK_GRAY));
        }
    }

    private void checkRolePermission(BotAPI botAPI, EmbedMessageManager em, PermissionManager pm, MessageChannel messageChannel, Message message) {
        Role role = message.getMentionedRoles().get(0);
        for(SimpleRank sr : SimpleRank.values()) {
            if(sr.isLowerThan(SimpleRank.MOD))
                continue;
            botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription(String.format(":thinking: **%s** to **%s**? `AtLeast`:`%s`, `Higher`:`%s`, `Lower`:`%s`", role.getName(), sr.getDescription(), pm.roleAtLeast(role, sr), pm.roleHigherThan(role, sr), pm.roleLowerThan(role, sr)), Color.DARK_GRAY));
        }
    }

    private void setUserPermission(BotAPI botAPI, EmbedMessageManager em, PermissionManager pm, MessageChannel messageChannel, Message message, SimpleRank simpleRank) {
        User target = message.getMentionedUsers().get(0);
        pm.setUserPermission(target, simpleRank);
        botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription("done.", Color.DARK_GRAY));
    }

    private void setRolePermission(BotAPI botAPI, EmbedMessageManager em, PermissionManager pm, MessageChannel messageChannel, Message message, SimpleRank simpleRank) {
        Role role = message.getMentionedRoles().get(0);
        pm.setRolePermission(botAPI, role, simpleRank);
        botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription("done.", Color.DARK_GRAY));
    }

    private void setChannelType(BotAPI botAPI, SettingsManager sm, EmbedMessageManager em, MessageChannel messageChannel, Message message, Settings settings) {
        TextChannel tc = message.getMentionedChannels().get(0);
        sm.addSetting(settings, tc.getId());
        botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription("done", Color.DARK_GRAY));
    }

    private void setRankupRole(BotAPI botAPI, SettingsManager sm, EmbedMessageManager em, MessageChannel messageChannel, Message message, int level) {
        Role role = message.getMentionedRoles().get(0);

        if(!sm.isSet(Settings.RANKED_REWARDS))
            sm.addSetting(Settings.RANKED_REWARDS, String.format("%d-%s", level, role.getId()));
        else {
            String cs = sm.getSetting(Settings.RANKED_REWARDS);
            cs += String.format(",%d-%s", level, role.getId());
            sm.updateSetting(Settings.RANKED_REWARDS, cs);
        }
        botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription("done", Color.DARK_GRAY));
    }

    @Deprecated
    private void removeRankupRole(BotAPI botAPI, SettingsManager sm, EmbedMessageManager em, MessageChannel messageChannel, Message message, int level) {
        Role role = message.getMentionedRoles().get(0);

        //if(!sm.isSet(Settings.RANKED_REWARDS)) {
        //    botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription("done", Color.RED));
        //    throw new NullPointerException();
        //}else{
        //    String cs = sm.getSetting(Settings.RANKED_REWARDS);
        //    if(cs.contains("-"))
        //        if (cs.contains(String.format("%d,%s", level, role.getId()))) {
        //            cs = cs.replace(String.format("-%d,%s-", level, role.getId()), ",");
        //            cs = cs.replace(String.format("%d,%s", level, role.getId()), "");
        //            sm.updateSetting(Settings.RANKED_REWARDS, cs);
        //        }
        //    else
        //        sm.updateSetting(Settings.RANKED_REWARDS, "null");
        //}
        botAPI.getMessageManager().sendMessage(messageChannel, em.getAsDescription("done", Color.DARK_GRAY));
    }
}
