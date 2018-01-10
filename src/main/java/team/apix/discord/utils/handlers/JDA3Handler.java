package team.apix.discord.utils.handlers;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.Sdcf4jMessage;
import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class JDA3Handler extends CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(de.btobastian.sdcf4j.handler.JDA3Handler.class);

    public JDA3Handler(JDA jda) {
        jda.addEventListener(new ListenerAdapter() {
            public void onMessageReceived(MessageReceivedEvent event) {
                handleMessageCreate(event);
            }
        });
    }

    public void addPermission(User user, String permission) {
        this.addPermission(user.getId(), permission);
    }

    public boolean hasPermission(User user, String permission) {
        return this.hasPermission(user.getId(), permission);
    }

    private void handleMessageCreate(MessageReceivedEvent event) {
        JDA jda = event.getJDA();
        if (event.getAuthor() != jda.getSelfUser()) {
            String[] splitMessage = event.getMessage().getContentRaw().split(" ");
            String commandString = splitMessage[0];
            SimpleCommand command = this.commands.get(commandString.toLowerCase());
            if (command == null) {
                if (splitMessage.length <= 1) {
                    return;
                }

                command = this.commands.get(splitMessage[1].toLowerCase());
                if (command == null || !command.getCommandAnnotation().requiresMention()) {
                    return;
                }

                splitMessage = Arrays.copyOfRange(splitMessage, 1, splitMessage.length);
            }

            Command commandAnnotation = command.getCommandAnnotation();
            if (!commandAnnotation.requiresMention() || commandString.equals(jda.getSelfUser().getAsMention())) {
                if (!event.isFromType(ChannelType.PRIVATE) || commandAnnotation.privateMessages()) {
                    if (event.isFromType(ChannelType.PRIVATE) || commandAnnotation.channelMessages()) {
                        if (!this.hasPermission(event.getAuthor(), commandAnnotation.requiredPermissions())) {
                            if (Sdcf4jMessage.MISSING_PERMISSIONS.getMessage() != null) {
                                event.getChannel().sendMessage(Sdcf4jMessage.MISSING_PERMISSIONS.getMessage());
                            }

                        } else {
                            Object[] parameters = this.getParameters(splitMessage, command, event);
                            if (commandAnnotation.async()) {
                                SimpleCommand finalCommand = command;
                                Thread t = new Thread(() -> {
                                    this.invokeMethod(finalCommand, event, parameters);
                                });
                                t.setDaemon(true);
                                t.start();
                            } else {
                                this.invokeMethod(command, event, parameters);
                            }

                        }
                    }
                }
            }
        }
    }

    private void invokeMethod(SimpleCommand command, MessageReceivedEvent event, Object[] parameters) {
        Method method = command.getMethod();
        Object reply = null;

        try {
            method.setAccessible(true);
            reply = method.invoke(command.getExecutor(), parameters);
        } catch (InvocationTargetException | IllegalAccessException var7) {
            logger.warn("An error occurred while invoking method {}!", method.getName(), var7);
        }

        if (reply != null) {
            event.getChannel().sendMessage(String.valueOf(reply)).queue();
        }

    }

    private Object[] getParameters(String[] splitMessage, SimpleCommand command, MessageReceivedEvent event) {
        String[] args = Arrays.copyOfRange(splitMessage, 1, splitMessage.length);
        Class<?>[] parameterTypes = command.getMethod().getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        int stringCounter = 0;

        for(int i = 0; i < parameterTypes.length; ++i) {
            Class<?> type = parameterTypes[i];
            if (type == String.class) {
                if (stringCounter++ == 0) {
                    parameters[i] = splitMessage[0];
                } else if (args.length + 2 > stringCounter) {
                    parameters[i] = args[stringCounter - 2];
                }
            } else if (type == String[].class) {
                parameters[i] = args;
            } else if (type == MessageReceivedEvent.class) {
                parameters[i] = event;
            } else if (type == JDA.class) {
                parameters[i] = event.getJDA();
            } else if (type == MessageChannel.class) {
                parameters[i] = event.getChannel();
            } else if (type == Message.class) {
                parameters[i] = event.getMessage();
            } else if (type == User.class) {
                parameters[i] = event.getAuthor();
            } else if (type == Member.class) {
                parameters[i] = event.getMember();
            } else if (type == TextChannel.class) {
                parameters[i] = event.getTextChannel();
            } else if (type == PrivateChannel.class) {
                parameters[i] = event.getPrivateChannel();
            } else if (type == MessageChannel.class) {
                parameters[i] = event.getChannel();
            } else if (type == Channel.class) {
                parameters[i] = event.getTextChannel();
            } else if (type == Group.class) {
                parameters[i] = event.getGroup();
            } else if (type == Guild.class) {
                parameters[i] = event.getGuild();
            } else if (type != Integer.class && type != Integer.TYPE) {
                if (type == Object[].class) {
                    parameters[i] = this.getObjectsFromString(event.getJDA(), args);
                } else {
                    parameters[i] = null;
                }
            } else {
                parameters[i] = event.getResponseNumber();
            }
        }

        return parameters;
    }

    private Object[] getObjectsFromString(JDA jda, String[] args) {
        Object[] objects = new Object[args.length];

        for(int i = 0; i < args.length; ++i) {
            objects[i] = this.getObjectFromString(jda, args[i]);
        }

        return objects;
    }

    private Object getObjectFromString(JDA jda, String arg) {
        try {
            return Long.valueOf(arg);
        } catch (NumberFormatException var5) {
            String id;
            if (arg.matches("<@([0-9]*)>")) {
                id = arg.substring(2, arg.length() - 1);
                User user = jda.getUserById(id);
                if (user != null) {
                    return user;
                }
            }

            if (arg.matches("<#([0-9]*)>")) {
                id = arg.substring(2, arg.length() - 1);
                Channel channel = jda.getTextChannelById(id);
                if (channel != null) {
                    return channel;
                }
            }

            return arg;
        }
    }
}
