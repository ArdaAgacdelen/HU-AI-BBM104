/**
 * This class represents a type of decoration called tile, inheriting properties
 * from the DecorationType class. It includes additional information about the surface area of a tile.
 */
public class Tile extends DecorationType {

    /**
     * Constructs a new object with the given parameters.
     *
     * @param name        The name of the tile decoration type.
     * @param costPerUnit The cost per unit(1 tile) of the tile decoration type.
     * @param areaOfTile  The area of a single tile.
     */
    Tile(String name, float costPerUnit, double areaOfTile) {
        super(name, costPerUnit);
        this.areaOfTile = areaOfTile;
    }

    private double areaOfTile; //The area of a single tile.


    /**
     * Returns the area of a single tile.
     *
     * @return The area of a single tile.
     */
    public double getAreaOfTile() {
        return areaOfTile;
    }
}
