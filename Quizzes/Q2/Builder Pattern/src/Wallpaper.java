/**
 * This class represents a type of decoration called wallpaper, inheriting properties
 * from the DecorationType class.
 */
public class Wallpaper extends DecorationType{

    /**
     * Constructs a new object with the given parameters.
     *
     * @param name         The name of the paint decoration type.
     * @param costPerUnit  The cost per unit(1 square meter) of the wallpaper decoration type.
     */
    Wallpaper(String name, float costPerUnit) {
        super(name, costPerUnit);
    }
}
