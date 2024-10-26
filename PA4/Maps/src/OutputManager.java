import java.util.*;

/**
 * This class handles writing map analyzing methods' outputs(such as informative messages) to a file,
 */
public class OutputManager {

    /**
     * Writes the listed roads, which creates a barely connected map, to the specified output file.
     *
     * @param outputFilePath       the path of the file to write the output to
     * @param barelyConnectedRoads the list of roads that form the barely connected map
     */
    public static void writeBarelyConnectedMap(String outputFilePath, ArrayList<Road> barelyConnectedRoads) {
        FileIO.writeToFile(outputFilePath, "Roads of Barely Connected Map is:", true, true);
        //Sorts the roads given in list by their own length (short to long)
        MapManager.sortRoadsListByLength(barelyConnectedRoads);
        for (Road road : barelyConnectedRoads) {
            //toString() method is overridden to give an informative message of the road
            FileIO.writeToFile(outputFilePath, road.toString(), true, true);
        }
    }

    /**
     * Writes the fastest route information to the specified output file.
     *
     * @param outputFilePath       the path of the file to write the output to
     * @param departureCity        the name of the departure city
     * @param arrivalCity          the name of the arrival city
     * @param pointsVisited        a map of points that have been visited, keyed by city name
     * @param isBarelyConnectedMap a boolean indicating whether the route is for the barely connected map or original map
     */
    public static void writeFastestRouteInformationToFile(String outputFilePath, String departureCity, String arrivalCity, HashMap<String, Point> pointsVisited, boolean isBarelyConnectedMap) {

        if (isBarelyConnectedMap) { //Message for barely connected map
            //Point vector keeps the distance travelled to reach it, for its unique route.
            int fastestRouteForBarelyConnectedMap = pointsVisited.get(arrivalCity).getDistanceTravelled();
            FileIO.writeToFile(outputFilePath, String.format("Fastest Route from %s to %s on Barely Connected Map (%d KM):", departureCity, arrivalCity, fastestRouteForBarelyConnectedMap), true, true);
            //Writes track of the roads to reach arrival-city, recursively
            writeFastestRouteStops(pointsVisited.get(arrivalCity), outputFilePath);

        } else { //Message for original map
            //Point vector keeps the distance travelled to reach it, for its unique route.
            int fastestRouteForOriginalMap = pointsVisited.get(arrivalCity).getDistanceTravelled();
            FileIO.writeToFile(outputFilePath, String.format("Fastest Route from %s to %s (%d KM):", departureCity, arrivalCity, fastestRouteForOriginalMap), true, true);
            //Writes track of the roads to reach arrival-city, recursively
            writeFastestRouteStops(pointsVisited.get(arrivalCity), outputFilePath);
        }
    }

    /**
     * Writes analysis of construction material usage and route distances among two map to the specified output file.
     *
     * @param outputFilePath               the path of the file to write the output to
     * @param allRoadsOfCities             a map of cities and an array-list of all roads connecting them [(City Name: String) -> roads: ArrayList<Road>]
     * @param barelyConnectedRoadsOfCities a map of cities and an array-list of barely roads connecting them [(City Name: String) -> roads: ArrayList<Road>]
     * @param barelyConnectedRouteDistance the total distance of the fastest route in the barely connected map
     * @param normalConnectedRouteDistance the total distance of the fastest route in the original map
     */
    public static void writeAnalyses(String outputFilePath, HashMap<String, ArrayList<Road>> allRoadsOfCities, HashMap<String, ArrayList<Road>> barelyConnectedRoadsOfCities, int barelyConnectedRouteDistance, int normalConnectedRouteDistance) {

        //Calculation of total length of all roads for both maps.
        //Result will be double of the actual one since map holds same road for both ends of it
        //However, thanks to we use just ratio of them it is unnecessary to divide both of them by 2
        int constructionLengthForBarelyConnectedRoads = 0;
        int constructionLengthForAllRoads = 0;
        for (ArrayList<Road> roads : allRoadsOfCities.values()) { //For all cities of original map
            for (Road road : roads) { //For all roads connecting to the city
                constructionLengthForAllRoads += road.getLength(); //Adds length of road
            }
        }
        for (ArrayList<Road> roads : barelyConnectedRoadsOfCities.values()) { //For all cities of map
            for (Road road : roads) { //For barely roads connecting to the city
                constructionLengthForBarelyConnectedRoads += road.getLength(); //Adds length of road
            }
        }

        FileIO.writeToFile(outputFilePath, String.format("Analysis:\n" +
                "Ratio of Construction Material Usage Between Barely Connected and Original Map: %.2f\n" +
                "Ratio of Fastest Route Between Barely Connected and Original Map: %.2f", (float) constructionLengthForBarelyConnectedRoads / constructionLengthForAllRoads, (float) barelyConnectedRouteDistance / normalConnectedRouteDistance), true, false);
    }

    /**
     * Recursively writes track of the roads which reach arrival-city by using the fastest route
     *
     * @param point          the current point in the route
     * @param outputFilePath the path of the file to write the output to
     */
    private static void writeFastestRouteStops(Point point, String outputFilePath) {
        //Stops if point comes before parameter point is null (Reached starting point)
        if (point.getPointComesBeforeThisPoint() != null) {
            //Recursive call
            writeFastestRouteStops(point.getPointComesBeforeThisPoint(), outputFilePath);
            //toString() method is overridden to give an informative message of the road
            FileIO.writeToFile(outputFilePath, point.getRoadComesThisPoint().toString(), true, true);
        }
    }
}