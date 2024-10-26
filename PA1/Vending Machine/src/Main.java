/**
 * This class represents main class of a meal vending machine, includes operations and method calls.
 */
public class Main {
    /**
     * Precondition: This main must be run with command line arguments.
     * args[0] : Product.txt
     * args[1] : Purchase.txt
     * args[2] : GMMOutput.txt (Source code could create the file, but file name is required.)
     */
    public static void main(String[] args) {

        FileOutput.writeToFile(args[2], "", false, false); //Creates an empty GMMOutput.txt

        Slot[] slots = new Slot[24];
        for (int i = 0; i != 24; i++) { //Creates new Slot objects for all indexes.
            slots[i] = new Slot();
        }

        SlotManager.fill(slots, args[0], args[2]); //Fills slots according to Product.txt (Returns -1 when GMM is full)

        SlotManager.writeCurrentSlotsOfMachine(slots, args[2]); //Writes GMM's current state to the GMMOutput.txt

        String[] linesOfPurchaseFile = FileInput.readFile(args[1], true, true);
        for (String line : linesOfPurchaseFile) { //Makes purchasing process for every line
            PurchaseManager.purchase(slots, line, args[2]); //(Returns -1 when an error occurs)
        }

        SlotManager.writeCurrentSlotsOfMachine(slots, args[2]);
    }
}