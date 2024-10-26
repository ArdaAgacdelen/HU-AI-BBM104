/**
 * Defines an abstract class for messages which will be used for different cases of dice rolling.
 */
public abstract class Message {
    public abstract void writeMessage(Player currentPlayer, String outputFile, String numbers);
}
