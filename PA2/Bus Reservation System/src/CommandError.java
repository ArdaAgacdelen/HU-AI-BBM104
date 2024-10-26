/**
 * This class is a custom exception class that is thrown when there is an error about commands that are used.
 * It provides a constructor that takes an error message as a parameter.
 */
public class CommandError extends Exception {

    /**
     * Constructor with the detailed error message.
     * This message provides more information about why error occurred.
     *
     * @param errorMessage The error message explaining the cause of the error.
     */
    public CommandError(String errorMessage) {
        super(errorMessage);
    }
}
