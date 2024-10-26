import java.util.*;

/**
 * This class provides utility methods to manage an inventory of inventory items.
 * It includes methods to add, remove, and search for items in the inventory.
 */
public class InventoryManager {

    /**
     * Adds an item to the specified list of items.
     *
     * @param <T>   The type of inventory item
     * @param items The list of items to which the item will be added
     * @param item  The item to be added to the list
     */
    public static <T extends InventoryItem> void addItem(List<T> items, T item) {
        items.add(item);
    }


    /**
     * Removes an item from the inventory based on its barcode.
     * The method searches through all categories in the inventory to find and remove the item.
     *
     * @param inventory The inventory from which the item will be removed
     * @param barcode   The barcode of the item to be removed
     */
    public static void removeItem(HashMap<String, ArrayList<InventoryItem>> inventory, String barcode) {
        Set<String> keys = inventory.keySet();

        outer:
        //Defines label for outer "for" loop
        for (String key : keys) { //For all types of item lists
            ArrayList<InventoryItem> itemsList = inventory.get(key);
            for (InventoryItem item : itemsList) {
                if (item.getBarcode().equals(barcode)) {
                    itemsList.remove(item);
                    break outer;
                }
            }
        }
    }


    /**
     * Searches for an item in the inventory based on a search key.
     * The search can be conducted using either the item's name or barcode.
     *
     * @param inventory            The inventory in which to search for the item
     * @param searchKey            The search key, which can be either the item's name or barcode
     * @param isSearchCriteriaName {@code true} to search by name, {@code false} to search by barcode
     * @return if found, InventoryItem object; else, null
     */
    public static InventoryItem search(HashMap<String, ArrayList<InventoryItem>> inventory, String searchKey, boolean isSearchCriteriaName) {
        Set<String> keys = inventory.keySet();

        if (isSearchCriteriaName) {
            for (String key : keys) { //For all types of item lists
                ArrayList<InventoryItem> itemsList = inventory.get(key);
                for (InventoryItem item : itemsList) {
                    if (item.getName().equals(searchKey)) {
                        return item;
                    }
                }
            }
        } else {
            for (String key : keys) { //For all types of item lists
                ArrayList<InventoryItem> itemsList = inventory.get(key);
                for (InventoryItem item : itemsList) {
                    if (item.getBarcode().equals(searchKey)) {
                        return item;
                    }
                }
            }
        }
        return null;
    }
}