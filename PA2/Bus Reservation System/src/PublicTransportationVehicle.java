/**
 * This class represents a public transportation vehicle reference,
 * with properties and methods to manage operational process.
 * It maintains details about the vehicle.
 */
public class PublicTransportationVehicle {

    /**
     * Constructor for attributes that should be initialized when creating a vehicle(voyage).
     *
     * @param busId             Unique id of the vehicle
     * @param seatPrice         Seat price for normal seats(Prevents keeping the same price for all seat objects and unnecessary memory usage)
     * @param departureLocation Name of departure city
     * @param arrivalLocation   Name of arrival city
     * @param numberOfSeats     Total number of seats in the vehicle
     */
    public PublicTransportationVehicle(int busId, float seatPrice, String departureLocation, String arrivalLocation,
                                       int numberOfSeats) {

        this.vehicleId = busId;
        this.seatPrice = seatPrice;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.numberOfSeats = numberOfSeats;
        this.seatsArray = new Seat[numberOfSeats];

        for (int i = 0; i < numberOfSeats; i++) {
            getSeatsArray()[i] = new Seat(); //Initialization of seats array. Changes elements null to seat object.
        }
    }

    private int vehicleId;
    private float seatPrice;
    private String departureLocation;
    private String arrivalLocation;
    private int numberOfSeats;
    private Seat[] seatsArray; //Seats are class for extensibility(exp: customer information can be saved in seat object)
    private double totalRevenue;


    /**
     * Returns the unique ID of the vehicle.
     *
     * @return The vehicle ID as an integer.
     */
    public int getVehicleId() {
        return vehicleId;
    }

    /**
     * Returns the price of a seat on the vehicle.
     *
     * @return The seat price as a float.
     */
    public float getSeatPrice() {
        return seatPrice;
    }

    /**
     * Returns the departure location of the vehicle.
     *
     * @return The departure location as a string.
     */
    public String getDepartureLocation() {
        return departureLocation;
    }

    /**
     * Returns the arrival location of the vehicle.
     *
     * @return The arrival location as a string.
     */
    public String getArrivalLocation() {
        return arrivalLocation;
    }

    /**
     * Returns the array of seats on the vehicle.
     *
     * @return An array of Seat objects.
     */
    public Seat[] getSeatsArray() {
        return seatsArray;
    }

    /**
     * Returns the total revenue generated by the vehicle.
     *
     * @return The total revenue as a double.
     */
    public double getTotalRevenue() {
        return totalRevenue;
    }

    /**
     * Sets the total revenue generated by the vehicle.
     *
     * @param totalRevenue The total revenue wanted to set as a double.
     */
    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    /**
     * Returns the total number of seats on the vehicle.
     *
     * @return The number of seats as an integer.
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }
}