import java.util.ArrayList;

/**
 * The Main class contains the main method to run the program. It reads input files,
 * constructs classrooms and decorations, builds walls and floors for classrooms based on
 * specified decorations, calculates the total expenditure, and writes the result to an output file.
 * <p>
 * Usage:
 * java Main [items.txt] [decoration.txt] [outputFile.txt]
 * </p>
 * <p>
 * Pre-conditions: The program assumes that the input files are formatted correctly and that the required
 * command-line arguments are provided.
 * </p>
 */
public class Main {

    /**
     * The main method of the program. It reads input files, constructs classrooms and decorations,
     * builds walls and floors for classrooms based on specified decorations, calculates the total
     * expenditure, and writes the result to an output file.
     *
     * @param args Command-line arguments. Expects three arguments: [items.txt] [decoration.txt] [outputFile.txt]
     */
    public static void main(String[] args) {

        //Creates an empty file or deletes content if file already exists.
        FileIO.writeToFile(args[2], "", false, false);

        //Info about classes wanted to be constructed and decorations types.
        String[] itemLines = FileIO.readFile(args[0], true, true);
        //Info about which decoration type is going to be used for classes.
        String[] decorationLines = FileIO.readFile(args[1], true, true); //

        ArrayList<Classroom> classes = new ArrayList<Classroom>(); // List of classes wanted to be constructed.
        ArrayList<DecorationType> decorationTypes = new ArrayList<DecorationType>(); //Decoration types that can be used.

        ClassBuilder classBuilder = new ClassBuilder(); //Instantiation of class builder.
        OutputBuilder outputBuilder = new OutputBuilder(); ////Instantiation of output builder to write info to output.txt.


        for (String item : itemLines) {
            String[] contentItem = item.split("\t");
            String itemType = contentItem[0];

            if (itemType.equals("CLASSROOM")) {
                float width = Float.parseFloat(contentItem[3]);
                float length = Float.parseFloat(contentItem[4]);
                float height = Float.parseFloat(contentItem[5]);
                String classShape = contentItem[2];

                classes.add(new Classroom(contentItem[1], new float[]{width, length, height}, classShape));
            }
            else if (itemType.equals("DECORATION")) {
                String decorationName = contentItem[1];
                String decorationType = contentItem[2];
                float pricePerUnit = Float.parseFloat(contentItem[3]);

                if (decorationType.equals("Tile")) {
                    float areaOfTile = Float.parseFloat(contentItem[4]);
                    decorationTypes.add(new Tile(decorationName, pricePerUnit, areaOfTile));
                }
                else if (decorationType.equals("Wallpaper")) {
                    decorationTypes.add(new Wallpaper(decorationName, pricePerUnit));
                }
                else if (decorationType.equals("Paint")) {
                    decorationTypes.add(new Paint(decorationName, pricePerUnit));

                }
            }
        }


        long totalExpenditure = 0;

        for (Classroom classroom: classes){

            for (String decorationLine : decorationLines) {

                String[] decorationLineContent = decorationLine.split("\t");
                String wantedClassName = decorationLineContent[0];

                if (classroom.getClassName().equals(wantedClassName)){ //Class found in array-list.

                    String wantedWallDecorationName = decorationLineContent[1];
                    String wantedFloorDecorationName = decorationLineContent[2];

                    classBuilder.setClassUnderConstruction(classroom); //Construction process started.
                    outputBuilder.setClassUnderConstruction(classroom); //Output process started.


                    for (DecorationType decorationType : decorationTypes) {
                        if (decorationType.getName().equals(wantedWallDecorationName)) { //Wanted decoration type for walls found.
                            classBuilder.buildWalls(decorationType); //Walls are built.
                            outputBuilder.buildWalls(args[2]); //Information about wall construction was written to output.
                            break;
                        }
                    }

                    for (DecorationType decorationType : decorationTypes) {
                        if (decorationType.getName().equals(wantedFloorDecorationName)) { //Wanted decoration type for floor found.
                            classBuilder.buildFloor(decorationType); //Floor are built.
                            outputBuilder.buildFloor(args[2]); //Information about floor construction was written to output.
                            //Total cost spent is added to total expenditure for all classes.
                            totalExpenditure += outputBuilder.calculateCost();
                        }
                    }
                    break;
                }
            }
        }

        //Last, total expenditure for all classes is written to the output.txt.
        outputBuilder.writeTotalExpenditure(args[2], totalExpenditure);
    }
}
