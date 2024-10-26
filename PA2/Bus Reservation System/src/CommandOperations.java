import java.util.ArrayList;
import java.util.Objects;

/**
 * This class contains methods to operate various commands related to public
 * transportation vehicles. These operations include initializing voyage, selling tickets, refunding tickets,
 * canceling voyages, printing voyage details, and generating z-reports.
 */
public class CommandOperations {

    /**
     * Operates a command based on the given command line string.
     *
     * @param commandLine The command line string containing the command and its parameters.
     * @param vehicleList The list of public transportation vehicles.
     * @param outputFile  The output file to write the results of the command.
     * @throws RefundTicketError     if there is an error during ticket refunding.
     * @throws InitializeVoyageError if there is an error during voyage initialization.
     * @throws ZReportError          if there is an error during generating a z-report.
     * @throws CommandError          if there is an error with the provided command.
     * @throws SellTicketError       if there is an error during ticket selling.
     * @throws CancelVoyageError     if there is an error during voyage cancellation.
     * @throws PrintVoyageError      if there is an error during printing voyage details.
     */
    public static void operateCommand(String commandLine, ArrayList<PublicTransportationVehicle> vehicleList, String outputFile) throws RefundTicketError, InitializeVoyageError, ZReportError, CommandError, SellTicketError, CancelVoyageError, PrintVoyageError {
        //Writes command line to the output file.
        FileIO.writeToFile(outputFile, "COMMAND: " + commandLine, true, true);

        checkCommand(commandLine); //Checks command given in input file if it is proper.

        String[] contentCommandLine = commandLine.split("\t");  //Split by tab to collect info.
        String command = contentCommandLine[0];  //Command name in line

        switch (command) {
            case "INIT_VOYAGE":

                int id;
                String departure = contentCommandLine[3];
                String arrival = contentCommandLine[4];
                int numberOfRows;
                float price;
                int refundCutPercentage;

                try {
                    id = Integer.parseInt(contentCommandLine[2]);

                } catch (NumberFormatException e) {  //Catches parsing errors for ID.
                    throw new InitializeVoyageError(String.format("%s is not a positive integer, ID of a voyage must be a positive integer!", contentCommandLine[2]));
                }

                try {
                    numberOfRows = Integer.parseInt(contentCommandLine[5]);

                } catch (NumberFormatException e) {  //Catches parsing errors for number of rows.
                    throw new InitializeVoyageError(String.format("%s is not a positive integer, number of seat rows of a voyage must be a positive integer!", contentCommandLine[5]));
                }

                try {
                    price = Float.parseFloat(contentCommandLine[6]);

                } catch (NumberFormatException e) {  //Catches parsing errors for price.
                    throw new InitializeVoyageError(String.format("%s is not a positive number, price must be a positive number!", contentCommandLine[6]));
                }

                if (0 >= id) { //Not-positive id
                    throw new InitializeVoyageError(String.format("%s is not a positive integer, ID of a voyage must be a positive integer!", id));
                }
                if (0 >= numberOfRows) { //Not-positive row number for vehicle
                    throw new InitializeVoyageError(String.format("%d is not a positive integer, number of seat rows of a voyage must be a positive integer!", numberOfRows));
                }
                if (0 >= price) { //Not-positive seat price
                    throw new InitializeVoyageError(String.format("%.0f is not a positive number, price must be a positive number!", price));
                }

                for (PublicTransportationVehicle vehicle : vehicleList) {
                    if (vehicle.getVehicleId() == id) { //ID is already taken.
                        throw new InitializeVoyageError(String.format("There is already a voyage with ID of %d!", id));
                    }
                }

                switch (contentCommandLine[1]) {
                    case "Minibus":
                        //Successful creation of minibus if reaches.
                        vehicleList.add(new Minibus(id, price, departure, arrival, 2 * numberOfRows));
                        break;

                    case "Standard":
                        try {
                            refundCutPercentage = Integer.parseInt(contentCommandLine[7]);
                        } catch (NumberFormatException e) { //Catches parsing error for refund cut percentage.
                            throw new InitializeVoyageError(String.format("%s is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!", contentCommandLine[7]));
                        }

                        if (0 > refundCutPercentage || 100 < refundCutPercentage) { //Refund cut percentage is out ot range(0-100)
                            throw new InitializeVoyageError(String.format("%d is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!", refundCutPercentage));
                        }

                        //Successful creation of standard buss if reaches.
                        vehicleList.add(new StandardBuss(id, price, departure, arrival, 4 * numberOfRows, refundCutPercentage));
                        break;

                    case "Premium":
                        try {
                            refundCutPercentage = Integer.parseInt(contentCommandLine[7]);
                        } catch (NumberFormatException e) {  //Catches parsing error for refund cut percentage.
                            throw new InitializeVoyageError(String.format("%s is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!", contentCommandLine[7]));

                        }
                        if (0 > refundCutPercentage || 100 < refundCutPercentage) { //Refund cut percentage is out ot range(0-100)
                            throw new InitializeVoyageError(String.format("%d is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!", refundCutPercentage));
                        }

                        float premiumSeatPercentage;
                        try {
                            premiumSeatPercentage = Integer.parseInt(contentCommandLine[8]);
                        } catch (NumberFormatException e) {  //Catches parsing error for premium seat percentage.
                            throw new InitializeVoyageError(String.format("%s is not a non-negative integer, premium fee must be a non-negative integer!", contentCommandLine[8]));

                        }
                        if (0 > premiumSeatPercentage) { //Negative premium seat price
                            throw new InitializeVoyageError(String.format("%.0f is not a non-negative integer, premium fee must be a non-negative integer!", premiumSeatPercentage));
                        }
                        //Successful creation of premium buss if reaches.
                        vehicleList.add(new PremiumBuss(id, price, premiumSeatPercentage, departure, arrival, 3 * numberOfRows, refundCutPercentage));
                        break;
                }

                //Informative message about created voyage to the output file.
                Messages.InitializeVoyage.printMessage(vehicleList.get(vehicleList.size() - 1), outputFile);
                break;


            case "Z_REPORT":
                if (!vehicleList.isEmpty()) {
                    Messages.ZReport.printMessage(vehicleList, outputFile);
                    FileIO.writeToFile(outputFile, "", true, true);
                    break;
                } else  //There is no voyage left.
                {
                    throw new ZReportError("Z Report:\n----------------\nNo Voyages Available!\n----------------");
                }

            case "SELL_TICKET":
                int givenBussId; //Given buss id in input.
                try {
                    givenBussId = Integer.parseInt(contentCommandLine[1]);
                } catch (NumberFormatException e) {  //Catches parsing error for ID.
                    throw new SellTicketError(String.format("%s is not a positive integer, ID of a voyage must be a positive integer!", contentCommandLine[1]));
                }

                if (0 >= givenBussId) {  //Not-positive buss id.
                    throw new SellTicketError(String.format("%d is not a positive integer, ID of a voyage must be a positive integer!", givenBussId));
                }

                boolean isBussFound = false;
                for (PublicTransportationVehicle vehicle : vehicleList) {

                    if (vehicle.getVehicleId() == givenBussId) { //Vehicle is found in list(ID matched)
                        isBussFound = true;

                        ArrayList<Integer> seatNumbers = new ArrayList<>();
                        boolean areSeatNumbersReservable = true;
                        for (String number : contentCommandLine[2].split("_")) {
                            int seatNumber = Integer.parseInt(number);

                            if (seatNumber < 0) {  //Negative seat number.
                                throw new SellTicketError(String.format("%d is not a positive integer, seat number must be a positive integer!", seatNumber));
                            }

                            try {
                                if (vehicle.getSeatsArray()[seatNumber - 1].isReserved()) {
                                    areSeatNumbersReservable = false; //Seat is already reserved.
                                }
                            } catch (Exception e) { //There is no such a seat.
                                throw new SellTicketError("There is no such a seat!");
                            }
                            seatNumbers.add(seatNumber);
                        }

                        //Checks if same seats wanted to be bought.
                        for (int i = 0; i<seatNumbers.size(); i++) {
                            for (int j = i+1; j<seatNumbers.size(); j++){
                                if (Objects.equals(seatNumbers.get(i), seatNumbers.get(j))){
                                    throw new SellTicketError("Same seats cannot be sold more than once.");
                                }
                            }
                        }

                        double payment = 0;
                        if (areSeatNumbersReservable) {
                            for (String number : contentCommandLine[2].split("_")) {
                                int seatNumber = Integer.parseInt(number);

                                //Tickets are sold.
                                payment += OperationManager.sellTicket(vehicle, seatNumber);

                            }
                            //Informative sell-ticket message is written to the output file.
                            Messages.SellTicket.printMessage(vehicle, outputFile, contentCommandLine[2], payment);

                        } else { //One or more seats are already sold!
                            throw new SellTicketError("One or more seats already sold!");
                        }
                    }
                }
                if (!isBussFound) { //There is not a voyage with given ID.
                    throw new SellTicketError(String.format("There is no voyage with ID of %d!", givenBussId));
                }
                break;


            case "REFUND_TICKET":
                int givenVehicleId;  //Given buss id in input.

                try {
                    givenVehicleId = Integer.parseInt(contentCommandLine[1]);
                } catch (NumberFormatException e) {//Catches parsing error for ID.
                    throw new RefundTicketError(String.format("%s is not a positive integer, ID of a voyage must be a positive integer!", contentCommandLine[1]));
                }

                if (0 >= givenVehicleId) { //Not-positive vehicle ID
                    throw new RefundTicketError(String.format("%d is not a positive integer, ID of a voyage must be a positive integer!", givenVehicleId));
                }

                boolean isVehicleFound = false;
                for (PublicTransportationVehicle vehicle : vehicleList) {

                    if (vehicle.getVehicleId() == givenVehicleId) { //Vehicle is found in the list.(ID matched)
                        isVehicleFound = true;

                        ArrayList<Integer> seatNumbers = new ArrayList<>();
                        boolean areSeatNumbersRefundable = true;
                        if (!(vehicle instanceof Minibus)) {
                            for (String number : contentCommandLine[2].split("_")) {
                                int seatNumber = Integer.parseInt(number);

                                if (seatNumber < 0) { //Negative seat number
                                    throw new SellTicketError(String.format("%d is not a positive integer, seat number must be a positive integer!", seatNumber));
                                }

                                try {
                                    if (!vehicle.getSeatsArray()[seatNumber - 1].isReserved()) {
                                        areSeatNumbersRefundable = false; //Seat is not reserved? Refund is not possible.
                                    }
                                } catch (Exception e) { //"There is no such a seat
                                    throw new SellTicketError("There is no such a seat!");
                                }
                                seatNumbers.add(seatNumber);

                            }
                        } else { //Refund try for non-refundable vehicle
                            throw new RefundTicketError("Minibus tickets are not refundable!");
                        }


                        //Checks if same seats wanted to be refunded.
                        for (int i = 0; i<seatNumbers.size(); i++) {
                            for (int j = i+1; j<seatNumbers.size(); j++){
                                if (Objects.equals(seatNumbers.get(i), seatNumbers.get(j))){
                                    throw new RefundTicketError("Same seats cannot be refunded more than once.");
                                }
                            }
                        }

                        if (areSeatNumbersRefundable) {
                            double totalRefundPayment = 0;
                            for (String number : contentCommandLine[2].split("_")) {
                                int seatNumber = Integer.parseInt(number);

                                //Tickets are refunded.
                                totalRefundPayment += OperationManager.refundTicket(vehicle, seatNumber);
                            }
                            //Informative refund-ticket message is written to the output file.
                            Messages.RefundTicket.printMessage(vehicle, outputFile, contentCommandLine[2], totalRefundPayment);

                        } else { //One or more seats are already empty. Refund is not possible.
                            throw new RefundTicketError("One or more seats are already empty!");
                        }
                    }
                }
                if (!isVehicleFound) { //There is no voyage with given ID
                    throw new RefundTicketError(String.format("There is no voyage with ID of %d!", givenVehicleId));
                }
                break;


            case "CANCEL_VOYAGE":
                int idOfVehicle;  //Given buss id in input.


                try {
                    idOfVehicle = Integer.parseInt(contentCommandLine[1]);
                } catch (NumberFormatException e) { //Catches parsing error for ID.
                    throw new CancelVoyageError(String.format("%s is not a positive integer, ID of a voyage must be a positive integer!", contentCommandLine[1]));
                }
                if (0 >= idOfVehicle) { //Not-positive ID for vehicle.
                    throw new CancelVoyageError(String.format("%d is not a positive integer, ID of a voyage must be a positive integer!", idOfVehicle));
                }

                boolean isGivenVehicleFound = false;
                for (PublicTransportationVehicle vehicle : vehicleList) {
                    if (vehicle.getVehicleId() == idOfVehicle) { //Vehicle is found in list.(ID matched)

                        OperationManager.cancelVoyage(vehicleList, idOfVehicle); //Voyage is cancelled.

                        //Informative cancel-voyage message is written to the output file.
                        Messages.CancelVoyage.printMessage(vehicle, outputFile);
                        isGivenVehicleFound = true;
                        break;
                    }
                }
                if (!isGivenVehicleFound) { //There is no voyage with given ID.
                    throw new CancelVoyageError(String.format("There is no voyage with ID of %d!", idOfVehicle));
                }
                break;

            case "PRINT_VOYAGE":

                int idOfGivenVehicle;
                try {
                    idOfGivenVehicle = Integer.parseInt(contentCommandLine[1]);
                } catch (NumberFormatException e) { //Catches parsing error for ID.
                    throw new PrintVoyageError(String.format("%s is not a positive integer, ID of a voyage must be a positive integer!", contentCommandLine[1]));
                }

                if (0 >= idOfGivenVehicle) { //Not-positive ID for vehicle.
                    throw new PrintVoyageError(String.format("%s is not a positive integer, ID of a voyage must be a positive integer!", idOfGivenVehicle));
                }


                boolean isVoyageFound = false;
                for (PublicTransportationVehicle vehicle : vehicleList) { //Vehicle found in list.(ID matched)
                    if (vehicle.getVehicleId() == idOfGivenVehicle) {

                        //Voyage details is written to the output file.
                        Messages.PrintVoyage.printMessage(vehicle, outputFile);
                        isVoyageFound = true;
                    }
                }
                if (!isVoyageFound) { //There is no voyage with given ID.
                    throw new CancelVoyageError(String.format("There is no voyage with ID of %s!", contentCommandLine[1]));
                }
        }
    }


