/**
 * This class represents valuable gems and mines in a mining game.
 * It provides fields to keep attributes of valuable.
 */
public class Valuable {

    /**
     * This constructor initializes attributes of valuable.
     *
     * @param name   Name of valuable
     * @param weight Weight of valuable in a single block
     * @param worth  Net worth of valuable in a single block
     */
    public Valuable(String name, int weight, int worth) {
        this.name = name;
        this.weight = weight;
        this.worth = worth;
    }

    private final String name;
    private final int weight;
    private final int worth;


    /**
     * Getter for valuable name.
     *
     * @return Name of valuable
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for valuable weight.
     *
     * @return Weight of valuable in a single block
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Getter for valuable worth.
     *
     * @return Net worth of valuable in a single block
     */
    public int getWorth() {
        return worth;
    }
}
