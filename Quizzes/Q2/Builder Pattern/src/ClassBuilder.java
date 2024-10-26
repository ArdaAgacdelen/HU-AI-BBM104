/**
 * This class implements the Builder interface to construct a classroom.
 * It provides methods to build walls and floor of the classroom with specified decoration types.
 */
public class ClassBuilder implements Builder {
    private Classroom classUnderConstruction; //The classroom which is being constructed.


    /**
     * Sets the type of decoration for walls.
     *
     * @param wallDecorationType The type of decoration for walls.
     */
    @Override
    public void buildWalls(DecorationType wallDecorationType) {
        classUnderConstruction.setWallDecorationType(wallDecorationType);
    }


    /**
     * Sets the type of decoration for floors.
     *
     * @param floorDecorationType The type of decoration for floors.
     */
    @Override
    public void buildFloor(DecorationType floorDecorationType) {
        classUnderConstruction.setFloorDecorationType(floorDecorationType);
    }

    @Override
    public void buildFloor(String outputFile) {
        buildFloor(getClassUnderConstruction().getFloorDecorationType());
    }

    @Override
    public void buildWalls(String outputFile) {
        buildWalls(getClassUnderConstruction().getWallDecorationType());
    }


    /**
     * Returns the classroom object under construction.
     *
     * @return The classroom under construction.
     */
    public Classroom getClassUnderConstruction() {
        return classUnderConstruction;
    }


    /**
     * Sets the classroom under construction.
     *
     * @param classUnderConstruction The classroom to set under construction.
     */
    public void setClassUnderConstruction(Classroom classUnderConstruction) {
        this.classUnderConstruction = classUnderConstruction;
    }
}
