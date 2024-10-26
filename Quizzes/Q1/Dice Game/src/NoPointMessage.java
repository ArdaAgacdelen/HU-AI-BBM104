/**
 * Cases (1-x) and (x-1)
 */
public class NoPointMessage extends Message{
    @Override
    public void writeMessage(Player currentPlayer, String outputFile, String numbers) {
        FileOutput.writeToFile(outputFile, String.format("%s threw %s and %s’s score is %d.",
                        currentPlayer.getName(), numbers, currentPlayer.getName(), currentPlayer.getScore()),
                true, true);
    }
}
