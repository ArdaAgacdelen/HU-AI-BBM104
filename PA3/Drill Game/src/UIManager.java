import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.*;

/**
 * This class supplies methods for UI operations of a Mining Game
 */
public class UIManager {

    /**
     * This method creates grids in the specified GridPane with the given number of rows and columns,
     * each cell having the provided width and height.
     *
     * @param pane            The GridPane to which the grids are added
     * @param numberOfRows    The number of rows in the grid
     * @param numberOfColumns The number of columns in the grid
     * @param gridWidth       The width of each grid cell
     * @param gridHeight      The height of each grid cell
     */
    public void createGrids(GridPane pane, int numberOfRows, int numberOfColumns, double gridWidth, double gridHeight) {
        for (int i = 0; i < numberOfColumns; i++) {
            //Sets grid with constant edges
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMinWidth(gridWidth);
            columnConstraints.setMaxWidth(gridWidth);
            columnConstraints.setPrefWidth(gridWidth);
            pane.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < numberOfRows; i++) {
            //Sets grid with constant edges
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(gridHeight);
            rowConstraints.setMaxHeight(gridHeight);
            rowConstraints.setPrefHeight(gridHeight);
            pane.getRowConstraints().add(rowConstraints);
        }

        //Adds sky
        pane.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, null, new Insets(0, 0, 650, 0))));
    }


    /**
     * This method initializes the grids in the specified GridPane.
     * Adds Mining game images to create a random background for Mining game.
     *
     * @param pane            The GridPane to initialize
     * @param drill           The Drill instance to place on the grid
     * @param numberOfRows    The number of rows in the grid
     * @param numberOfColumns The number of columns in the grid
     * @throws IOException pre-condition: Path of assets must be true
     */
    public void initializeGrids(GridPane pane, Drill drill, int numberOfRows, int numberOfColumns) throws IOException {

        //Pulls underground images
        HashMap<String, Image> gameImages = Assets.getPNGFiles("assets/underground");

        Image[] topImages = Assets.getTopImages(gameImages);
        Image[] obstacleImages = Assets.getObstacleImages(gameImages);
        Image[] lavaImages = Assets.getLavaImages(gameImages);
        Image[] soilImages = Assets.getSoilImages(gameImages);
        HashMap<String, Image> valuableImages = Assets.getValuableImages(gameImages);


        //Adds top and obstacle border images horizontally
        for (int i = 0; i < numberOfColumns; i++) {
            //Selects random image for each obstacle will be placed
            ImageView randomObstacle = new ImageView(randomImage(obstacleImages));
            resizeImageview(randomObstacle);
            pane.add(randomObstacle, i, numberOfRows - 1);
            //Adds obstacle label
            addLabelToPane(pane, "obstacle", i, numberOfRows - 1);

            //Selects random image for each top will be placed
            ImageView randomTop = new ImageView(randomImage(topImages));
            resizeImageview(randomTop);
            pane.add(randomTop, i, 2);
            //Adds top label
            addLabelToPane(pane, "soil", i, 2);
        }

        //Adds obstacle border images vertically
        for (int i = 3; i < numberOfRows - 1; i++) {
            //Selects random image for each obstacle will be placed on right border
            ImageView randomObstacle = new ImageView(randomImage(obstacleImages));
            resizeImageview(randomObstacle);
            pane.add(randomObstacle, 0, i);
            //Adds obstacle label
            addLabelToPane(pane, "obstacle", 0, i);

            //Selects random image for each obstacle will be placed on left border
            ImageView randomObstacle1 = new ImageView(randomImage(obstacleImages));
            resizeImageview(randomObstacle1);
            pane.add(randomObstacle1, numberOfColumns - 1, i);
            //Adds obstacle label
            addLabelToPane(pane, "obstacle", numberOfColumns - 1, i);
        }

        //A 2D String array that includes label names for representing grid
        String[][] imageTypeList = new String[numberOfRows - 4][numberOfColumns - 2];
        Random random = new Random();
        //Random number of obstacles in range [3-8] for underground pool
        int obstacleNumber = random.nextInt(6) + 3;
        //Random number of valuables in range [10-19] for underground pool
        int valuableNumber = random.nextInt(10) + 5;
        //Random number of lavas in range [8-14] for underground pool
        int lavaNumber = random.nextInt(7) + 8;

        boolean isUndergroundValid = false;
        //Tries creating valid Underground
        while (!isUndergroundValid) {
            //Randomly places obstacles
            for (int i = 0; i < obstacleNumber; i++) {
                boolean isPlaced = false;
                while (!isPlaced) {
                    int randomColumnIndex = random.nextInt(numberOfColumns - 2); //Random column index
                    int randomRowIndex = random.nextInt(numberOfRows - 4); //Random row index
                    if (imageTypeList[randomRowIndex][randomColumnIndex] == null) { //If random grid empty
                        imageTypeList[randomRowIndex][randomColumnIndex] = "obstacle"; //Grid found for obstacle
                        isPlaced = true; //While loop can be ended
                    }
                }
            }
            //Randomly places lavas
            for (int i = 0; i < lavaNumber; i++) {
                boolean isPlaced = false;
                while (!isPlaced) {
                    int randomColumnIndex = random.nextInt(numberOfColumns - 2); //Random column index
                    int randomRowIndex = random.nextInt(numberOfRows - 4); //Random row index
                    if (imageTypeList[randomRowIndex][randomColumnIndex] == null) { //If random grid empty
                        imageTypeList[randomRowIndex][randomColumnIndex] = "lava"; //Grid found for lava
                        isPlaced = true;//While loop can be ended
                    }
                }
            }
            //Randomly places valuables
            for (int i = 0; i < valuableNumber; i++) {
                boolean isPlaced = false;
                while (!isPlaced) {
                    int randomColumnIndex = random.nextInt(numberOfColumns - 2); //Random column index
                    int randomRowIndex = random.nextInt(numberOfRows - 4); //Random row index
                    if (imageTypeList[randomRowIndex][randomColumnIndex] == null) { //If random grid empty
                        imageTypeList[randomRowIndex][randomColumnIndex] = "valuable"; //Grid found for valuable
                        isPlaced = true;
                    }
                }
            }


            if (undergroundCheck(imageTypeList)) {
                isUndergroundValid = true; //Underground is valid while loop can be ended
            }
        }


        //Schema of underground is ready. Image views can be placed to the grid pane
        for (int rowIndex = 0; rowIndex < numberOfRows - 4; rowIndex++) { //For underground pool rows
            for (int columnIndex = 0; columnIndex < numberOfColumns - 2; columnIndex++) { //For underground pool columns

                if (imageTypeList[rowIndex][columnIndex] != null) { //null represents soil

                    switch (imageTypeList[rowIndex][columnIndex]) {

                        case "valuable":
                            //Selects random image for each valuable will be placed
                            ArrayList randomValuable = randomImage(valuableImages);

                            String randomValuableName = ((String) randomValuable.get(0));

                            ImageView randomValuableImageView = new ImageView((Image) randomValuable.get(1));
                            resizeImageview(randomValuableImageView);

                            pane.add(randomValuableImageView, columnIndex + 1, rowIndex + 3);
                            //Adds valuable label with its type name(such as ruby, gold)
                            addLabelToPane(pane, randomValuableName, columnIndex + 1, rowIndex + 3);
                            break;

                        case "obstacle":
                            //Selects random image for each obstacle will be placed
                            ImageView randomObstacle = new ImageView(randomImage(obstacleImages));
                            resizeImageview(randomObstacle);
                            pane.add(randomObstacle, columnIndex + 1, rowIndex + 3);
                            //Adds obstacle label
                            addLabelToPane(pane, "obstacle", columnIndex + 1, rowIndex + 3);
                            break;

                        case "lava":
                            //Selects random image for each lava will be placed
                            ImageView randomLava = new ImageView(randomImage(lavaImages));
                            resizeImageview(randomLava);
                            pane.add(randomLava, columnIndex + 1, rowIndex + 3);
                            //Adds lava label
                            addLabelToPane(pane, "lava", columnIndex + 1, rowIndex + 3);
                            break;

                    }

                } else {
                    //Selects random image for each soil will be placed
                    ImageView randomSoil = new ImageView(randomImage(soilImages));
                    resizeImageview(randomSoil);
                    pane.add(randomSoil, columnIndex + 1, rowIndex + 3);
                    //Adds soil label
                    addLabelToPane(pane, "soil", columnIndex + 1, rowIndex + 3);
                }
            }
        }

        if (countValuableVariety(pane, drill) < 3) { //If there isn't minimum 3 different type valuable
            pane.getChildren().removeAll(pane.getChildren()); //Delete all content
            initializeGrids(pane, drill, numberOfRows, numberOfColumns); //Re-try
        } else {
            pane.add(drill.getDrillImageView(), 0, 1); //Else add drill image view to the pane
        }
    }


    /**
     * This method displays a game over message with the total money drill earned.(GREEN)
     *
     * @param pane  The GridPane to which the game over message is added
     * @param drill The Drill object containing game data
     */
    public void displayFuelGameOver(GridPane pane, Drill drill) {
        pane.getChildren().removeAll(pane.getChildren());
        pane.setBackground(new Background(new BackgroundFill(Color.FORESTGREEN, null, null)));
        Text gameOverMessage = new Text(String.format("Game Over!\nCollected Money: %d", drill.getTotalRevenue()));
        gameOverMessage.setFont(new Font("Comic Sans MS", 70));
        gameOverMessage.setTextAlignment(TextAlignment.CENTER);
        GridPane.setHalignment(gameOverMessage, HPos.CENTER);
        pane.add(gameOverMessage, 0, 5, 24, 4);
    }


    /**
     * Randomly selects and returns an Image from the provided array of Images.
     *
     * @param randomImages An array of Images from which a random Image is selected init
     * @return A randomly selected Image from the array
     */
    private static Image randomImage(Image[] randomImages) {
        Random random = new Random();
        return randomImages[random.nextInt(randomImages.length)];
    }

    /**
     * Randomly selects and returns an Image from the provided map of Images.
     * First element of arraylist is key(name) of image.
     * Second element of arraylist is value(Image itself).
     *
     * @param randomImages A HashMap containing String keys and Image values
     * @return An ArrayList containing the key and corresponding Image selected randomly from the HashMap
     */
    private static ArrayList randomImage(HashMap<String, Image> randomImages) {
        ArrayList returnList = new ArrayList();
        Random random = new Random();
        ArrayList<String> keys = new ArrayList(randomImages.keySet());
        String randomKey = keys.get(random.nextInt(keys.size())); //Selects a random key
        //Keys are filenames such valuable_ruby.png so it is needed to be cropped.
        returnList.add(randomKey.substring(9, randomKey.length() - 4));
        returnList.add(randomImages.get(randomKey));
        return returnList;
    }


    /**
     * This method checks if the placement of underground elements is valid.
     *
     * @param imageTypeList A 2D string array representing the types of images in the underground grid
     * @return True if the placement of underground elements is valid, false otherwise
     */
    private boolean undergroundCheck(String[][] imageTypeList) {
        int valuableCount = 0;
        int lavaCount = 0;
        int soilCount = 0;
        int obstacleCount = 0;

        //Counts all image types
        for (String[] rows : imageTypeList) {
            for (String imageType : rows) {
                if (imageType == null) { //Soil
                    soilCount++;
                } else if (imageType.equals("valuable")) {
                    valuableCount++;
                } else if (imageType.equals("lava")) {
                    lavaCount++;
                } else if (imageType.equals("obstacle")) {
                    obstacleCount++;
                }

            }
        }
        //If there is more than 5 neighbor (lavas and or obstacles) or soil count is under of other's sum return false
        if (edgeCheck(imageTypeList, 0, 0) > 5 || lavaCount + valuableCount + obstacleCount >= soilCount) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * Recursively checks the neighboring cells to determine how many lava-obstacle are neighbor.
     *
     * @param imageTypeList A 2D array representing the types of images in the underground grid.
     * @param rowIndex      The index of the current row being checked.
     * @param columnIndex   The index of the current column being checked.
     * @return The number of neighboring cells containing lava or obstacles.
     */
    private int edgeCheck(String[][] imageTypeList, int rowIndex, int columnIndex) {
        int neighborObstacleAndLavaNumber = 0;
        try {
            //If neighbor is lava or obstacle
            if (imageTypeList[rowIndex][columnIndex].equals("lava") || imageTypeList[rowIndex][columnIndex].equals("obstacle")) {
                neighborObstacleAndLavaNumber++;
                //Checks right neighbor recursively
                neighborObstacleAndLavaNumber += edgeCheck(imageTypeList, rowIndex, columnIndex + 1);
                //Checks below neighbor recursively
                neighborObstacleAndLavaNumber += edgeCheck(imageTypeList, rowIndex + 1, columnIndex);
            }
        } catch (Exception e) { //Array index out of bounds
            return neighborObstacleAndLavaNumber;
        }
        return neighborObstacleAndLavaNumber;
    }


    /**
     * Resizes the provided ImageView to a fixed block width and height.
     *
     * @param imageView The ImageView will be resized
     */
    public static void resizeImageview(ImageView imageView) {
        imageView.setFitHeight(50);
        imageView.setFitHeight(50);
    }


    /**
     * Adds a label to the specified grid in the GridPane with the provided name.
     *
     * @param pane        The GridPane to which the label is added
     * @param name        The name of the label
     * @param columnIndex The column index where the label is added
     * @param rowIndex    The row index where the label is added
     */
    private void addLabelToPane(GridPane pane, String name, int columnIndex, int rowIndex) {
        Label label = new Label(name);
        label.setVisible(false);
        pane.add(label, columnIndex, rowIndex);
    }


    /**
     * Counts the number of valuable types collected on the grid.
     *
     * @param pane  The GridPane containing the game Images
     * @param drill The Drill object containing game fields
     * @return The number of valuable types in grid
     */
    public int countValuableVariety(GridPane pane, Drill drill) {
        HashSet<String> valuableTypes = new HashSet<>(); //To avoid duplicates set was used

        //Adds all labels in grid pane.
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 15; j++) {
                valuableTypes.add(drill.getLabel(pane, j, i));
            }
        }
        //Removes lava, obstacle, "", soil and top labels
        valuableTypes.remove("lava");
        valuableTypes.remove("obstacle");
        valuableTypes.remove("");
        valuableTypes.remove("soil");
        valuableTypes.remove("top");

        return valuableTypes.size();
    }
}