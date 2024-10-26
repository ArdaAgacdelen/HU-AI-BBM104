import java.util.ArrayList;

/**
 * This class provides methods to manage ticket sales, refunds, and voyage cancellations
 * for public transportation vehicles. It handles operations such as selling tickets, refunding tickets,
 * canceling voyages.
 */
public class OperationManager {

    /**
     * Sells a ticket for a specified seat number on a public transportation vehicle.
     *
     * @param vehicle    The public transportation vehicle on which the ticket is to be sold.
     * @param seatNumber The seat number(s) for which the ticket is to be sold.
     * @return The price of the ticket sold.
     * @throws SellTicketError If the seat number is negative or invalid.
     */
    public static float sellTicket(PublicTransportationVehicle vehicle, int seatNumber) throws SellTicketError {
        if (seatNumber < 0) { //Negative seat number
            throw new SellTicketError(String.format("%d is not a positive integer, seat number must be a positive integer!", seatNumber));
        }

        try {
            vehicle.getSeatsArray()[seatNumber - 1].setReserved(true);
        } catch (Exception e) { //Not existing seat number
            throw new SellTicketError("There is no such a seat!");
        }

        if (!vehicle.getSeatsArray()[seatNumber - 1].isPremiumSeat()) { //Normal seat
            vehicle.setTotalRevenue(vehicle.getTotalRevenue() + vehicle.getSeatPrice());
            return vehicle.getSeatPrice();

        } else { //Premium seat.
            if (vehicle instanceof PremiumBuss) {
                vehicle.setTotalRevenue(vehicle.getTotalRevenue() + ((PremiumBuss) vehicle).getPremiumSeatPrice());
                return ((PremiumBuss) vehicle).getPremiumSeatPrice();
            }
        }
        return 0f;
    }

    /**
     * Refunds a ticket for a specified seat number on a public transportation vehicle.
     *
     * @param vehicle    The public transportation vehicle from which the ticket is to be refunded.
     * @param seatNumber The seat number for which the ticket is to be refunded.
     * @return The refund amount for the ticket.
     * @throws RefundTicketError If the vehicle doesn't allow refunds or seat number is negative or invalid
     */
    public static float refundTicket(PublicTransportationVehicle vehicle, int seatNumber) throws RefundTicketError {

        if (vehicle instanceof Refundable) {

            if (seatNumber < 0) { //Negative seat number
                throw new RefundTicketError(String.format("%d is not a positive integer, seat number must be a positive integer!", seatNumber));
            }

            try {
                vehicle.getSeatsArray()[seatNumber - 1].setReserved(false);
            } catch (Exception e) { //Not existing seat number
                throw new RefundTicketError("There is no such a seat!");
            }

            if (!vehicle.getSeatsArray()[seatNumber - 1].isPremiumSeat()) { //Normal seat
                vehicle.setTotalRevenue(vehicle.getTotalRevenue() - calculateRefund(vehicle, false));
                return calculateRefund(vehicle, false);

            } else { //Seat is a premium seat.
                if (vehicle instanceof PremiumBuss) {
                    vehicle.setTotalRevenue(vehicle.getTotalRevenue() - calculateRefund(vehicle, true));
                }
                return calculateRefund(vehicle, true);
            }
        } else { //Vehicle seats are not refundable.
            throw new RefundTicketError("Minibus tickets are not refundable!");
        }
    }


    /**
     * Cancels a voyage for given vehicle ID.
     *
     * @param vehicleList The list of public transportation vehicles.
     * @param vehicleId   The ID of the vehicle whose voyage will be cancelled.
     */
    public static void cancelVoyage(ArrayList<PublicTransportationVehicle> vehicleList, int vehicleId) {

        for (int i = 0; i < vehicleList.size(); i++) {

            if (vehicleList.get(i).getVehicleId() == vehicleId) { //Vehicle is found in list. (ID matched)

                //Iterates the seat list to refund reserved seats.(No refund cut)
                for (Seat seat : vehicleList.get(i).getSeatsArray()) { //
                    if (seat.isReserved()) {

                        if (vehicleList.get(i) instanceof PremiumBuss) {
                            if (seat.isPremiumSeat()) { //Seat is premium.
                                vehicleList.get(i).setTotalRevenue(vehicleList.get(i).getTotalRevenue() - ((PremiumBuss) vehicleList.get(i)).getPremiumSeatPrice());
                            } else {  //Seat is normal seat.
                                vehicleList.get(i).setTotalRevenue(vehicleList.get(i).getTotalRevenue() - vehicleList.get(i).getSeatPrice());
                            }
                        } else { //Seat is normal seat.
                            vehicleList.get(i).setTotalRevenue(vehicleList.get(i).getTotalRevenue() - vehicleList.get(i).getSeatPrice());
                        }
                    }
                }
                vehicleList.remove(i); //Deletes voyage in list.
                break;
            }
        }
    }


    /**
     * Calculates refund amount for given vehicle and seat type.
     *
     * @param vehicle       The vehicle whose seat is going to be refunded.
     * @param isPremiumSeat Is seat premium(true) or normal(false) seat.
     * @return Calculates refund amount for seat.
     */
    public static float calculateRefund(PublicTransportationVehicle vehicle, boolean isPremiumSeat) {
        float refundCutConstant = 1 - (((Refundable) vehicle).getRefundCutPercentage()) / 100f;

        return (isPremiumSeat) ? ((PremiumBuss) vehicle).getPremiumSeatPrice() * refundCutConstant : vehicle.getSeatPrice() * refundCutConstant;
    }
}