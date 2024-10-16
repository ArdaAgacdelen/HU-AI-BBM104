/**
 * Custom exception class for the case that drill machine had drilled the lava block.
 */
public class LavaContactError extends Exception {

    /**
     * Constructor passes message to the Exception constructor.
     *
     * @param message Custom exception message
     */
    LavaContactError(String message) {
        super(message);
    }
}