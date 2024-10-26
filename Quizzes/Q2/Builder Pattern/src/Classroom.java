/**
 * This classroom class defines a real world classroom object with some properties.
 * It contains information about the class name, dimensions, shape, and decoration types for walls and floors.
 */
public class Classroom {

    /**
     * Creates a new classroom object with the parameters which are all required for all classes.
     *
     * @param className  The name of the classroom.
     * @param dimensions A float array representing the dimensions of the classroom.(width, length, height)
     * @param classShape The shape of the classroom ("Circle" or "Rectangle").
     */
    Classroom(String className, float[] dimensions, String classShape) {
        this.className = className;
        this.dimensions = dimensions;

        if (classShape.equals("Circle")) { //Class shape is circular.
            this.classShape = new CircularShape();
        } else if (classShape.equals("Rectangle")) { //Class shape is rectangular.
            this.classShape = new RectangularShape();
        }
    }

    private String className;
    private ClassShape classShape;
    private float[] dimensions; //width, length, height
    private DecorationType wallDecorationType;
    private DecorationType floorDecorationType;


    /**
     * Returns the name of the classroom.
     *
     * @return The name of the classroom.
     */
    public String getClassName() {

        return className;
    }

    /**
     * Returns the shape of the classroom.
     *
     * @return The shape of the classroom.
     */
    public ClassShape getClassShape() {
        return classShape;
    }

    /**
     * Returns the width, length, height of the classroom.
     *
     * @return A float array representing the dimensions of the classroom.
     */
    public float[] getDimensions() {
        return dimensions;
    }

    /**
     * Returns the type of decoration for walls.
     *
     * @return The type of decoration for walls.
     */
    public DecorationType getWallDecorationType() {
        return wallDecorationType;
    }

    /**
     * Sets the type of decoration for walls.
     *
     * @param wallDecorationType The type of decoration for walls.
     */
    public void setWallDecorationType(DecorationType wallDecorationType) {
        this.wallDecorationType = wallDecorationType;
    }

    /**
     * Returns the type of decoration for floors.
     *
     * @return The type of decoration for floors.
     */
    public DecorationType getFloorDecorationType() {
        return floorDecorationType;
    }

    /**
     * Sets the type of decoration for floors.
     *
     * @param floorDecorationType The type of decoration for floors.
     */
    public void setFloorDecorationType(DecorationType floorDecorationType) {
        this.floorDecorationType = floorDecorationType;
    }
}
