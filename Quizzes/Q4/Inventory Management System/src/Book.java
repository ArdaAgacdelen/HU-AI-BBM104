/**
 * This class represents a book in an inventory system.
 * It extends the InventoryItem class and includes additional
 * information specific to books, such as the author.
 */
public class Book extends InventoryItem {

    /**
     * Constructs a new Book object with the specified barcode, name,
     * price, and author.
     *
     * @param barcode The barcode of the book
     * @param name    The name of the book
     * @param price   The price of the book
     * @param author  The author of the book
     */
    Book(String barcode, String name, double price, String author) {
        super(barcode, name, price);
        this.author = author;
    }

    private final String author;


    /**
     * Returns the author of the book.
     *
     * @return The author of the book
     */
    public String getAuthor() {
        return author;
    }


    /**
     * Returns a string representation of the book.
     *
     * @return A string representation of the book
     */
    @Override
    public String toString() {
        return String.format("Author of the %s is %s. Its barcode is %s and its price is %s", getName(), getAuthor(), getBarcode(), getPrice());
    }
}
