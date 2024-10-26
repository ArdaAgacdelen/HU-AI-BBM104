/**
 * This class is a custom exception class that is thrown when there is an error about printing a voyage.
 * It provides a constructor that takes an error message as a parameter.
 */
public class PrintVoyageError extends Exception {

    /**
     * Constructor with the detailed error message.
     * This message provides more information about why error occurred.
     *
     * @param errorMessage The error message explaining the cause of the error.
     */
    public PrintVoyageError(String errorMessage) {
        super(errorMessage);
    }
}
