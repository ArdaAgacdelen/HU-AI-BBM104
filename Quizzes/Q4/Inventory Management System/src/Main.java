import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * This class is the Main class of the inventory management application.
 * It initializes the inventory, operates commands in input files, and writes outputs to a file.
 */
public class Main {

    /**
     * The main method of the inventory application.
     *
     * @param args the command line arguments which are
     *             args[0] is the input file path,
     *             args[1] is the output file path.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        //Creates new inventory as a map
        HashMap<String, ArrayList<InventoryItem>> inventory = new HashMap<>();
        inventory.put("Books", new ArrayList());
        inventory.put("Toys", new ArrayList());
        inventory.put("Stationery", new ArrayList());

        //Either creates or cleans the output file
        FileIO.writeToFile(args[1], "", false,false);

        //Operates the operations specified by input commands
        InputManager.operateLines(args[0], args[1], inventory);

        //Operations to delete empty lines in output file.
        String[] lines = FileIO.readFile(args[1], true, true);
        FileIO.writeToFile(args[1], "", false, false);
        for (String line: lines){
            FileIO.writeToFile(args[1], line, true, true);
        }
    }
}
