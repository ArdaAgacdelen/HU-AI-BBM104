import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class is the main class of JavaFX Mining Game.
 * It extends Application and overrides the start method to set up the application's UI.
 */
public class Main extends Application {

    /**
     * Main method of application.
     * It launches application by calling the method.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Application.launch(args);
    }


    /**
     * This method initializes the JavaFX application stage.
     * It sets up the application's scene and adds necessary UI components to the scene via some method calls.
     *
     * @param stage The stage of this application.
     * @throws Exception If there is an issue during initialization.
     */
    @Override
    public void start(Stage stage) throws Exception {

        //Creates a grid pane as a root
        GridPane root = new GridPane();
        //Creates scene with Saddle brown color which represents empty are
        Scene scene = new Scene(root, 1200, 750, Color.SADDLEBROWN);
        stage.setTitle("MINING GAME");
        //Resizes stage according to scene
        stage.sizeToScene();
        //Adds scene to the stage
        stage.setScene(scene);

        //Initializes a drill machine with custom properties
        Drill drill = new Drill(root, 25000, 0.5f, 100);

        //Creates UI manager instance object to manage UI of application
        UIManager UIManager = new UIManager();
        //Divides scene by 15 vertical grid and 24 horizontal grid
        UIManager.createGrids(root, 15, 24, 50, 50);
        //Initializes Mining game UI elements such as soils, drill machine, lavas
        UIManager.initializeGrids(root, drill, 15, 24);

        //Displays drill machine attributes at top left of screen
        drill.displayMachineAttributes(root);
        //Starts drill machine(For instance, fuel consumption starts)
        drill.runDrillEngine(UIManager, scene, root);

        stage.show();
    }
}