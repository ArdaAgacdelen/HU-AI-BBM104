import java.util.HashMap;

/**
 * Main class of this project Quiz 3.
 * I have used Hash Map as collection type since it allows keeping array for a String key.
 * Format used for data: String key -> String[] values. Where,
 * key represents left-hand side of input and
 * values represent right-hand side which is split by "|" for each possibility.
 * <p>
 * Thanks to this format I could easily write a recursive method by passing key as a parameter.
 */
public class BNF {
    public static void main(String[] args) {

        //Saves input in HashMap format.
        HashMap<String, String[]> input = InputManager.takeInput(args[0]);

        //Creates output file or deletes content of it if already exists.
        FileIO.writeToFile(args[1], "", false, false);

        FileIO.writeToFile(args[1], "(", true, false);
        //First call of recursive function. Starts from S.
        OutputManager.writePossibilities(args[1], input, "S");
        FileIO.writeToFile(args[1], ")", true, false);

    }
}