package team.apix.discord.libs.commands.fun.secret;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import team.apix.discord.utils.BotAPI;
import team.apix.discord.utils.api.EmbedMessageManager;
import team.apix.discord.utils.api.ExtraUtils;

import java.awt.*;
import java.util.Scanner;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public class ComBrainfuck implements CommandExecutor {
    @Command(aliases = {"brainfuck", "brainf**k"}, channelMessages = false, async = true)
    public void onCommand(User user, MessageChannel messageChannel, Message message, String[] strings) {
        BotAPI botAPI = new BotAPI();
        ExtraUtils eu = botAPI.getExtraUtils();
        EmbedMessageManager em = botAPI.getEmbedMessageManager();
        String command = "brainfuck";

        if (strings.length == 0) {
            botAPI.getMessageManager().sendMessage(messageChannel, getUsage());
            return;
        }

        if (eu.cooldown(botAPI, messageChannel, user, command, 30))
            return;

        StringBuilder str = new StringBuilder();
        for (String string : strings) str.append(string);

        //System.out.println("input: " + str);

        interpret(str.toString());
        botAPI.getMessageManager().sendMessage(messageChannel, output != null && !output.isEmpty() ? em.getAsDescription(output) : em.getAsDescription("error", Color.RED));
    }

    private MessageEmbed getUsage() {
        return new BotAPI().getEmbedMessageManager().getUsage("!brainfuck (code)");
    }

    private String output;

    // Interpreter function which accepts the code
    // a string parameter
    private void interpret(String s)
    {
        output = "";
        Scanner ob = new Scanner(s);

        // Max memory limit. It is the highest number which
        // can be represented by an unsigned 16-bit binary
        // number. Many computer programming environments
        // beside brainfuck may have predefined
        // constant values representing 65535.
        int length = 65535;

        // Array of byte type simulating memory of max
        // 65535 bits from 0 to 65534.
        byte memory[] = new byte[length];

        int ptr = 0; // Data pinter
        int c = 0;

        // Parsing through each character of the code
        for (int i = 0; i < s.length(); i++)
        {
            // BrainFuck is a tiny language with only
            // eight instructions. In this loop we check
            // and execute all those eight instructions


            // > moves the pointer to the right
            if (s.charAt(i) == '>')
            {
                if (ptr == length - 1)//If memory is full
                    ptr = 0;//pointer is returned to zero
                else
                    ptr ++;
            }

            // < moves the pointer to the left
            else if (s.charAt(i) == '<')
            {
                if (ptr == 0) // If the pointer reaches zero

                    // pointer is returned to rightmost memory
                    // position
                    ptr = length - 1;
                else
                    ptr --;
            }

            // + increments the value of the memory
            // cell under the pointer
            else if (s.charAt(i) == '+')
                memory[ptr] ++;

                // - decrements the value of the memory cell
                // under the pointer
            else if (s.charAt(i) == '-')
                memory[ptr] --;

                // . outputs the character signified by the
                // cell at the pointer
            else if (s.charAt(i) == '.')
                output += (char) memory[ptr];

                // , inputs a character and store it in the
                // cell at the pointer
            //else if (s.charAt(i) == ',')
            //    memory[ptr] = (byte)(ob.next().charAt(0));

                // [ jumps past the matching ] if the cell
                // under the pointer is 0
            else if (s.charAt(i) == '[')
            {
                if (memory[ptr] == 0)
                {
                    i++;
                    while (c > 0 || s.charAt(i) != ']')
                    {
                        if (s.charAt(i) == '[')
                            c++;
                        else if (s.charAt(i) == ']')
                            c--;
                        i ++;
                    }
                }
            }

            // ] jumps back to the matching [ if the
            // cell under the pointer is nonzero
            else if (s.charAt(i) == ']')
            {
                if (memory[ptr] != 0)
                {
                    i --;
                    while (c > 0 || s.charAt(i) != '[')
                    {
                        if (s.charAt(i) == ']')
                            c ++;
                        else if (s.charAt(i) == '[')
                            c --;
                        i --;
                    }
                    i --;
                }
            }
        }
    }
}
