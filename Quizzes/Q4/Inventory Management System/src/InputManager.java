import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class provides methods to process input lines and operate on the inventory.
 * It reads commands from an input file, processes them, and writes the results to an output file.
 */
public class InputManager {

    /**
     * Processes the input file line by line and performs the specified operations on the inventory.
     *
     * @param inputFileName  The name of the input file containing commands
     * @param outputFileName The name of the output file to write results
     * @param inventory      The inventory to operate on
     */
    public static void operateLines(String inputFileName, String outputFileName, HashMap<String, ArrayList<InventoryItem>> inventory) {
        String[] inputLines = FileIO.readFile(inputFileName, true, true);
        for (String line : inputLines) {
            operateLine(line, outputFileName, inventory);
        }
    }


    /**
     * Processes a single input line and performs the specified operation on the inventory.
     *
     * @param inputLine      The input line containing a command
     * @param outputFileName The name of the output file to write results
     * @param inventory      The inventory to operate on
     */
    public static void operateLine(String inputLine, String outputFileName, HashMap<String, ArrayList<InventoryItem>> inventory) {
        String[] lineContent = inputLine.split("\t"); //Splits line to its contents
        String command = lineContent[0];

        switch (command) {
            case "ADD":
                String type = lineContent[1];
                String name = lineContent[2];
                String barcode = lineContent[4];
                double price = Double.parseDouble(lineContent[5]);

                switch (type) {
                    case "Book":
                        String author = lineContent[3];
                        //Adds a book to inventory
                        InventoryManager.addItem(inventory.get("Books"), new Book(barcode, name, price, author));
                        break;
                    case "Toy":
                        String color = lineContent[3];
                        //Adds a toy to inventory
                        InventoryManager.addItem(inventory.get("Toys"), new Toy(barcode, name, price, color));
                        break;
                    case "Stationery":
                        String kind = lineContent[3];
                        //Adds a stationery to inventory
                        InventoryManager.addItem(inventory.get("Stationery"), new Stationery(barcode, name, price, kind));
                        break;
                }
                break;

            case "REMOVE":
                barcode = lineContent[1];
                //Looks for item with the given barcode
                InventoryItem itemToRemove = InventoryManager.search(inventory, barcode, false);

                FileIO.writeToFile(outputFileName, "REMOVE RESULTS:\n", true, true);

                if (itemToRemove != null) { //Item is found
                    //Removes item
                    InventoryManager.removeItem(inventory, barcode);
                    FileIO.writeToFile(outputFileName, "Item is removed.\n", true, true);

                } else { //Item couldn't be found
                    FileIO.writeToFile(outputFileName, "Item is not found.\n", true, true);
                }

                FileIO.writeToFile(outputFileName, "------------------------------", true, true);

                break;

            case "SEARCHBYBARCODE":
                barcode = lineContent[1];
                //Looks for item with the given barcode
                InventoryItem item = InventoryManager.search(inventory, barcode, false);

                FileIO.writeToFile(outputFileName, "SEARCH RESULTS:\n", true, true);

                if (item != null) { //Item is found
                    FileIO.writeToFile(outputFileName, item.toString(), true, true);

                } else { //Item couldn't be found
                    FileIO.writeToFile(outputFileName, "Item is not found.\n", true, true);
                }

                FileIO.writeToFile(outputFileName, "------------------------------", true, true);

                break;


            case "SEARCHBYNAME":
                name = lineContent[1];
                //Looks for item with the given name
                item = InventoryManager.search(inventory, name, true);

                FileIO.writeToFile(outputFileName, "SEARCH RESULTS:\n", true, true);

                if (item != null) { //Item is found
                    FileIO.writeToFile(outputFileName, item.toString(), true, true);

                } else { //Item couldn't be found
                    FileIO.writeToFile(outputFileName, "Item is not found.\n", true, true);
                }

                FileIO.writeToFile(outputFileName, "------------------------------", true, true);

                break;

            case "DISPLAY":
                ArrayList<InventoryItem> books = inventory.get("Books"); //Books' list
                ArrayList<InventoryItem> toys = inventory.get("Toys"); //Toys' list
                ArrayList<InventoryItem> stationeries = inventory.get("Stationery"); //Stationery' list

                FileIO.writeToFile(outputFileName, "INVENTORY:", true, true);

                for (InventoryItem book : books) { //Writes books respectively
                    FileIO.writeToFile(outputFileName, book.toString(), true, true);
                }
                for (InventoryItem toy : toys) { //Writes toys respectively
                    FileIO.writeToFile(outputFileName, toy.toString(), true, true);
                }
                for (InventoryItem stationery : stationeries) { //Writes stationeries respectively
                    FileIO.writeToFile(outputFileName, stationery.toString(), true, true);
                }

                FileIO.writeToFile(outputFileName, "------------------------------", true, true);
        }
    }
}
