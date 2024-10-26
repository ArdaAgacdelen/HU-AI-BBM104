/**
 * This class represents a utility class for accessing and modifying slots.
 */
public class SlotManager {

    /**
     * Calculates calorie value with given food macro-values.
     *
     * @param protein Protein value of food.
     * @param carb    Carbohydrate value of food.
     * @param fat     Fat value of food.
     * @return Calorie value of food as double.
     */
    private static double calculateCalorie(double protein, double carb, double fat) {
        return 4 * protein + 4 * carb + 9 * fat;
    }


    /**
     * Checks whether given slot is suitable for filling with the given food.
     *
     * @param slot     Slot wanted to fill.
     * @param foodName Food will be inserted into slot.
     * @return Boolean (True if slot is suitable and false if it is not.)
     */
    private static boolean isSlotSuitable(Slot slot, String foodName) {
        return (slot == null || slot.foodName == null) || (slot.foodNumberInSlot < 10 && slot.foodName.equals(foodName));
    }


    /**
     * Fills slots with using data in input-file.
     * Writes an error message (Machine is full.) when all slots are at full capacity.
     *
     * @param slotsArray           Array of slot objects which will be changed with new products filling process.
     * @param inputProductFileName File which product-informations will be collected.
     * @param outputFileName       File which error messages will be written.
     * @return 1 when any problem occurs and -1 after any attempt of filling after machine is fully filled.
     */
    public static int fill(Slot[] slotsArray, String inputProductFileName, String outputFileName) {

        String[] lines = FileInput.readFile(inputProductFileName, true, true);
        for (String line : lines) {

            //Splits content of the line to the three. (Food Name, price, nutrition values(protein, carb, fat))
            String[] lineContent = line.split("\t");
            String inputFoodName = lineContent[0];
            double inputFoodPrice = Double.parseDouble(lineContent[1]);
            String inputFoodNutritions = lineContent[2];

            boolean isProductFoundSlot = false;

            for (Slot slot : slotsArray) {

                if (isSlotSuitable(slot, inputFoodName)) {
                    //Records Slot and Food data.
                    isProductFoundSlot = true;
                    slot.foodName = inputFoodName;
                    slot.foodNumberInSlot++;
                    slot.food = new Food(inputFoodPrice); //Inıtıalızatıon of food object.
                    slot.food.protein = Double.parseDouble(inputFoodNutritions.split(" ")[0]);
                    slot.food.carb = Double.parseDouble(inputFoodNutritions.split(" ")[1]);
                    slot.food.fat = Double.parseDouble(inputFoodNutritions.split(" ")[2]);
                    slot.food.calorie = calculateCalorie(slot.food.protein, slot.food.carb, slot.food.fat);
                    break;
                }
            }

            if (!isProductFoundSlot) { //Product couldn't be placed.
                FileOutput.writeToFile(outputFileName, String.format("INFO: There is no available place to put %s",
                        inputFoodName), true, true);

                if (isAllSlotsFull(slotsArray)) { //Machine has not any space.
                    FileOutput.writeToFile(outputFileName, "INFO: The machine is full!",
                            true, true);
                    return -1;
                }
            }
        }
        return 1; //Machine is filled without any problem.
    }


    /**
     * Writes current statement of the GMM to the given output file.
     *
     * @param slotsArray     Array of slots which is going to be represented.
     * @param outputFileName File that is going to be written on.
     */
    public static void writeCurrentSlotsOfMachine(Slot[] slotsArray, String outputFileName) {

        FileOutput.writeToFile(outputFileName, "-----Gym Meal Machine-----", true, true);

        for (int i = 1; i <= 24; i++) {
            Slot slot = slotsArray[i - 1];

            if (slot.foodNumberInSlot != 0) { //Slot has at least 1 food.
                FileOutput.writeToFile(outputFileName, String.format("%s(%d, %d)___", slot.foodName,
                        (int) (slot.food.calorie + 0.5), slot.foodNumberInSlot), true, i % 4 == 0);
            } else { //Slot is empty.
                FileOutput.writeToFile(outputFileName, "___(0, 0)___", true, i % 4 == 0);
            }
        }
        FileOutput.writeToFile(outputFileName, "----------", true, true);
    }


    /**
     * Checks whether all slots are full or not.
     *
     * @param slotsArray Array of slots which is going to be checked.
     * @return True when all slots are full and false when they are not.
     */
    private static boolean isAllSlotsFull(Slot[] slotsArray) {
        for (Slot slot : slotsArray) {
            if (slot.foodNumberInSlot != 10) { //Looks for a slot that is not fully filled.
                return false;
            }
        }
        return true; //All slots are at full capacity.
    }
}
