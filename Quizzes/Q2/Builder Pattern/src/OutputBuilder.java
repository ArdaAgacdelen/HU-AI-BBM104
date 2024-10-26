/**
 * This class implements the Builder interface to write construction details to an output file.
 */
public class OutputBuilder implements Builder {
    private Classroom classUnderConstruction; //The classroom which was constructed.


    @Override
    public void buildWalls(String outputFile) {

        float[] areas = calculateAreas(classUnderConstruction.getClassShape(), classUnderConstruction.getDimensions());
        float areaOfWalls = areas[0];

        if (classUnderConstruction.getWallDecorationType() instanceof Tile) { //Tile is used for walls.
            FileIO.writeToFile(outputFile, String.format("Classroom %s used %d Tiles for walls and ",
                    classUnderConstruction.getClassName(), calculateHowManyTileUsed(areaOfWalls, ((Tile) classUnderConstruction.getWallDecorationType()).getAreaOfTile())),
                    true, false);
        }

        else if (classUnderConstruction.getWallDecorationType() instanceof Wallpaper) { //Wallpaper is used for walls.
            FileIO.writeToFile(outputFile, String.format("Classroom %s used %.0fm2 of Wallpaper for walls and ",
                    classUnderConstruction.getClassName(), Math.ceil(areaOfWalls)),
                    true, false);

        }

        else if (classUnderConstruction.getWallDecorationType() instanceof Paint) { //Paint is used for walls.
            FileIO.writeToFile(outputFile, String.format("Classroom %s used %.0fm2 of Paint for walls and ", classUnderConstruction.getClassName(), Math.ceil(areaOfWalls)),
                    true, false);

        }

    }

    @Override
    public void buildFloor(String outputFile) {
        float[] areas = calculateAreas(classUnderConstruction.getClassShape(), classUnderConstruction.getDimensions());
        float areaOfFloor = areas[1];

        if (classUnderConstruction.getFloorDecorationType() instanceof Tile) { //Tile is used for floor.
            FileIO.writeToFile(outputFile, String.format("used %d Tiles for flooring, these costed %dTL.",
                    calculateHowManyTileUsed(areaOfFloor, ((Tile) classUnderConstruction.getFloorDecorationType()).getAreaOfTile()),
                    calculateCost()), true, true);
        }
    }


    /**
     * Calculates the total cost of decorations used in the classroom.
     *
     * @return The total cost of decorations.
     */
    public int calculateCost() {
        float[] areas = calculateAreas(classUnderConstruction.getClassShape(), classUnderConstruction.getDimensions());
        float areaOfWalls = areas[0];
        float areaOfFloor = areas[1];
        DecorationType wallDecoration = classUnderConstruction.getWallDecorationType(); //Decoration type used for walls.
        DecorationType floorDecoration = classUnderConstruction.getFloorDecorationType(); //Decoration type used for floor.


        if (wallDecoration instanceof Tile || floorDecoration instanceof Tile) { //Tile is used.

            if (wallDecoration instanceof Tile && floorDecoration instanceof Tile) { //Tile is used for both floor and walls.

                //((Tile number used for walls) * price per tile) + ((Tile number used for floor) * price per tile)
                return (int) Math.ceil(calculateHowManyTileUsed(areaOfWalls, ((Tile) wallDecoration).getAreaOfTile()) * wallDecoration.getCostPerUnit()
                        + calculateHowManyTileUsed(areaOfFloor, ((Tile) floorDecoration).getAreaOfTile()) * floorDecoration.getCostPerUnit());
            }

            else if (wallDecoration instanceof Tile) { //Tile is just used for walls.

                //((Tile number used for walls) * price per tile) + ((Area of floor) * price per meter square)
                return (int) Math.ceil(calculateHowManyTileUsed(areaOfWalls, ((Tile) wallDecoration).getAreaOfTile()) * wallDecoration.getCostPerUnit()
                        + areaOfFloor * floorDecoration.getCostPerUnit());
            }

            else { //Tile is just used for floor.

                //((Area of walls) * price per meter square) + ((Tile number used for floor) * price per tile)
                return (int) Math.ceil(areaOfWalls * wallDecoration.getCostPerUnit() +
                        calculateHowManyTileUsed(areaOfFloor, ((Tile) floorDecoration).getAreaOfTile()) * floorDecoration.getCostPerUnit());
            }
        }

        else { //Tile is not used.

            //((Area of walls) * price per meter square) + ((Area of walls) * price per meter square)
            return (int) Math.ceil(areaOfWalls * wallDecoration.getCostPerUnit() + areaOfFloor * floorDecoration.getCostPerUnit());
        }
    }


    /**
     * Calculates the number of tiles required based on the area will be covered and surface area of each tile.
     *
     * @param area     The total area to cover with tiles.
     * @param tileArea The area of a single tile.
     * @return The number of tiles required.
     */
    private int calculateHowManyTileUsed(float area, double tileArea) {
        return (int) Math.ceil(area / tileArea);
    }

    @Override
    public void buildFloor(DecorationType floorDecorationType) {
    }

    @Override
    public void buildWalls(DecorationType wallDecorationType) {
    }


    /**
     * Sets the classroom under construction.
     *
     * @param classUnderConstruction The classroom to set under construction.
     */
    public void setClassUnderConstruction(Classroom classUnderConstruction) {
        this.classUnderConstruction = classUnderConstruction;
    }

    /**
     * Writes to the given file total expenditure for all classes' constructions information.
     *
     * @param outputFile File that is going to be written.
     * @param totalExpenditure Total expenditure for all classes' constructions.
     */
    public void writeTotalExpenditure(String outputFile, long totalExpenditure){
        FileIO.writeToFile(outputFile, String.format("Total price is: %dTL.", totalExpenditure), true, false);
    }
}
