/**
 * This class is a custom exception class that is thrown when there is an error about initializing a voyage.
 * It provides a constructor that takes an error message as a parameter.
 */
public class InitializeVoyageError extends Exception {

    /**
     * Constructor with the detailed error message.
     * This message provides more information about why error occurred.
     *
     * @param errorMessage The error message explaining the cause of the error.
     */
    public InitializeVoyageError(String errorMessage) {
        super(errorMessage);
    }
}
