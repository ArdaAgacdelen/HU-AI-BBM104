/**
 * The Refundable interface provides a reference for transportation vehicles that support
 * refund operations.
 */
public interface Refundable {

    /**
     * Deduction percentage of refund relative to normal price of seat when customer wants refund.
     *
     * @return Deduction percentage of refund.
     */
    int getRefundCutPercentage();
}
