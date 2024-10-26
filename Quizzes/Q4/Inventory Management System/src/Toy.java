/**
 * This class represents a toy in an inventory system.
 * It extends the InventoryItem class and includes additional
 * information specific to toys, such as the color.
 */
public class Toy extends InventoryItem {

    /**
     * Constructs a new Toy object with the specified barcode, name, price, and color.
     *
     * @param barcode The barcode of the toy
     * @param name    The name of the toy
     * @param price   The price of the toy
     * @param color   The color of the toy
     */
    Toy(String barcode, String name, double price, String color) {
        super(barcode, name, price);
        this.color = color;
    }

    private final String color;


    /**
     * Returns the color of the toy.
     *
     * @return The color of the toy
     */
    public String getColor() {
        return color;
    }


    /**
     * Returns a string representation of the toy.
     *
     * @return A string representation of the toy
     */
    @Override
    public String toString() {
        return String.format("Color of the %s is %s. Its barcode is %s and its price is %s", getName(), getColor(), getBarcode(), getPrice());
    }
}
