package team.apix.discord.utils.vars;

/**
 * SourceBot (2017) was created by ApixTeam (C) 2016-2018
 * in association with TheSourceCode (C) 2016-2018
 */
public interface Messages {
    String NO_PERMISSION = ":cold_sweat: Sorry, but you don't have permissions to do that.";
    String NO_COM_PERMISSION = ":cold_sweat: Sorry, but you don't have permissions to use that command.";

    String NO_COOLDOWN = ":speaking_head: You're no longer on cooldown.";
    String ON_COOLDOWN = ":speaking_head: Sorry, but you cannot use that for the next %s.";
    String ON_COM_COOLDOWN = ":speaking_head: Sorry, but you cannot use that command for the next %s.";

    String INCORRECT_USAGE = "**Incorrect usage!** Correct format: `%s` :wink:";

    String[] RANK_BRAGS = {"Try and keep up.", "pffftt, gud one.", "I'm always in the top.", "ez.", "huh, too eazy.", "Is this ez mode?", "Over 9000!"};
    String[] RANK_BOTS = {"Ew, ban that thing", "GET THAT THING OUTTA HERE!", "reminds me of stupid mee6", "boooooooooooooooriingg", "if you ask me, i'd rather ban it.", "pfffft, I'm much better."};

    String[] LEAVE = {"OH NO! :cry: %PLAYER% just left the server :sob:", "RIP! %PLAYER% abandoned us :sob:", "Cya! %PLAYER% thought that we wanted them :joy:", "%PLAYER% has left the game.", "Later! %PLAYER% did rage quit :joy:"};

    String[] PROFANITY = {"**Watch your mouth** %s :rage:", "**Chill out** %s :rage:", "**LANGUAGE** %s :rage:", "**That's really bad** %s :rage:", "**I'M ALWAYS WATCHING YOU** %s :eyes::rage:", "**YOUR MOM SHOULD'VE TOLD YOU THAT THIS WORD IS BAD** %s :rage:", "**THAT'S CROSSING THE RED LINE** %s :rage:"};

    String BOT_INFO = "In the past, our server has used many pre-built Discord Bots made by other people, such as Dyno, and Mee6." +
            "\nWe moved on from Dyno pretty quickly, but up until this bot was released, we have always used Mee6 (yes, the one and only Mee6), of course it's a good bot with some perks, but we could do better." +
            "\n\nSo we did! We, the ApixTeam, consistent of yours truly, **Alw7SHxD** and **Andrewboy159**, have created a discord bot that has been in the making for months, we're proud of what we have created, and hope all of you users are also proud of what has been made specifically for this server that we all love.";
    String BOT_INFO_EXP = "You earn experience `(from 15 to 25)` every message within a minute from the last message." +
            "\n\nHere's an example if you're still confused, you just sent a message at **1:12 o'clock** that'll earn you experience, if you send any message within that time `(1:12 o'clock)` then you wont get any experience, until it's **1:13 o'clock** or even **1:14 o'clock** aka sending a message after a minute from the initial time which is **1:12 o'clock**, it's as simple as that!";

    String[] BALANCE_BRAGS = {"pfft I have over **9000 coins**!!", "I'm super busy, but if I wanted I could easily have a small loan of **2.5B coins** :upside_down:", "Currently **-7 coins** :upside_down:", "**nope**."};

    String[] GAME_PLAYIN = {"some minigames on SourceCade", "emily is away", "on SourceCade"};
    String[] GAME_WATCHIN = {"videos while vapin with Ned", "Ned's vaping animation", "Andrew suck at coding", "Alw's anime", "TheSourceCode", "Daeshan pretend to code"};
    String[] GAME_LISTENIN = {"Daeshan's voice", "earrape with Alw", "some quality mosic"};
}
