package animals;

import java.time.LocalTime;
import java.util.*;

public class ReplyGen {

    private static final List<String> INVALID_LIST, BYE_LIST, MORNING_LIST, NIGHT_LIST;

    static {
        NIGHT_LIST = List.of("Hi, Night Owl!", "Good morning!");
        MORNING_LIST = List.of("Hi, Early Bird!", "Good morning!");
        INVALID_LIST = List.of("I'm not sure I caught you: was it yes or no?",
                "Funny, I still don't understand, is it yes or no?",
                "Oh, it's too complicated for me: just tell me yes or no.",
                "Could you please simply say yes or no?",
                "Oh, no, don't try to confuse me: say yes or no.");

        BYE_LIST = List.of("I’m out", "Bye", "Goodbye",
                "Bye-bye", "Farewell", "Cheerio", "See you",
                "See you soon", "Catch you later", "Keep in touch",
                "See you later", "Bye for now", "Gotta go!",
                "Take it easy", "I’m out");
    }


    /**
     * @return a random invalid answer reply
     */
    public static String genInvalidAns() {
        Random random = new Random();
        return INVALID_LIST.get(random.nextInt(5));
    }

    /**
     * @return a random bye answer
     */
    public static String generateByeAnswer() {
        Random random = new Random();
        return BYE_LIST.get(random.nextInt(15));
    }

    public static String greeter() {

        LocalTime now = LocalTime.now();
        if ((now.equals(LocalTime.parse("05:00")) || now.isAfter(LocalTime.parse("05:00")))
                && now.isBefore(LocalTime.parse("12:00"))) {
            return now.isBefore(LocalTime.parse("08:00")) ? MORNING_LIST.get(0) : MORNING_LIST.get(1);
        } else if ((now.equals(LocalTime.parse("12:00")) || now.isAfter(LocalTime.parse("12:00")))
                && now.isBefore(LocalTime.parse("06:01"))) {
            return "Good afternoon!";
        } else {
            return now.isAfter(LocalTime.parse("00:00")) ? NIGHT_LIST.get(0) : NIGHT_LIST.get(1);
        }
    }
}
