/**
 * Case (1-1)
 */
public class GameOverMessage extends Message {
    @Override
    public void writeMessage(Player currentPlayer, String outputFile, String numbers) {
        FileOutput.writeToFile(outputFile, String.format("%s threw 1-1. Game over %s!",
                currentPlayer.getName(), currentPlayer.getName()), true, true);
    }
}
