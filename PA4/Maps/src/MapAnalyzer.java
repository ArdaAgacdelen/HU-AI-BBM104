import java.util.*;

/**
 * This class serves the main class of a map analyzer application.
 */
public class MapAnalyzer {
    /**
     * Main method of main class.
     * Contains necessary method calls of application
     * Pre-condition: There must be at least 2 argument and must start with;
     * args[0]: Input file in specified format
     * args[1]: Output file
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        //Either creates or cleans the file namely second argument of command line
        FileIO.writeToFile(args[1], "", false, false);

        //Reads input file and pulls data
        HashMap<String, Object> inputMap = InputManager.readInput(args[0]);
        //Keeps departure city data
        String departureCity = (String) inputMap.get("departure");
        //Keeps arrival city data
        String arrivalCity = (String) inputMap.get("arrival");
        //Keeps cities' and roads connects to them in map
        HashMap<String, ArrayList<Road>> allRoadsOfCities = (HashMap<String, ArrayList<Road>>) inputMap.get("allRoadsOfCities");

        //Finds fastest route of original map, writes information about it to output and last, returns length of the route
        int normalRouteDistance = MapManager.getFastestRoute(args[1], allRoadsOfCities, departureCity, arrivalCity, false);
        //Creates barely connected map, writes information about it to output and last, returns the map
        HashMap<String, ArrayList<Road>> barelyConnectedRoadsOfCities = MapManager.createBarelyConnectedMap(args[1], allRoadsOfCities);
        //Finds fastest route of barely connected map, writes information about it to output and last, returns length of the route
        int barelyConnectedRouteDistance = MapManager.getFastestRoute(args[1], barelyConnectedRoadsOfCities, departureCity, arrivalCity, true);

        //Writes analyses are done from these two maps.
        OutputManager.writeAnalyses(args[1], allRoadsOfCities, barelyConnectedRoadsOfCities, barelyConnectedRouteDistance, normalRouteDistance);
    }
}
