/**
 * The Seat class represents a seat on a public transportation vehicle.
 * It contains properties for determining whether the seat is reserved and whether it is a premium seat.
 * This class defined for extensibility. For instance customer information can be added to seat!!
 */
public class Seat {
    private boolean isReserved;
    private boolean isPremiumSeat;

    /**
     * Returns whether the seat is reserved.
     *
     * @return True if the seat is reserved, false otherwise.
     */
    public boolean isReserved() {
        return isReserved;
    }

    /**
     * Sets whether the seat is reserved.
     *
     * @param reserved True to reserve the seat, false to cancel it.
     */
    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    /**
     * Returns whether the seat is a premium seat.
     *
     * @return True if the seat is a premium seat, false otherwise.
     */
    public boolean isPremiumSeat() {
        return isPremiumSeat;
    }

    /**
     * Sets whether the seat is a premium seat.
     *
     * @param premiumSeat True to mark the seat as premium, false otherwise.
     */
    public void setPremiumSeat(boolean premiumSeat) {
        isPremiumSeat = premiumSeat;
    }

}

