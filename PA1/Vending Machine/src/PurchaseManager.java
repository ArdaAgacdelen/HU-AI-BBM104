/**
 * This class represents a utility class for purchasing operations of a gym meal machine.
 */
public class PurchaseManager {

    /**
     * This method trys to find proper product with respect to customer's requests about nutrition or slot number.
     * If finds, gives product and change.
     * If can't find; returns money.
     *
     * @param slotsArray     Current array of slots.
     * @param line           Line that includes purchase data.
     * @param outputFileName File that message is going to be written.
     * @return 1 if purchase has done successfully and -1 if any error occurs.
     */
    public static int purchase(Slot[] slotsArray, String line, String outputFileName) {
        int returnValue = 1;

        String[] lineContent = line.split("\t"); //Splits line to the 4 content.
        //lineContent[1] : Money
        //lineContent[2] : Macro or Slot Choice
        //lineContent[3] : Macro Value or Slot Number


        int totalMoneyGiven = 0;
        boolean isMoneyAcceptable = true;
        int totalInvalidMoney = 0; //Total money that is not valid for machine.
        int wantedNutritionalValue = Integer.parseInt(lineContent[3]);

        for (String strMoney : lineContent[1].split(" ")) { //Money inputs as string. "x x x".split(" ")

            totalMoneyGiven += Integer.parseInt(strMoney); //Parses money to integer and adds total money.

            switch (strMoney) {
                case "1":
                    break;
                case "5":
                    break;
                case "10":
                    break;
                case "20":
                    break;
                case "50":
                    break;
                case "100":
                    break;
                case "200":
                    break;
                default: //Money is not valid for machine.
                    isMoneyAcceptable = false;
                    totalInvalidMoney += Integer.parseInt(strMoney);
            }
        }


        //Input message. Common for all purchase requests.
        FileOutput.writeToFile(outputFileName, String.format("INPUT: CASH\t%s\t%s\t%d", lineContent[1], lineContent[2],
                wantedNutritionalValue), true, true);


        if (!isMoneyAcceptable) { //There are invalid moneys.
            totalMoneyGiven = totalMoneyGiven - totalInvalidMoney;
            FileOutput.writeToFile(outputFileName, String.format("INFO: %d TL is given with not valid amounts, " +
                    "will be returned.", totalInvalidMoney), true, true);

            returnValue = -1; //Method will return -1 as an error has occurred.
        }


        Slot slotFound = null; //Slot that includes proper food.

        switch (lineContent[2]) { //lineContent[2] : Macro or Slot Choice

            case "PROTEIN":
                for (Slot slot : slotsArray) {

                    //Looks for ±5 protein value.
                    if (slot.food.protein <= wantedNutritionalValue + 5 && slot.food.protein >= wantedNutritionalValue - 5) {
                        if (slot.foodNumberInSlot > 0) { //Slot is not empty.
                            slotFound = slot;
                            break;
                        }
                    }
                }
                break;

            case "FAT":
                for (Slot slot : slotsArray) {

                    //Looks for ±5 fat value.
                    if (slot.food.fat <= wantedNutritionalValue + 5 && slot.food.fat >= wantedNutritionalValue - 5) {
                        if (slot.foodNumberInSlot > 0) { //Slot is not empty.
                            slotFound = slot;
                            break;
                        }
                    }
                }
                break;

            case "CARB":
                for (Slot slot : slotsArray) {

                    //Looks for ±5 carb value.
                    if (slot.food.carb <= wantedNutritionalValue + 5 && slot.food.carb >= wantedNutritionalValue - 5) {
                        if (slot.foodNumberInSlot > 0) { //Slot is not empty.
                            slotFound = slot;
                            break;
                        }
                    }
                }
                break;

            case "CALORIE":
                for (Slot slot : slotsArray) {

                    //Looks for ±5 calorie value.
                    if (slot.food.calorie <= wantedNutritionalValue + 5 && slot.food.calorie >= wantedNutritionalValue - 5) {
                        if (slot.foodNumberInSlot > 0) { //Slot is not empty.
                            slotFound = slot;
                            break;
                        }
                    }
                }
                break;

            case "NUMBER":
                if (wantedNutritionalValue <= 23 && wantedNutritionalValue >= 0) { //Number is proper.
                    slotFound = slotsArray[wantedNutritionalValue];
                    break;
                }
                break;
        }


        if (slotFound != null) { //A product matched with request.

            if (slotFound.foodNumberInSlot > 0) { //Slot is not empty.

                if (totalMoneyGiven >= slotFound.food.getPrice()) { //Enough money.
                    slotFound.foodNumberInSlot--;

                    FileOutput.writeToFile(outputFileName, String.format("PURCHASE: You have bought one %s\n" +
                            "RETURN: Returning your change: %d TL", slotFound.foodName,
                            (int) (totalMoneyGiven - slotFound.food.getPrice())), true, true);

                    if (slotFound.foodNumberInSlot == 0) { //Last product at the slot has been sold.
                        slotFound = null;
                    }

                }
                else { //Insufficient money
                    FileOutput.writeToFile(outputFileName, String.format("INFO: Insufficient money, try again with more money." +
                            "\nRETURN: Returning your change: %d TL", totalMoneyGiven), true, true);
                    returnValue = -1; //Error
                }
            }
            else { //Slot is empty.
                FileOutput.writeToFile(outputFileName, String.format("INFO: This slot is empty, " +
                        "your money will be returned.\nRETURN: Returning your change: %d TL",
                        totalMoneyGiven), true, true);
                returnValue = -1; //Error
            }
        }
        else {
            returnValue = -1; //Error

            if (!"NUMBER".equals(lineContent[2])) { //Any product matched with request.
                FileOutput.writeToFile(outputFileName, String.format("INFO: Product not found, your money will be returned.\n" +
                        "RETURN: Returning your change: %d TL", totalMoneyGiven), true, true);
            }
            else { //Number out of slots.
                FileOutput.writeToFile(outputFileName, String.format("INFO: Number cannot be accepted. Please try again " +
                        "with another number.\nRETURN: Returning your change: %d TL", totalMoneyGiven), true, true);
            }
        }
        return returnValue;
    }
}
