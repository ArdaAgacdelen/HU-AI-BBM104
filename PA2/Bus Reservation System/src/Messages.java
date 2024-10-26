import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

/**
 * This class contains various nested classes that gives operation messages related to public transportation vehicles.
 * These operations include generating z-reports, ticket sales and refunds,
 * canceling voyages, initializing voyages, and printing voyage details.
 */
public class Messages {

    /**
     * This nested class contains method for generating a summary report for all the current voyages.
     */
    public static class ZReport {

        /**
         * Prepares a z-report for the given list of public transportation vehicles
         * and writes it to the specified output file.
         *
         * @param vehicleList The list of public transportation vehicles to include in the report.
         * @param outputFile  The path to the file where the report will be written.
         */
        public static void printMessage(ArrayList<PublicTransportationVehicle> vehicleList, String outputFile) {

            //Sorts the vehicle list by vehicles ID's.
            vehicleList.sort(Comparator.comparingInt(PublicTransportationVehicle::getVehicleId));

            FileIO.writeToFile(outputFile, "Z Report:\n----------------", true, true);
            for (PublicTransportationVehicle vehicle : vehicleList) {
                FileIO.writeToFile(outputFile, String.format(new Locale("en", "us"), "Voyage %d\n%s-%s", vehicle.getVehicleId(), vehicle.getDepartureLocation(), vehicle.getArrivalLocation()), true, true);

                if (vehicle instanceof Minibus) {
                    for (int i = 0; i < vehicle.getNumberOfSeats(); i++) {

                        //Seats of minibus are located as 2 side by side seats.
                        if ((i + 1) % 2 == 0) { //if (second seat): newline
                            FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X" : "*", true, true);
                        } else {  //if (first seat): no newline
                            FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X " : "* ", true, false);
                        }
                    }
                    FileIO.writeToFile(outputFile, String.format(new Locale("en", "us"), "Revenue: %.2f", vehicle.getTotalRevenue()) + "\n----------------", true, true);
                }

                if (vehicle instanceof PremiumBuss) {
                    for (int i = 0; i < vehicle.getNumberOfSeats(); i++) {

                        //Seats of premium buss are located as 1+2 seats.
                        if ((i + 1) % 3 == 0) { //if (third seat): newline
                            FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X" : "*", true, true);
                        } else if ((i + 1) % 3 == 1) {//if (first seat): "|" character and no newline
                            FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X | " : "* | ", true, false);
                        } else { //if (second seat): no newline
                            FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X " : "* ", true, false);
                        }
                    }
                    FileIO.writeToFile(outputFile, String.format(new Locale("en", "us"), "Revenue: %.2f", vehicle.getTotalRevenue()) + "\n----------------", true, true);
                }

                if (vehicle instanceof StandardBuss) {
                    for (int i = 0; i < vehicle.getNumberOfSeats(); i++) {

                        //Seats of normal buss are located as 2+2 seats.
                        if ((i + 1) % 4 == 0) { //if (fourth seat): newline
                            FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X" : "*", true, true);
                        } else if ((i + 1) % 4 == 1 || (i + 1) % 4 == 3) { //if (first or third seat): no newline
                            FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X " : "* ", true, false);
                        } else if ((i + 1) % 4 == 2) { //if (second seat): "|" character and no newline
                            FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X | " : "* | ", true, false);
                        }
                    }
                    FileIO.writeToFile(outputFile, String.format(new Locale("en", "us"), "Revenue: %.2f", vehicle.getTotalRevenue()) + "\n----------------", true, true);
                }
            }
        }
    }


    /**
     * This nested class contain methods for printing messages related to the sale
     * of tickets on public transportation vehicles.
     */
    public static class SellTicket {

        /**
         * Writes a message to output file indicating that a ticket was successfully sold for given seat(s)
         * on a given public transportation vehicle.
         *
         * @param vehicle     The public transportation vehicle on which the ticket was sold.
         * @param outputFile  The path to the file where the message will be written.
         * @param seatNumbers The seat numbers that were sold, as a string.
         * @param payment     The total payment amount for the ticket sale.
         */
        public static void printMessage(PublicTransportationVehicle vehicle, String outputFile, String seatNumbers, double payment) {
            FileIO.writeToFile(outputFile, String.format(new Locale("en", "us"), "Seat %s of the Voyage %d from %s to %s was successfully sold for %.2f TL.", seatNumbers.replace("_", "-"), vehicle.getVehicleId(), vehicle.getDepartureLocation(), vehicle.getArrivalLocation(), payment), true, true);
        }
    }


    /**
     * This nested class contains method for printing messages related to the refund
     * of tickets on public transportation vehicles.
     */
    public static class RefundTicket {

        /**
         * Writes to output file a message indicating that a ticket was successfully refunded for given seat(s)
         * on a given public transportation vehicle.
         *
         * @param vehicle            The public transportation vehicle on which the ticket was refunded.
         * @param outputFile         The path to the file where the message will be written.
         * @param seatNumbers        The seat numbers that were refunded, as a string.
         * @param totalRefundPayment The total refund payment amount.
         */
        public static void printMessage(PublicTransportationVehicle vehicle, String outputFile, String seatNumbers, double totalRefundPayment) {
            FileIO.writeToFile(outputFile, String.format(new Locale("en", "us"), "Seat %s of the Voyage %d from %s to %s was successfully refunded for %.2f TL.", seatNumbers.replace("_", "-"), vehicle.getVehicleId(), vehicle.getDepartureLocation(), vehicle.getArrivalLocation(), totalRefundPayment), true, true);
        }
    }


    /**
     * This nested class contains method for printing messages related to the cancellation
     * of voyages on public transportation vehicles.
     */
    public static class CancelVoyage {

