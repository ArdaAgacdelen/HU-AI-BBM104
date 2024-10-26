/**
 * This class defines Food objects, also holds price and nutrition values of the food.
 */
public class Food {

    /**
     * Constructor is the only way to assign price since changing price value is not needed after the initialization.
     * @param price Price of the product.
     */
    Food(double price){
        this.price = price;
    }

    double fat = -6;
    double carb = -6;
    double protein = -6;
    double calorie = -6;
    private double price ; //Access modifier is private to be sure price value can only be changed when food changes.


    public double getPrice() {
        return price;
    }
}
