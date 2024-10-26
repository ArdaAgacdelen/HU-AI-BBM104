/**
 * This class is a custom exception class that is thrown when there is an error about refunding a ticket.
 * It provides a constructor that takes an error message as a parameter.
 */
public class RefundTicketError extends Exception {

    /**
     * Constructor with the detailed error message.
     * This message provides more information about why error occurred.
     *
     * @param errorMessage The error message explaining the cause of the error.
     */
    public RefundTicketError(String errorMessage) {
        super(errorMessage);
    }
}
