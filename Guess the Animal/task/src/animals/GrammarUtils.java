package animals;

import java.util.Scanner;
import java.util.regex.*;

public class GrammarUtils {
    public final static Pattern VOWELS,STATEMENT,UNDEFINED,YES_REPLY,NO_REPLY;
    public final static Scanner scanner = new Scanner(System.in);

    static {
        VOWELS = Pattern.compile("^[aeiou].+", Pattern.CASE_INSENSITIVE);
        STATEMENT = Pattern.compile("it\\s(can|has|is)\\s(.*)", Pattern.CASE_INSENSITIVE);
        UNDEFINED = Pattern.compile("^(a |an ).+", Pattern.CASE_INSENSITIVE);
        YES_REPLY = Pattern.compile("^(yes|y|yeah|yep|sure|right|affirmative|correct|indeed" +
                "|you bet|exactly|you said it)", Pattern.CASE_INSENSITIVE);
        NO_REPLY = Pattern.compile("^(no|n|no way|nah|nope|negative|i don't think so|yeah no)", Pattern.CASE_INSENSITIVE);

    }

    public static void newLine() {
        System.out.println();
    }

    public static void prt(String text) {
        System.out.println(text);
    }


    public static String stripArticles(String name) {
        return name
                .replaceAll("^an ", "")
                .replaceAll("^a ", "")
                .replaceAll("^the ", "");
    }

    public static void prtFeatures(String animal, String verb, String info) {
        prt(" - The " + animal + " " + verb + " " + info + ".");
    }

    public static String processArticle(String name) {
        Matcher undefinedMatcher = UNDEFINED.matcher(name);
        Matcher vowelsMatcher = VOWELS.matcher(name);
        if (!undefinedMatcher.matches()) {
            name = vowelsMatcher.matches() ? "an " + name : "a " + name;
        }
        return name.toLowerCase();
    }

    public static String stripReplies(String s) {
        return s.replaceAll("[.?!]$", "").strip();
    }

    public static void printErrorStatement() {
        prt("The examples of a statement:\n" +
                " - It can fly\n" +
                " - It has horn\n" +
                " - It is a mammal\n");
    }

    public static void startGame() {
        prt("You think of an animal, and I guess it.\n" +
                "Press enter when you're ready.");
        String enterToStart = scanner.nextLine();
    }

    public static String specifyStatement(String animal1, String animal2) {
        prt("Specify a fact that distinguishes " + animal1 + " from " + animal2 + ".\nThe sentence should be of the format: 'It can/has/is ...'.");
        return scanner.nextLine().replaceAll("[^a-zA-Z\\d\\s:]$", "");
    }

    public static String prtStatusAndGenerateQuestion(String guess, String animal2, String verb, String extraInfo, Matcher noMatcher) {
        prt("I have learned the following facts about animals:");
        String question;
        if (verb.equals("has")) {
            if (noMatcher.matches()) {
                prtFeatures(guess, "has", extraInfo);
                prtFeatures(animal2, "doesn't have", extraInfo);
            } else {
                prtFeatures(guess, "doesn't have", extraInfo);
                prtFeatures(animal2, "has", extraInfo);
            }
            question = "Does it have " + extraInfo + "?";
        } else if (verb.equals("is")) {
            if (noMatcher.matches()) {
                prtFeatures(guess, "is", extraInfo);
                prtFeatures(animal2, "isn't", extraInfo);
            } else {
                prtFeatures(guess, "isn't", extraInfo);
                prtFeatures(animal2, "is", extraInfo);
            }
            question = "Is it " + extraInfo + "?";
        } else {
            if (noMatcher.matches()) {
                prtFeatures(guess, "can", extraInfo);
                prtFeatures(animal2, "can't", extraInfo);
            } else {
                prtFeatures(guess, "can't", extraInfo);
                prtFeatures(animal2, "can", extraInfo);
            }
            question = "Can it " + extraInfo + "?";
        }
        prt("I can distinguish these animals by asking the question:");
        prt("- " + question);
        return question;
    }

    public static void prtMenuOptions() {
        prt("What do you want to do:\n" +
                "1. Play the guessing game\n" +
                "2. List of all animals\n" +
                "3. Search for an animal\n" +
                "4. Calculate statistics\n" +
                "5. Print the Knowledge Tree\n" +
                "0. Exit");
    }

    public static String changeQuestionToFact(String question, boolean positive) {
        String fact = question;
        if (question.startsWith("Is")) {
            if (positive) {
                fact = question.replace("Is it", "- It is").replace("?", ".");
            } else {
                fact = question.replace("Is it", "- It isn't").replace("?", ".");
            }

        } else if (question.startsWith("Can")) {
            if (positive) {
                fact = question.replace("Can it", "- It can").replace("?", ".");
            } else {
                fact = question.replace("Can it", "- It can't").replace("?", ".");
            }
        } else if (question.startsWith("Does")) {
            if (positive) {
                fact = question.replace("Does it have", "- It has").replace("?", ".");
            } else {
                fact = question.replace("Does it have", "- It doesn't have").replace("?", ".");
            }
        }
        return fact;
    }
}
