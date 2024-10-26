/**
 * This class represents a standard type bus that extends the PublicTransportationVehicle class
 * and implements the Refundable interface which provides refund operations.
 */
public class StandardBuss extends PublicTransportationVehicle implements Refundable {

    /**
     * Constructor for a standard buss instance with the specified parameters
     * that should be initialized when creating a vehicle(voyage).
     *
     * @param busId               Unique id of the vehicle
     * @param seatPrice           Seat price for normal seats(Prevents keeping the same price for all seat objects and unnecessary memory usage)
     * @param departureLocation   Name of departure city
     * @param arrivalLocation     Name of arrival city
     * @param numberOfSeats       Total number of seats in the vehicle
     * @param refundCutPercentage Percentage of the total amount that will be deducted when a refund is processed.
     */
    public StandardBuss(int busId, float seatPrice, String departureLocation, String arrivalLocation, int numberOfSeats, int refundCutPercentage) {
        super(busId, seatPrice, departureLocation, arrivalLocation, numberOfSeats);
        this.refundCutPercentage = refundCutPercentage;
    }

    private int refundCutPercentage;

    /**
     * Returns the refund cut percentage for the bus.
     *
     * @return The refund cut percentage as an integer.
     */
    @Override
    public int getRefundCutPercentage() {
        return refundCutPercentage;
    }
}
