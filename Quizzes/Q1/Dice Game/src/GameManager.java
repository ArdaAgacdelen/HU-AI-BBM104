/**
 * This class includes methods that returns information about game or simulates the game.
 */
public class GameManager {


    /**
     * This method finds that how many player will play the dice game.
     *
     * @param firstLine Line which includes number of players.
     * @return Number of players as int value.
     */
    public static int howManyPlayer(String firstLine) {
        return Integer.parseInt(firstLine);
    }


    /**
     * This method finds the name of the players and returns them as a string array.
     *
     * @param secondLine Line which includes the name of the players.
     * @return The name of the players as a string array.
     */
    public static String[] playerNames(String secondLine) {
        return secondLine.split(",");
    }


    /**
     * Find whose turn and returns that player.
     * If just one player had left in game, returns last player.
     *
     * @param players    Array of players that play game.
     * @param lastPlayer Player who played last round.
     * @return Player whose round came.
     */
    public static Player whoseTurn(Player[] players, Player lastPlayer) {
        if (lastPlayer != null) {
            for (int index = 0; index < players.length; index++) {
                if (players[index] == lastPlayer) { //Finds index of last player in players array.

                    for (int j = index + 1; j < players.length; j++) { //Players who come after last player in array.
                        if (!players[j].isGameOver()) { // If player is still in the game.
                            return players[j];
                        }
                    }

                    for (int j = 0; j < index; j++) { //Players who come before last player in array.
                        // (The turn to play is back to the beginning)

                        if (!players[j].isGameOver()) { // If player is still in the game.
                            return players[j];
                        }
                    }
                }
            }
            return lastPlayer; //There is no other player than last player. Last player is winner.

        } else { //Inıtıalization of game.
            return players[0];
        }
    }


    /**
     * Simulates the game.
     *
     * @param players    Array of players that play game.
     * @param lines      Lines of input file.
     * @param outputFile Output file to write laps of the dice game.
     */
    public static void playGame(Player[] players, String[] lines, String outputFile) {
        Player lastPlayer = null;
        Player currentPlayer = null;

        Message passMessage = new PassMessage();
        Message noPointMessage = new NoPointMessage();
        Message gameOverMessage = new GameOverMessage();
        Message newScoreMessage = new NewScoreMessage();

        for (int index = 2; index < lines.length; index++) { //Starts from 2 to skip first 2 lines in input file.

            String strNumbers = lines[index]; //Takes numbers of dices as String ("x-x")
            int[] numbersCame = Dice.throwDice(strNumbers); //Takes numbers of 2 dices as int[].

            if (!(lastPlayer == GameManager.whoseTurn(players, lastPlayer))) {
                currentPlayer = GameManager.whoseTurn(players, lastPlayer);
            } else { //whoseTurn method returns lastPlayer when there is just one player in game.
                break; //Game has a winner. No more dice rolls.
            }

            lastPlayer = currentPlayer; //Preparation for next iteration of for loop.

            if (numbersCame[0] == 0 && numbersCame[1] == 0) { //Player passed the lap.
                passMessage.writeMessage(currentPlayer, outputFile, strNumbers);

            }
            else if (numbersCame[0] == 1 || numbersCame[1] == 1) { //Case (1-x) or (x-1) or (1-1)

                if (numbersCame[0] == 1 && numbersCame[1] == 1) { //Case (1-1)
                    gameOverMessage.writeMessage(currentPlayer, outputFile, strNumbers);
                    currentPlayer.setGameOver(true);
                }
                else { //Case (1-x) or (x-1)
                    noPointMessage.writeMessage(currentPlayer, outputFile, strNumbers);
                }
            }
            else { //Neither of the dices are one.
                for (int numberCame : numbersCame) { //Adds numbers to the score of player.
                    currentPlayer.setScore(currentPlayer.getScore() + numberCame);
                }
                newScoreMessage.writeMessage(currentPlayer, outputFile, strNumbers);
            }
        }
    }


    /**
     * This method declares the winner by writing output file.
     *
     * @param players    Array of players that play game.
     * @param outputFile Output file to write winner.
     */
    public static void declareWinner(Player[] players, String outputFile) {
        Player winner = new Player("");

        for (Player p : players) { //Finds player who is still in the game.
            if (!p.isGameOver()) {
                winner = p;
            }
        }
        FileOutput.writeToFile(outputFile, String.format("%s is the winner of the game with the score of %d. " +
                "Congratulations %s!", winner.getName(), winner.getScore(), winner.getName()), true, false);
    }
}