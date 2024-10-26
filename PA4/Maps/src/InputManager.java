import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class handles reading input data from a file and
 * processing it into a structured format for use in a map analyzer application.
 */
public class InputManager {

    /**
     * Reads the input from the specified file and processes it into a HashMap.
     * The HashMap contains the departure city, arrival city, a map of all cities
     * and their connecting roads in an array-list.
     *
     * @param filePath the path of the file to read the input from
     * @return a HashMap containing the processed input data
     */
    public static HashMap<String, Object> readInput(String filePath) {

        String[] lines = FileIO.readFile(filePath, true, true);
        String[] departureAndArrival = lines[0].split("\t"); //First line
        String departure = departureAndArrival[0];
        String arrival = departureAndArrival[1];

        //A map of all cities and their connecting roads in an array-list.
        HashMap<String, ArrayList<Road>> allRoadsOfCities = new HashMap<>();

        for (int i = 1; i < lines.length; i++) { //Rest of the lines

            Road road = createRoadFromInputLine(lines[i]);
            //Adds Road object just created into both ends of the road in map
            MapManager.addRoadToCityMap(allRoadsOfCities, road);
        }

        HashMap<String, Object> input = new HashMap<>(); //A hash-map to return
        input.put("departure", departure); //Includes departure city from the first line
        input.put("arrival", arrival); //Includes arrival city from the first line
        input.put("allRoadsOfCities", allRoadsOfCities); //Includes map created from the rest of lines

        return input;
    }

    /**
     * Creates a Road object from a line of input.
     *
     * @param line the line of input at specified structure ("<FirstEnd>\t<SecondEnd>\t<Length>\t<ID>")
     * @return a new Road object created from the input line
     */
    private static Road createRoadFromInputLine(String line) {
        String[] lineContent = line.split("\t");
        String firstEnd = lineContent[0];
        String secondEnd = lineContent[1];
        int length = Integer.parseInt(lineContent[2]);
        int id = Integer.parseInt(lineContent[3]);

        return new Road(firstEnd, secondEnd, length, id, line);
    }
}
