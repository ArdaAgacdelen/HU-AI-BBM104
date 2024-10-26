/**
 * This class represents a minibus that extends the PublicTransportationVehicle class.
 */
public class Minibus extends PublicTransportationVehicle {

    /**
     * Constructor for a minibus instance with the specified parameters
     * that should be initialized when creating a vehicle(voyage).
     *
     * @param busId             Unique id of the vehicle
     * @param seatPrice         Seat price for normal seats(Prevents keeping the same price for all seat objects and unnecessary memory usage)
     * @param departureLocation Name of departure city
     * @param arrivalLocation   Name of arrival city
     * @param numberOfSeats     Total number of seats in the vehicle
     */
    public Minibus(int busId, float seatPrice, String departureLocation, String arrivalLocation, int numberOfSeats) {
        super(busId, seatPrice, departureLocation, arrivalLocation, numberOfSeats);
    }
}
