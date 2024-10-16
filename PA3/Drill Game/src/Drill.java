import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class defines a drill machine in a Mining game.
 * Provides methods to manage machine.
 */
public class Drill {

    /**
     * Constructor to initialize drill machine object with pre-defined attributes such as fuel consumption of machine.
     *
     * @param pane                      Grid pane which the drill machine will be displayed on
     * @param fuel                      Fuel amount on tank at the beginning of the game
     * @param continuousFuelConsumption Amount of fuel consumed while idling
     * @param drillFuelConsumption      Extra amount of fuel consumed while trying to move
     * @throws IOException While pulling drill images if the path is not correct
     */
    public Drill(GridPane pane, float fuel, float continuousFuelConsumption, float drillFuelConsumption) throws IOException {
        this.valuables = Assets.getValuables();
        this.continuousFuelConsumption = continuousFuelConsumption;
        this.drillFuelConsumption = drillFuelConsumption;
        this.totalFuel = fuel;
        this.drillImages = Assets.getPNGFiles("assets/drill");
        setDrillImageView(new ImageView(drillImages.get("drill_01.png")));
        getDrillImageView().setFitWidth(50);
        getDrillImageView().setFitHeight(50);
        this.pane = pane;
    }

    private final HashMap<String, Image> drillImages;
    private ImageView drillImageView;
    private GridPane pane;
    private final Valuable[] valuables;
    private int totalRevenue = 0;
    private int totalWeight = 0;
    private float totalFuel = 25000;
    private final float continuousFuelConsumption; //Amount of fuel consumed while idling
    private final float drillFuelConsumption; //Extra amount of fuel consumed while trying to move
    private Text weight = new Text("HAUL:    0");
    private Text revenue = new Text("MONEY: 0");
    private Text fuel = new Text("FUEL:     ".concat(String.valueOf(this.getTotalFuel())));
    private boolean lavaContact = false;


    /**
     * This method moves the drill machine on scene according to keyboard input.
     * Arranges pane grids after movement
     *
     * @param keyEvent Keyboard event
     * @param weight   Text object which is displayed for weight
     * @param revenue  Text object which is displayed for revenue
     * @throws LavaContactError When machine drills lava block
     */
    public void moveDrill(KeyEvent keyEvent, Text weight, Text revenue) throws LavaContactError {

        KeyCode keyCode = keyEvent.getCode();

        MediaPlayer burningSoundPlayer = Assets.getSoundMedia("assets/extras/sound/fireloop.mp3");
        MediaPlayer explosionSoundPlayer = Assets.getSoundMedia("assets/extras/sound/explosion1.mp3");

        WritableImage explosionImage = Assets.cropImage(drillImages.get("GameOver.png"), 15, 130, 75, 75);
        WritableImage burningImage = Assets.cropImage(drillImages.get("GameOver.png"), 415, 130, 75, 75);

        int row = GridPane.getRowIndex(getDrillImageView()); //Row of machine before movement
        int column = GridPane.getColumnIndex(getDrillImageView()); //Column of machine before movement


        if (keyCode == KeyCode.UP || keyCode == KeyCode.W) {

            String upperBlock = getLabel(pane, row - 1, column); //Label of upper block
            totalFuel -= drillFuelConsumption;

            if (row != 0 && upperBlock.isEmpty()) { //Drill can move upward

                getDrillImageView().setImage(drillImages.get("drill_27.png")); //Drill image for upward direction
                GridPane.setRowIndex(getDrillImageView(), row - 1); //Moves to upward block
            }


        } else if (keyCode == KeyCode.DOWN || keyCode == KeyCode.S) {

            String downBlock = getLabel(pane, row + 1, column); //Label of block below
            totalFuel -= drillFuelConsumption;

            if (!downBlock.equals("obstacle")) { //Drill can move downward

                if (downBlock.equals("lava")) {
                    lavaContact = true;

                    deleteGrid(pane, row + 1, column); //Deletes block that is drilled

                    burningSoundPlayer.play();
                    getDrillImageView().setImage(burningImage);
                    GridPane.setRowIndex(getDrillImageView(), row + 1); //Moves to downward block

                    PauseTransition delay = new PauseTransition(Duration.seconds(1.5)); //Delay before explosion
                    delay.play();
                    delay.setOnFinished(e -> {
                        explosionSoundPlayer.play();
                        getDrillImageView().setImage(explosionImage);
                    });

                    throw new LavaContactError("Game Over!");


                } else {
                    collectValuable(downBlock);

                }
                deleteGrid(pane, row + 1, column); //Deletes block that is drilled

                getDrillImageView().setImage(drillImages.get("drill_44.png")); //Drill image for downward direction
                GridPane.setRowIndex(getDrillImageView(), row + 1); //Moves to downward block
            }


        } else if (keyCode == KeyCode.RIGHT || keyCode == KeyCode.D) {

            String rightBlock = getLabel(pane, row, column + 1); //Label of block on the right
            totalFuel -= drillFuelConsumption;

            if (!rightBlock.equals("obstacle") && column != 23) {

                if (rightBlock.equals("lava")) {
                    lavaContact = true;

                    deleteGrid(pane, row, column + 1); //Deletes block that is drilled

                    burningSoundPlayer.play();
                    getDrillImageView().setImage(burningImage);
                    GridPane.setColumnIndex(getDrillImageView(), column + 1); //Moves to right block

                    PauseTransition delay = new PauseTransition(Duration.seconds(1.5)); //Delay before explosion
                    delay.play();
                    delay.setOnFinished(e -> {
                        explosionSoundPlayer.play();
                        getDrillImageView().setImage(explosionImage);
                    });

                    throw new LavaContactError("Game Over!");


                } else {
                    collectValuable(rightBlock);

                }
                deleteGrid(pane, row, column + 1); //Deletes block that is drilled

                getDrillImageView().setImage(drillImages.get("drill_60.png")); //Drill image for right direction
                GridPane.setColumnIndex(getDrillImageView(), column + 1); //Moves to block on the right
            }

        } else if (keyCode == KeyCode.LEFT || keyCode == KeyCode.A) {

            String leftBlock = getLabel(pane, row, column - 1); //Label of block on the right
            totalFuel -= drillFuelConsumption;

            if (!leftBlock.equals("obstacle") && column != 0) {

                if (leftBlock.equals("lava")) {
                    lavaContact = true;

                    deleteGrid(pane, row, column - 1); //Deletes block that is drilled

                    burningSoundPlayer.play();
                    getDrillImageView().setImage(burningImage);
                    GridPane.setColumnIndex(getDrillImageView(), column - 1); //Moves to left block

                    PauseTransition delay = new PauseTransition(Duration.seconds(1.5)); //Delay before explosion
                    delay.play();
                    delay.setOnFinished(e -> {
                        explosionSoundPlayer.play();
                        getDrillImageView().setImage(explosionImage);
                    });

                    throw new LavaContactError("Game Over!");


                } else {
                    collectValuable(leftBlock);

                }
                deleteGrid(pane, row, column - 1); //Deletes block that is drilled

                getDrillImageView().setImage(drillImages.get("drill_01.png")); //Drill image for left direction
                GridPane.setColumnIndex(getDrillImageView(), column - 1); //Moves to left block
            }
        }
    }


