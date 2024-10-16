import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * This class provides static methods to reach and read assets of the Mining game.
 * Assets can be modified by these methods.
 */
public class Assets {

    /**
     * This method gets .png images at given path.
     * Returns a hash map that has file names as keys and javafx.scene.image.Image instances as values.
     *
     * @param folderPath Folder will be read to pull png images.
     * @return The hash map that has file names as keys and javafx.scene.image.Image instances as values
     * @throws IOException Precondition: Path must be true
     */
    public static HashMap<String, Image> getPNGFiles(String folderPath) throws IOException {

        HashMap<String, Image> returnMap = new HashMap<>();

        //Filters png files in folder then puts them into hash map will be returned
        Files.list(Paths.get(folderPath)).filter(Files::isRegularFile)
                .filter(path -> path.toString().toLowerCase().endsWith(".png"))
                .forEach(path ->
                        {
                            Image image = new Image(path.toUri().toString());
                            String fileName = path.getFileName().toString();
                            returnMap.put(fileName, image);
                        }
                );
        return returnMap;
    }


    /**
     * This method hard-codes valuables' properties as Valuable objects and returns an array of them.
     *
     * @return The array of valuable objects in Mining game
     */
    public static Valuable[] getValuables() {

        return new Valuable[]{new Valuable("ironium", 10, 30),
                new Valuable("bronzium", 10, 60), new Valuable("silverium", 10, 100),
                new Valuable("goldium", 20, 250), new Valuable("platinum", 30, 750),
                new Valuable("einsteinium", 40, 2000), new Valuable("emerald", 60, 5000),
                new Valuable("ruby", 80, 20000), new Valuable("diamond", 100, 100000),
                new Valuable("amazonite", 120, 500000)};
    }

    /**
     * This method finds soil-top images in game images map and returns an array of them.
     *
     * @param gameImages Game images' map that is pulled by getPNGFiles
     * @return The array of soil-top images
     */
    public static Image[] getTopImages(HashMap<String, Image> gameImages) {
        Image[] tops = new Image[2];

        for (int i = 1; i < 3; i++) {
            String topName = "top_0".concat(String.valueOf(i).concat(".png"));
            tops[i - 1] = gameImages.get(topName);
        }
        return tops;
    }

    /**
     * This method finds obstacle images in game images map and returns an array of them.
     *
     * @param gameImages Game images' map that is pulled by getPNGFiles
     * @return The array of obstacle images
     */
    public static Image[] getObstacleImages(HashMap<String, Image> gameImages) {
        Image[] obstacles = new Image[3];

        for (int i = 1; i < 4; i++) {
            String obstacleName = "obstacle_0".concat(String.valueOf(i).concat(".png"));
            obstacles[i - 1] = gameImages.get(obstacleName);
        }
        return obstacles;
    }

    /**
     * This method finds lava images in game images map and returns an array of them.
     *
     * @param gameImages Game images' map that is pulled by getPNGFiles
     * @return The array of lava images
     */
    public static Image[] getLavaImages(HashMap<String, Image> gameImages) {
        Image[] lavas = new Image[3];

        for (int i = 1; i < 4; i++) {
            String lavaName = "lava_0".concat(String.valueOf(i).concat(".png"));
            lavas[i - 1] = gameImages.get(lavaName);
        }
        return lavas;
    }

    /**
     * This method finds soil images in game images map and returns an array of them.
     *
     * @param gameImages Game images' map that is pulled by getPNGFiles
     * @return The array of soil images
     */
    public static Image[] getSoilImages(HashMap<String, Image> gameImages) {
        Image[] soils = new Image[5];

        for (int i = 1; i < 6; i++) {
            String soilName = "soil_0".concat(String.valueOf(i).concat(".png"));
            soils[i - 1] = gameImages.get(soilName);
        }
        return soils;
    }


    /**
     * This method finds valuable images in game images map.
     * Returns a map which has file names as keys and valuable images as values.
     *
     * @param gameImages Game images' map that is pulled by getPNGFiles
     * @return The map which has file names as keys and javafx.scene.image.Image instances as values
     */
    public static HashMap<String, Image> getValuableImages(HashMap<String, Image> gameImages) {
        String[] valuableNames = {"valuable_amazonite.png", "valuable_bronzium.png", "valuable_diamond.png", "valuable_einsteinium.png", "valuable_emerald.png", "valuable_goldium.png", "valuable_ironium.png", "valuable_platinum.png", "valuable_ruby.png", "valuable_silverium.png"};
        HashMap<String, Image> valuables = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            valuables.put(valuableNames[i], gameImages.get(valuableNames[i]));
        }
        return valuables;
    }


    /**
     * This method creates and returns media player object fot the audio at given path.
     *
     * @param audioPath Path of audio file
     * @return javafx.scene.media.MediaPlayer instance for given audio
     */
    public static MediaPlayer getSoundMedia(String audioPath) {
        Media media = new Media(new File(audioPath).toURI().toString());
        return new MediaPlayer(media);
    }

    /**
     * This method reads pixels at wanted piece of image and writes on a new image instance that will be returned.
     *
     * @param originalImage Image will be cropped
     * @param cropX         x-coordinate of starting point
     * @param cropY         y-coordinate of starting point
     * @param cropWidth     Cropped image's width
     * @param cropHeight    Cropped image's height
     * @return Cropped image
     */
    public static WritableImage cropImage(Image originalImage, int cropX, int cropY, int cropWidth, int cropHeight) {

        WritableImage croppedImage = new WritableImage(cropWidth, cropHeight);

        // Gets a PixelReader from the original image
        PixelReader pixelReader = originalImage.getPixelReader();

        // Gets a PixelWriter from the WritableImage
        PixelWriter pixelWriter = croppedImage.getPixelWriter();

        // Copies pixels from the original image to the cropped image
        pixelWriter.setPixels(0, 0, cropWidth, cropHeight, pixelReader, cropX, cropY);

        return croppedImage;
    }
}
