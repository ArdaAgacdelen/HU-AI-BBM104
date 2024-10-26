import java.util.*;

/**
 * This class provides methods to calculate the fastest route between cities for the given map
 * and to create a barely connected map using the provided road data.
 */
public class MapManager {

    /**
     * Calculates the fastest route's length from the departure city to the arrival city.
     * Writes the fastest route information to the specified output file.
     *
     * @param outputFilePath       the path of the file to write the output to
     * @param allRoadsOfCities     a map containing all roads connecting the cities
     * @param departureCity        the name of the departure city
     * @param arrivalCity          the name of the arrival city
     * @param isBarelyConnectedMap boolean indicating if the calculation is for a barely connected map or original map
     * @return the distance travelled for the fastest route
     */
    public static int getFastestRoute(String outputFilePath, HashMap<String, ArrayList<Road>> allRoadsOfCities, String departureCity, String arrivalCity, boolean isBarelyConnectedMap) {

        ArrayList<Point> possiblePoints = new ArrayList<>();
        //First possible point is starting city
        possiblePoints.add(new Point(departureCity, null, null));
        HashMap<String, Point> pointsVisited = new HashMap<>();

        while (!pointsVisited.containsKey(arrivalCity)) { //While arrival-city haven't been visited (Route is not finished)
            //Sorts the possible points' list by the total distance needed to go to reach that points
            sortPointsListByDistanceTravelled(possiblePoints);

            //Moves to the point has the least total distance and deletes this point from possible-points list
            //Then adds all unvisited neighbors of this new point as possible points
            moveNextPoint(allRoadsOfCities, possiblePoints, pointsVisited);
        }
        //Fastest route is found
        OutputManager.writeFastestRouteInformationToFile(outputFilePath, departureCity, arrivalCity, pointsVisited, isBarelyConnectedMap);
        //Returns the length of fastest route
        return pointsVisited.get(arrivalCity).getDistanceTravelled();
    }

    /**
     * Creates a barely connected map from the given input data and writes the results to the specified output file.
     * Returns a map representing the barely connected roads of the cities.
     *
     * @param outputFilePath   the path of the file to write the output to
     * @param allRoadsOfCities a map containing all roads connecting the cities
     * @return a map representing the barely connected roads of the cities
     */
    public static HashMap<String, ArrayList<Road>> createBarelyConnectedMap(String outputFilePath, HashMap<String, ArrayList<Road>> allRoadsOfCities) {
        //Sorts all cities alphabetically and chooses the minimum one to start
        String startingCity = new TreeSet<>(allRoadsOfCities.keySet()).first();

        ArrayList<Point> possiblePoints = new ArrayList<>();
        //First possible point is starting city
        possiblePoints.add(new Point(startingCity, null, null));
        HashMap<String, Point> pointsVisited = new HashMap<>();

        while (pointsVisited.size() != allRoadsOfCities.keySet().size()) { //While all cities cannot linked to map
            //Sorts points by the roads, which connects current point to them, length
            sortPointsListByComingRoadLength(possiblePoints);

            //Moves to the point has the least connecting road length and deletes this point from possible-points list
            //Then adds all unvisited neighbors of this new point as possible points
            moveNextPoint(allRoadsOfCities, possiblePoints, pointsVisited);
        }

        //Barely connected map is found
        Set<String> setOfVisitedPoints = pointsVisited.keySet(); //Set consisting of city names

        ArrayList<Road> barelyConnectedRoads = new ArrayList<>(); //Array-list to keep all barely connected roads

        //Hash-map to keep barely connecting roads of cities
        HashMap<String, ArrayList<Road>> barelyConnectedRoadsOfCities = new HashMap<>();

        for (String visitedPoint : setOfVisitedPoints) { //For cities
            if (pointsVisited.get(visitedPoint).getRoadComesThisPoint() != null) { //If city is not the starting point
                //Gets road comes this point
                Road road = pointsVisited.get(visitedPoint).getRoadComesThisPoint();
                //Adds road to list
                barelyConnectedRoads.add(road);
                //Adds road for both of its ends in a map
                addRoadToCityMap(barelyConnectedRoadsOfCities, road);
            }
        }
        //Writes roads in ascending order of length to output file
        OutputManager.writeBarelyConnectedMap(outputFilePath, barelyConnectedRoads);

        //Returns Barely Connected Map [(City Name: String) -> roads: ArrayList<Road>]
        return barelyConnectedRoadsOfCities;
    }