    /**
     * Getter for image view of drill.
     *
     * @return Image view of drill
     */
    public ImageView getDrillImageView() {
        return drillImageView;
    }

    /**
     * Setter for image view of drill.
     */
    public void setDrillImageView(ImageView drillImageView) {
        this.drillImageView = drillImageView;
    }

    /**
     * Getter for label on given grid indexes.
     *
     * @return Label as string
     */
    public String getLabel(GridPane pane, int rowIndex, int columnIndex) {

        for (Node child : pane.getChildren()) { // Iterates through each child node

            // Check if the child node is in the specified grid cell
            if (GridPane.getRowIndex(child) == rowIndex && GridPane.getColumnIndex(child) == columnIndex) {
                // Check if the child node is an instance of Label
                if (child instanceof Label) {
                    Label label = (Label) child; // Cast the node to Label

                    return label.getText();
                }
            }
        }
        return "";
    }

    /**
     * This method deletes all nodes on given grid indexes.
     *
     * @param pane   Grid pane which has nodes on
     * @param row    Row of grid that will be deleted
     * @param column Column of grid that will be deleted
     */
    private void deleteGrid(GridPane pane, int row, int column) {

        ObservableList<Node> children = pane.getChildren();
        List<Node> nodesWillBeRemoved = new ArrayList<>();

        for (Node child : children) { // Iterates through the children of the GridPane

            // Check if the node is an ImageView and has the specified row and column
            if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == column) {
                nodesWillBeRemoved.add(child);
            }
        }
        children.removeAll(nodesWillBeRemoved);
    }

    /**
     * This method returns true if there is fuel left on tank, else returns false.
     *
     * @return True if there is fuel left on tank, else false
     */
    public boolean isThereFuelLeft() {
        return totalFuel > 0;
    }

    /**
     * Getter for total revenue of drill.
     *
     * @return Total revenue
     */
    public int getTotalRevenue() {
        return totalRevenue;
    }

    /**
     * Getter for total weight of drill.
     *
     * @return Total weight
     */
    public int getTotalWeight() {
        return totalWeight;
    }

    /**
     * Getter for total fuel of drill.
     *
     * @return Total fuel
     */
    public float getTotalFuel() {
        return totalFuel;
    }

    /**
     * Setter for total revenue of drill.
     */
    public void setTotalFuel(float totalFuel) {
        this.totalFuel = totalFuel;
    }

    /**
     * Getter for continuous fuel consumption of drill.
     *
     * @return Continuous fuel consumption
     */
    public float getContinuousFuelConsumption() {
        return continuousFuelConsumption;
    }

    /**
     * This method runs gravitation for drill machine when the block below is empty.
     */
    public void runGravitation() {

        int row = GridPane.getRowIndex(getDrillImageView()); //Row of machine before movement
        int column = GridPane.getColumnIndex(getDrillImageView()); //Column of machine before movement
        String downBlock = getLabel(pane, row + 1, column);

        if (downBlock.isEmpty()) {
            GridPane.setRowIndex(getDrillImageView(), row + 1); //Machine falls
        }

    }

    /**
     * This method displays machine attributes on top-left of the scene.
     *
     * @param pane Grid pane that will be displayed on.
     */
    public void displayMachineAttributes(GridPane pane) {
        //Font and size settings
        revenue.setFont(new Font("Comic Sans MS", 20));
        weight.setFont(new Font("Comic Sans MS", 20));
        fuel.setFont(new Font("Comic Sans MS", 20));

        //Alignment settings
        GridPane.setValignment(fuel, VPos.TOP);
        GridPane.setValignment(weight, VPos.CENTER);
        GridPane.setValignment(revenue, VPos.BOTTOM);

        pane.add(revenue, 0, 0, 5, 2);
        pane.add(weight, 0, 0, 5, 2);
        pane.add(fuel, 0, 0, 5, 2);
    }


    /**
     * This method starts drill machine.
     * Thus, gravitation, keyboard inputs, fuel consumption etc. starts to work.
     *
     * @param UIManager UI manager reference
     * @param scene     Scene of game
     * @param pane      Grid pane that drill machine is displayed on
     */
    public void runDrillEngine(UIManager UIManager, Scene scene, GridPane pane) {

        final float continuousFuelConsumption = this.getContinuousFuelConsumption();

        // Creates a Timeline to manage the drill's actions
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

            // Checks if there is fuel left and if there are valuable resources to collect
            if (this.isThereFuelLeft() && UIManager.countValuableVariety(pane, this) != 0) {
                fuel.setText("FUEL:     ".concat(String.valueOf(this.getTotalFuel())));
                this.setTotalFuel(this.getTotalFuel() - continuousFuelConsumption);

            } else {
                UIManager.displayFuelGameOver(pane, this);
            }

        }), new KeyFrame(Duration.seconds(1.5), event -> {
            // Performs gravitational movement every 1.5 seconds
            this.runGravitation();
        }));


        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        //Calls lambda function when a key is pressed
        scene.setOnKeyPressed(keyEvent -> {
            try {
                if (!lavaContact) { //Game did not end with lava
                    this.moveDrill(keyEvent, weight, revenue);
                }
            } catch (LavaContactError error) {

                PauseTransition delay = new PauseTransition(Duration.seconds(3.5));
                delay.play();
                delay.setOnFinished(e -> {
                    //Game is finished with lava (RED)
                    timeline.stop();
                    pane.getChildren().removeAll(pane.getChildren());
                    pane.setBackground(new Background(new BackgroundFill(Color.INDIANRED, null, null)));
                    Text gameOverMessage = new Text(error.getMessage());
                    gameOverMessage.setFont(new Font("Comic Sans MS", 150));
                    gameOverMessage.setTextAlignment(TextAlignment.CENTER);
                    GridPane.setHalignment(gameOverMessage, HPos.CENTER);
                    pane.add(gameOverMessage, 0, 7, 24, 1);
                });
            }
        });
    }

    /**
     * This method collects given valuable and changes machine attributes relatively.
     *
     * @param valuableName Name of valuable will be collected
     */
    private void collectValuable(String valuableName) {
        for (Valuable valuable : valuables) { //Iterates valuables array
            if (valuable.getName().equals(valuableName)) { //Valuable found in array
                totalRevenue += valuable.getWorth();
                totalWeight += valuable.getWeight();

                //Updates texts which are displayed.
                weight.setText("HAUL:    ".concat(String.valueOf(this.getTotalWeight())));
                revenue.setText("MONEY: ".concat(String.valueOf(this.getTotalRevenue())));
                break;
            }
        }
    }

}
