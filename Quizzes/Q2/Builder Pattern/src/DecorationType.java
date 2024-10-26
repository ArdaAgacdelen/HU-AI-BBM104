/**
 * This class represents a superclass for specific decoration types, such as tiles.
 */
public class DecorationType {

    /**
     * Constructs a new object with the given parameters.
     *
     * @param name        The name of the decoration type.
     * @param costPerUnit The cost per unit of the decoration type.
     */
    DecorationType(String name, float costPerUnit) {

        this.name = name;
        this.costPerUnit = costPerUnit;
    }

    private String name;
    private float costPerUnit; //Unit can be 1 tile, 1 square meter...


    /**
     * Returns the name of the decoration type.
     *
     * @return The name of the decoration type.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the cost per unit of the decoration type.
     *
     * @return The cost per unit of the decoration type.
     */
    public float getCostPerUnit() {
        return costPerUnit;
    }
}