    /**
     * Moves to the next point in the route calculation, updating the list of possible points and points visited.
     * Pre-condition: List must be provided sorted by the wanted priority
     *
     * @param cities         a map containing all roads connecting the cities
     * @param possiblePoints a list of possible points to visit next
     * @param pointsVisited  a map of points that have already been visited
     */
    private static void moveNextPoint(HashMap<String, ArrayList<Road>> cities, ArrayList<Point> possiblePoints, HashMap<String, Point> pointsVisited) {
        for (Point possiblePoint : possiblePoints) { //Trys until finding a proper point to move
            if (!pointsVisited.containsKey(possiblePoint.getName())) { //If possible point is not visited already
                //Visit possible point
                pointsVisited.put(possiblePoint.getName(), possiblePoint);
                possiblePoints.remove(possiblePoint);

                sortRoadsListByLength(cities.get(possiblePoint.getName()));
                for (Road road : cities.get(possiblePoint.getName())) { //For roads go to neighbors of current point
                    //The other end's (neighbor end) name
                    String nextPointName = (road.getFirstEnd()).equals(possiblePoint.getName()) ? road.getSecondEnd() : road.getFirstEnd();
                    //Adds all neighbors if not visited already as possible points
                    possiblePoints.add(new Point(nextPointName, possiblePoint, road));
                }
                break; //Break for loop as moving is done
            }
        }
    }

    /**
     * Adds a road to the map of cities -> their connecting roads.
     *
     * @param cities a map containing roads connecting the cities [(City Name: String) -> roads: ArrayList<Road>]
     * @param road   the road to be added to the map
     */
    public static void addRoadToCityMap(HashMap<String, ArrayList<Road>> cities, Road road) {
        String firstEnd = road.getFirstEnd();
        String secondEnd = road.getSecondEnd();

        Set<String> cityNames = cities.keySet();
        if (!cityNames.contains(firstEnd)) { //City is not added before
            ArrayList<Road> roads = new ArrayList<>();
            roads.add(road);
            cities.put(firstEnd, roads); //City is firstly added  with the road

        } else { //City is already added by another road
            cities.get(firstEnd).add(road); //Road added to the city
        }
        if (!cityNames.contains(secondEnd)) { //City is not added before
            ArrayList<Road> roads = new ArrayList<>();
            roads.add(road);
            cities.put(secondEnd, roads); //City is firstly added with the road

        } else { //City is already added by another road
            cities.get(secondEnd).add(road); //Road added to the city
        }
    }

    /**
     * Sorts a list of points by the distance travelled until reach that point.
     *
     * @param possiblePoints the list of points to be sorted
     */
    private static void sortPointsListByDistanceTravelled(ArrayList<Point> possiblePoints) {
        possiblePoints.sort(Comparator.comparingInt(Point::getDistanceTravelled));
    }


    /**
     * Sorts a list of points by the length of the road they are connected by.
     *
     * @param possiblePoints the list of points to be sorted
     */
    private static void sortPointsListByComingRoadLength(ArrayList<Point> possiblePoints) {
        possiblePoints.sort((p1, p2) -> {
            int result = Integer.compare(p1.getRoadComesThisPoint().getLength(), p2.getRoadComesThisPoint().getLength());
            if (result == 0) { //If lengths are equal
                return Integer.compare(p1.getRoadComesThisPoint().getId(), p2.getRoadComesThisPoint().getId());
            } else {
                return result;
            }
        });
    }


    /**
     * Sorts a list of roads by their length, and by their ID if lengths are equal.
     *
     * @param roads the list of roads to be sorted
     */
    public static void sortRoadsListByLength(ArrayList<Road> roads) {
        roads.sort((r1, r2) -> {
            int result = Integer.compare(r1.getLength(), r2.getLength());
            if (result == 0) { //If lengths are equal
                return Integer.compare(r1.getId(), r2.getId());
            } else {
                return result;
            }
        });
    }
}