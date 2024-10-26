/**
 * Case (0-0) which means player has skipped the lap.
  */
public class PassMessage extends Message{
    @Override
    public void writeMessage(Player currentPlayer, String outputFile, String numbers) {
        FileOutput.writeToFile(outputFile, String.format("%s skipped the turn and %s’s score is %d.",
                currentPlayer.getName(), currentPlayer.getName(), currentPlayer.getScore()), true, true);
    }
}
