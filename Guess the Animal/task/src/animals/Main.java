package animals;

import com.beust.jcommander.*;
import userinterface.ReplyGen;
import userinterface.UserInterface;

import static userinterface.GrammarUtils.*;

public class Main {
    @Parameter(names = {"-type", "-t"}, description = "Specifies the type for object mapping")
    private String type;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander
                .newBuilder()
                .addObject(main)
                .build()
                .parse(args);
            String typeOfMapping = defineMapping(main.getType());

        UserInterface ui = new UserInterface(typeOfMapping);
        prt(ReplyGen.greeter());
        newLine();
        prt("Welcome to the animal expert system!\n");
        ui.guessingGame();
    }

    private static String defineMapping(String type) {
        return type == null ? "json" : type;
    }

    private String getType() {
        return this.type;
    }
}