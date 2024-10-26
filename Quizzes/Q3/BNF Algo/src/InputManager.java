import java.util.HashMap;

/**
 * This class provides methods to read files and transfer them into a collection type(Hash Map).
 */
public class InputManager {

    /**
     * This method reads given file and returns a hash map which represents given file content.
     * The file is assumed to contain lines with the format "key->value1|value2|...".
     *
     * @param inputFilePath The path of the input file to read and process.
     * @return A `HashMap` where the keys are reference keys from the input file,
     * and the values are arrays of possible values associated with each key.
     */
    public static HashMap<String, String[]> takeInput(String inputFilePath) {
        //Reads file into a string array.
        String[] inputFile = FileIO.readFile(inputFilePath, true, true);

        HashMap<String, String[]> returnMap = new HashMap<>();
        for (String line : inputFile) {
            //Adds map each line.
            returnMap.put(takeReferenceKey(line), takePossibilities(line));
        }

        return returnMap;
    }


    /**
     * Extracts the possibilities (values) from a given line of the input file.
     * The line is assumed to be in the format "key->value1|value2|...".
     *
     * @param line The line of the input file.
     * @return An array of possible values.
     */
    private static String[] takePossibilities(String line) {
        //Takes sub-string without key.
        String subLine = line.split("->")[1];
        return subLine.split("\\|");
    }


    /**
     * Extracts the reference key from a given line of the input file.
     * The line is assumed to be in the format "key->value1|value2|...".
     *
     * @param line The line of the input file.
     * @return The reference key from the line.
     */
    private static String takeReferenceKey(String line) {
        return line.split("->")[0];
    }
}
