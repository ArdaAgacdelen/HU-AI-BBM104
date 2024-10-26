/**
 * This class represents the main class of a Dice Game.
 *
 * Precondition: This main class must be run with command line arguments.
 * args[0] : Input.txt
 * args[1] : Output.txt
 */
public class DiceGame {
    /**
     * main method of Dice Game, includes game-play method calls and IO operations.
     *
     * @param args Args
     */
    public static void main(String[] args) {
        String[] lines = FileInput.readFile(args[0], true, true); //Reads input file line by line.
        FileOutput.writeToFile(args[1], "", false, false); //Creates a new empty output file.

        int howManyPlayer = GameManager.howManyPlayer(lines[0]);

        String[] playerNames = GameManager.playerNames(lines[1]);

        Player[] players = new Player[howManyPlayer];

        for (int index = 0; index < howManyPlayer; index++) { //Fills player's names in players array with given order.
            players[index] = new Player(playerNames[index]);
        }

        GameManager.playGame(players, lines, args[1]);

        GameManager.declareWinner(players, args[1]); //Writes winner to the output file.
    }
}