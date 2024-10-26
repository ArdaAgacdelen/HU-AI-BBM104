/**
 * This class represents a stationery item in an inventory system.
 * It extends the InventoryItem class and includes additional
 * information specific to stationery, such as the kind of stationery.
 */
public class Stationery extends InventoryItem {

    /**
     * Constructs a new Stationery object with the specified barcode, name,
     * price, and kind.
     *
     * @param barcode The barcode of the stationery item
     * @param name    The name of the stationery item
     * @param price   The price of the stationery item
     * @param kind    The kind of stationery
     */
    Stationery(String barcode, String name, double price, String kind) {
        super(barcode, name, price);
        this.kind = kind;
    }

    private final String kind;


    /**
     * Returns the kind of the stationery item.
     *
     * @return The kind of the stationery item
     */
    public String getKind() {
        return kind;
    }


    /**
     * Returns a string representation of the stationery item.
     *
     * @return A string representation of the stationery item
     */
    @Override
    public String toString() {
        return String.format("Kind of the %s is %s. Its barcode is %s and its price is %s", getName(), getKind(), getBarcode(), getPrice());
    }
}
