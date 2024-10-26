/**
 * This class is a custom exception class that is thrown when there is an error about selling ticket.
 * It provides a constructor that takes an error message as a parameter.
 */
public class SellTicketError extends Exception {

    /**
     * Constructor with the detailed error message.
     * This message provides more information about why error occurred.
     *
     * @param errorMessage The error message explaining the cause of the error.
     */
    public SellTicketError(String errorMessage) {
        super(errorMessage);
    }
}
