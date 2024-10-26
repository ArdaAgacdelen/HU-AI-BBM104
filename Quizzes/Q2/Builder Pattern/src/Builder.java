import java.lang.Math;

/**
 * This interface defines methods for construction process of a class.
 */
public interface Builder {

    /**
     * Writes the wall's construction to a specified file.
     *
     * @param outputFile The name of the output file to write the wall construction details.
     */
    void buildWalls(String outputFile);


    /**
     * Builds the walls of the classroom with the specified wall decoration type.
     *
     * @param wallDecorationType The type of decoration to be applied to the walls.
     */
    void buildWalls(DecorationType wallDecorationType);


    /**
     * Writes the floor's construction to a specified file.
     *
     * @param outputFile The name of the output file to write the floor construction details.
     */
    void buildFloor(String outputFile);


    /**
     * Builds the floor of the classroom with the specified floor decoration type.
     *
     * @param floorDecorationType The type of decoration to be applied to the floor.
     */
    void buildFloor(DecorationType floorDecorationType);


    /**
     * Calculates the areas of walls and floor based on the shape and dimensions of the classroom.
     * <p>
     * Pre-conditions: Dimensions must contain valid values for the dimensions of the classroom.
     * Shape must be an instance of {@code RectangularShape} or {@code CircularShape}.
     * </p>
     * @param shape      The shape of the classroom (RectangularShape or CircularShape).
     * @param dimensions An array representing the dimensions of the classroom (width, length, height).
     * @return A float array containing the calculated areas of walls and floor respectively,
     */
    default float[] calculateAreas(ClassShape shape, float[] dimensions) {

        float height;
        float wallArea;
        float floorArea;

        if (shape instanceof CircularShape) { //Circular classroom

            float radius = dimensions[0] / 2;
            height = dimensions[2];

            wallArea = (float) (2 * Math.PI * radius * height); //2*π*r * h
            floorArea = (float) (Math.PI * Math.pow(radius, 2)); //π*(r**2)

            return new float[]{wallArea, floorArea};

        } else if (shape instanceof RectangularShape) { //Rectangular classroom
            float width = dimensions[0];
            float length = dimensions[1];
            height = dimensions[2];

            wallArea = (int) Math.ceil(height * 2 * (width + length)); //2*(a+b) * h
            floorArea = (int) Math.ceil(width * length); //a*b

            return new float[]{wallArea, floorArea};
        } else {
            return null;
        }
    }
}