    /**
     * Validates a command line input based on different command types.
     * Depending on the command type, specific checks are performed
     * to ensure the command line input is valid and suits to the expected format.
     *
     * @param commandLine The command line input as a string, containing the command and its parameters separated by tabs.
     * @throws CommandError          If there is an error with the general command usage or an unrecognized command.
     * @throws PrintVoyageError      If there is an error with the "PRINT_VOYAGE" command, such as incorrect parameter count.
     * @throws CancelVoyageError     If there is an error with the "CANCEL_VOYAGE" command, such as incorrect parameter count.
     * @throws SellTicketError       If there is an error with the "SELL_TICKET" command, such as invalid seat numbers.
     * @throws InitializeVoyageError If there is an error with the "INIT_VOYAGE" command, such as invalid vehicle type.
     * @throws RefundTicketError     If there is an error with the "REFUND_TICKET" command, such as invalid seat numbers.
     */
    public static void checkCommand(String commandLine) throws CommandError, PrintVoyageError, SellTicketError, InitializeVoyageError, RefundTicketError, CancelVoyageError {

        String[] contentCommandLine = commandLine.split("\t");
        switch (contentCommandLine[0]) { //contentCommandLine[0]: given voyage command in input file lines.

            case "INIT_VOYAGE":
                switch (contentCommandLine[1]) {
                    case "Minibus":
                        if (contentCommandLine.length != 7) {
                            //Minibus initialization command line contains 7 info which are split by tab.
                            throw new InitializeVoyageError("Erroneous usage of \"INIT_VOYAGE\" command!");
                        }

                        break;

                    case "Standard":
                        if (contentCommandLine.length != 8) {
                            //Standard buss initialization command line contains 8 info which are split by tab.
                            throw new InitializeVoyageError("Erroneous usage of \"INIT_VOYAGE\" command!");
                        }
                        break;

                    case "Premium":
                        if (contentCommandLine.length != 9) {
                            //Premium buss initialization command line contains 9 info which are split by tab.
                            throw new InitializeVoyageError("Erroneous usage of \"INIT_VOYAGE\" command!");
                        }
                        break;

                    default:
                        //There is no other than these vehicles.
                        throw new InitializeVoyageError("Erroneous usage of \"INIT_VOYAGE\" command!");
                }
                break;

            case "Z_REPORT":
                if (commandLine.length() != 8) {
                    //Z_REPORT command line must not contain any character other than space(" ").
                    throw new CommandError("Erroneous usage of \"Z_REPORT\" command!");
                }
                break;

            case "SELL_TICKET":
                if (contentCommandLine.length != 3) {
                    //SELL_TICKET command line contains 3 information which are split by tab.
                    throw new SellTicketError("Erroneous usage of \"SELL_TICKET\" command!");
                }

                for (String seatNumber : contentCommandLine[2].split("_")) {
                    if (!seatNumber.matches("-?[0-9]+")) { //Checks if the seat number is an integer.
                        throw new SellTicketError(String.format("%s is not a positive integer, seat number must be a positive integer!", seatNumber));
                    }
                }

                break;

            case "REFUND_TICKET":
                if (contentCommandLine.length != 3) {
                    //REFUND_TICKET command line contains 3 information which are split by tab.
                    throw new RefundTicketError("Erroneous usage of \"REFUND_TICKET\" command!");
                }
                for (String seatNumber : contentCommandLine[2].split("_")) {
                    if (!seatNumber.matches("-?[0-9]+")) { //Checks if the seat number is an integer.
                        throw new RefundTicketError(String.format("%s is not a positive integer, seat number must be a positive integer!", seatNumber));

                    }
                }
                break;

            case "CANCEL_VOYAGE":
                if (contentCommandLine.length != 2) {
                    //CANCEL_VOYAGE command line contains 2 information which are split by tab.
                    throw new CancelVoyageError("Erroneous usage of \"CANCEL_VOYAGE\" command!");
                }

                break;

            case "PRINT_VOYAGE":
                if (contentCommandLine.length != 2) {
                    //PRINT_VOYAGE command line contains 2 information which are split by tab.
                    throw new PrintVoyageError("Erroneous usage of \"PRINT_VOYAGE\" command!");
                }
                break;

            default:
                //There is no command with the given name.
                throw new CommandError(String.format("There is no command namely %s!", commandLine.split("\t")[0]));
        }
    }
}