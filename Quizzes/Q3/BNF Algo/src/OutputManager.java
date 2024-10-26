import java.util.HashMap;

/**
 * This class provides a method to write every possible values of S from a given data structure to an output file.
 */
public class OutputManager {

    /**
     * Writes possible values associated with a given key from a provided input map to the specified output file.
     *
     * @param outputFilePath The path of the output file to write.
     * @param input          Input in hash map format.
     * @param key            The key for which the possible values should be written to the output file.
     */
    public static void writePossibilities(String outputFilePath, HashMap<String, String[]> input, String key) {

        //Gets array which contains every possible value key refers.
        String[] possibleWordsArray = input.get(key);

        for (int i = 0; i < possibleWordsArray.length; i++) {
            String word = possibleWordsArray[i];


            for (String character : word.split("")) {

                //Uppercase letter which is a reference key(non-terminal).
                if (character.equals(character.toUpperCase())) {
                    FileIO.writeToFile(outputFilePath, "(", true, false);
                    //Calls function with uppercase letter found.
                    writePossibilities(outputFilePath, input, character); //Recursion
                    FileIO.writeToFile(outputFilePath, ")", true, false);

                } else { //Lowercase letter which is a terminal item.
                    FileIO.writeToFile(outputFilePath, character, true, false);
                }
            }

            if (i + 1 != possibleWordsArray.length) {
                FileIO.writeToFile(outputFilePath, "|", true, false);
            }
        }
    }
}
