/**
 * This class represents a generic item in an inventory system.
 * This is an abstract class that provides a super-class for more specific inventory item types.
 */
public abstract class InventoryItem {

    /**
     * Constructs an InventoryItem with the specified barcode, name, and price.
     *
     * @param barcode The barcode of the inventory item
     * @param name    The name of the inventory item
     * @param price   The price of the inventory item
     */
    InventoryItem(String barcode, String name, double price) {
        this.barcode = barcode;
        this.name = name;
        this.price = price;
    }

    private final String barcode;
    private final String name;
    private final double price;


    /**
     * Returns the barcode of the inventory item.
     *
     * @return The barcode of the inventory item
     */
    public String getBarcode() {
        return barcode;
    }


    /**
     * Returns the name of the inventory item.
     *
     * @return The name of the inventory item
     */
    public String getName() {
        return name;
    }


    /**
     * Returns the price of the inventory item.
     *
     * @return The price of the inventory item
     */
    public double getPrice() {
        return price;
    }
}
