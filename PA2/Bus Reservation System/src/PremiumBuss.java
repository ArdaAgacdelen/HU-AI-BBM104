/**
 * This class represents a premium type bus that extends the PublicTransportationVehicle class
 * and implements the Refundable interface which provides refund operations.
 */
public class PremiumBuss extends PublicTransportationVehicle implements Refundable {


    /**
     * Constructor for a premium buss instance with the specified parameters
     * that should be initialized when creating a vehicle(voyage).
     *
     * @param busId                 Unique id of the vehicle
     * @param seatPrice             Seat price for normal seats(Prevents keeping the same price for all seat objects and unnecessary memory usage)
     * @param premiumSeatPercentage Seat price for premium seats
     * @param departureLocation     Name of departure city
     * @param arrivalLocation       Name of arrival city
     * @param numberOfSeats         Total number of seats in the vehicle
     * @param refundCutPercentage   Percentage of the total amount that will be deducted when a refund is processed.
     */
    public PremiumBuss(int busId, float seatPrice, float premiumSeatPercentage, String departureLocation, String arrivalLocation, int numberOfSeats, int refundCutPercentage) {
        super(busId, seatPrice, departureLocation, arrivalLocation, numberOfSeats);
        this.premiumSeatPrice = premiumSeatPercentage / 100 * seatPrice + seatPrice;
        this.refundCutPercentage = refundCutPercentage;

        for (int i = 0; i < super.getNumberOfSeats(); i++) {
            super.getSeatsArray()[i].setPremiumSeat((i + 1) % 3 == 1);
        }
    }

    private float premiumSeatPrice;
    private int refundCutPercentage;


    /**
     * Returns the premium seat price for the bus.
     *
     * @return The premium seat price as a float.
     */
    public float getPremiumSeatPrice() {
        return premiumSeatPrice;
    }


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
