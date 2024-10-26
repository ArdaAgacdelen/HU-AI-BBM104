/**
 * This class represents a point vector in a road map.
 * It keeps name of the point,
 * the total distance travelled to reach this point,
 * the {@code Point} object that comes before this point in the path,
 * and the road that leads to this point.
 */
public class Point {

    private final String name;
    private final int distanceTravelled;
    private final Point pointComesBeforeThisPoint;
    private final Road roadComesThisPoint;


    /**
     * Constructs a Point with the specified name, the preceding point,
     * and the road that leads to this point.
     * Calculates and initializes distance travelled to reach this point.
     *
     * @param name                      the name of the point
     * @param pointComesBeforeThisPoint the point object that comes before this point in the path
     * @param roadComesThisPoint        the road object that leads to this point
     */
    public Point(String name, Point pointComesBeforeThisPoint, Road roadComesThisPoint) {
        this.name = name;
        this.pointComesBeforeThisPoint = pointComesBeforeThisPoint;
        this.roadComesThisPoint = roadComesThisPoint;

        //Method call to calculate and initialize the distance field
        distanceTravelled = calculateDistanceTravelled(this);
    }


    /**
     * Recursively calculates the total distance travelled to reach the specified point.
     *
     * @param point the point to calculate the distance
     * @return the total distance travelled to reach the specified point
     */
    private int calculateDistanceTravelled(Point point) {
        if (point.pointComesBeforeThisPoint == null) {
            return 0;

        } else {
            return calculateDistanceTravelled(point.pointComesBeforeThisPoint) + point.roadComesThisPoint.getLength();
        }
    }

    /**
     * Returns the name of the point.
     *
     * @return the name of the point
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the total distance travelled to reach this point.
     *
     * @return the total distance travelled to reach this point
     */
    public int getDistanceTravelled() {
        return distanceTravelled;
    }

    /**
     * Returns the point that comes before this point in the path.
     *
     * @return the point object that comes before this point in the path
     */
    public Point getPointComesBeforeThisPoint() {
        return pointComesBeforeThisPoint;
    }

    /**
     * Returns the road that leads to this point.
     *
     * @return the road that leads to this point
     */
    public Road getRoadComesThisPoint() {
        return roadComesThisPoint;
    }
}
