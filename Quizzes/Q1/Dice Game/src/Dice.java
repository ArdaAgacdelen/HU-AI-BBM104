/**
 * This class provides operations and methods which are about dices.
 */
public class Dice {


    /**
     * This method throws the 2 dices.
     *
     * @param diceLine Line that includes dice values.
     * @return Returns numbers came as integer array.
     */
    public static int[] throwDice(String diceLine) {
        int[] returnIntArray = new int[2];
        for (int i = 0; i < 2; i++) { //Reads numbers in line and saves as integers.
            returnIntArray[i] = Integer.parseInt(diceLine.split("-")[i]);
        }
        return returnIntArray;
    }
}
