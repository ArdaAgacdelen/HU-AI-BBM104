/**
 * This class represents a road with two endpoints, a length, an ID, and a string representation message.
 */
public class Road {
    private final String firstEnd;
    private final String secondEnd;
    private final int length;
    private final int id;
    private final String toStr;

    /**
     * Constructs a new Road object with the specified endpoints, length, ID, and string representation message.
     *
     * @param firstEnd  the name of the first endpoint of the road
     * @param secondEnd the name of the second endpoint of the road
     * @param length    the length of the road
     * @param id        the unique identifier of the road
     * @param toStr     the string representation of the road
     */
    Road(String firstEnd, String secondEnd, int length, int id, String toStr) {
        this.firstEnd = firstEnd;
        this.secondEnd = secondEnd;
        this.length = length;
        this.id = id;
        this.toStr = toStr;
    }

    /**
     * Returns the name of the first endpoint of the road.
     *
     * @return the name of the first endpoint
     */
    public String getFirstEnd() {
        return firstEnd;
    }

    /**
     * Returns the name of the second endpoint of the road.
     *
     * @return the name of the second endpoint
     */
    public String getSecondEnd() {
        return secondEnd;
    }

    /**
     * Returns the length of the road.
     *
     * @return the length of the road
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns the unique identifier of the road.
     *
     * @return the identifier of the road
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the string representation of the road.
     *
     * @return the string representation of the road
     */
    @Override
    public String toString() {
        return toStr;
    }
}
