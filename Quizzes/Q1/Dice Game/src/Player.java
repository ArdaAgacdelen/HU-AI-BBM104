/**
 * This class defines a player object for a dice game.
 */
public class Player {
    /**
     * Constructor to create new player object with name.
     * @param name Name of the player.
     */
    Player(String name){
        this.name = name;
    }

    private String name; //Since name cannot change after initialization there is no set method.
    private int score = 0;
    private boolean gameOver = false; //Did player lose the game?


    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}