        /**
         * Writes to output file a message indicating that a voyage was successfully canceled for a given public
         * transportation vehicle then writes cancelled voyage details.
         *
         * @param vehicle    The public transportation vehicle for which the voyage was canceled.
         * @param outputFile The path to the file where the message will be written.
         */
        public static void printMessage(PublicTransportationVehicle vehicle, String outputFile) {
            FileIO.writeToFile(outputFile, String.format(new Locale("en", "us"), "Voyage %d was successfully cancelled!\nVoyage details can be found below:", vehicle.getVehicleId()), true, true);
            Messages.PrintVoyage.printMessage(vehicle, outputFile);
        }
    }


    /**
     * This nested class contains methods for printing messages related to the initialization
     * of voyages on public transportation vehicles.
     */
    public static class InitializeVoyage {

        /**
         * Writes to output file a message indicating that a voyage
         * was successfully initialized for a given public transportation vehicle.
         *
         * @param vehicle    The public transportation vehicle for which the voyage was initialized.
         * @param outputFile The path to the file where the message will be written.
         */
        public static void printMessage(PublicTransportationVehicle vehicle, String outputFile) {
            if (vehicle instanceof Minibus) { //Minibus message
                FileIO.writeToFile(outputFile, String.format(new Locale("en", "us"), "Voyage %d was initialized as a minibus (2) voyage from %s to %s with %.2f TL priced %d regular seats. Note that minibus tickets are not refundable.", vehicle.getVehicleId(), vehicle.getDepartureLocation(), vehicle.getArrivalLocation(), vehicle.getSeatPrice(), vehicle.getNumberOfSeats()), true, true);
            }

            if (vehicle instanceof PremiumBuss) { //Premium buss message
                FileIO.writeToFile(outputFile, String.format(new Locale("en", "us"), "Voyage %d was initialized as a premium (1+2) voyage from %s to %s with %.2f TL priced %d regular seats and %.2f TL priced %d premium seats. Note that refunds will be %d%% less than the paid amount.", vehicle.getVehicleId(), vehicle.getDepartureLocation(), vehicle.getArrivalLocation(), vehicle.getSeatPrice(), vehicle.getNumberOfSeats() * 2 / 3, ((PremiumBuss) vehicle).getPremiumSeatPrice(), vehicle.getNumberOfSeats() / 3, ((Refundable) vehicle).getRefundCutPercentage()), true, true);

            }

            if (vehicle instanceof StandardBuss) { //Standard buss message
                FileIO.writeToFile(outputFile, String.format(new Locale("en", "us"), "Voyage %d was initialized as a standard (2+2) voyage from %s to %s with %.2f TL priced %d regular seats. Note that refunds will be %d%% less than the paid amount.", vehicle.getVehicleId(), vehicle.getDepartureLocation(), vehicle.getArrivalLocation(), vehicle.getSeatPrice(), vehicle.getNumberOfSeats(), ((Refundable) vehicle).getRefundCutPercentage()), true, true);

            }
        }
    }


    /**
     * This nested class contains method for writing voyage information
     * for a given public transportation vehicle.
     */
    public static class PrintVoyage {

        /**
         * Writes to output file detailed information about a voyage for a given public transportation vehicle.
         *
         * @param vehicle    The public transportation vehicle for which the voyage information will be printed.
         * @param outputFile The path to the file where the information will be written.
         */
        public static void printMessage(PublicTransportationVehicle vehicle, String outputFile) {

            FileIO.writeToFile(outputFile, String.format(new Locale("en", "us"), "Voyage %d\n%s-%s", vehicle.getVehicleId(), vehicle.getDepartureLocation(), vehicle.getArrivalLocation()), true, true);

            if (vehicle instanceof Minibus) { //2 side by side seats
                for (int i = 0; i < vehicle.getNumberOfSeats(); i++) {

                    if ((i + 1) % 2 == 0) { //Second seat: newline
                        FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X" : "*", true, true);
                    } else { //First seat: no newline
                        FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X " : "* ", true, false);
                    }
                }
                FileIO.writeToFile(outputFile, String.format(new Locale("en", "us"), "Revenue: %.2f", vehicle.getTotalRevenue()), true, true);
            }

            if (vehicle instanceof PremiumBuss) { //1+2 design seats
                for (int i = 0; i < vehicle.getNumberOfSeats(); i++) {

                    if ((i + 1) % 3 == 0) { //Third seat: newline
                        FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X" : "*", true, true);
                    } else if ((i + 1) % 3 == 1) {//First seat: "|" character and no newline
                        FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X | " : "* | ", true, false);
                    } else { //Second seat: no newline
                        FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X " : "* ", true, false);
                    }
                }
                FileIO.writeToFile(outputFile, String.format(new Locale("en", "us"), "Revenue: %.2f", vehicle.getTotalRevenue()), true, true);
            }

            if (vehicle instanceof StandardBuss) { //2+2 design seats
                for (int i = 0; i < vehicle.getNumberOfSeats(); i++) {

                    if ((i + 1) % 4 == 0) { //Fourth seat: newline
                        FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X" : "*", true, true);
                    } else if ((i + 1) % 4 == 1 || (i + 1) % 4 == 3) { //First and third seat: no newline
                        FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X " : "* ", true, false);
                    } else if ((i + 1) % 4 == 2) { //Second seat: "|" character and no newline
                        FileIO.writeToFile(outputFile, (vehicle.getSeatsArray()[i].isReserved()) ? "X | " : "* | ", true, false);
                    }
                }
                FileIO.writeToFile(outputFile, String.format(new Locale("en", "us"), "Revenue: %.2f", vehicle.getTotalRevenue()), true, true);
            }
        }
    }
}
