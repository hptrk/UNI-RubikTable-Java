package rubiktable;

/**
 * Enum representing the four directions for movement in the game.
 * Provides a symbol for each direction.
 */
public enum Direction {
    UP("↑"), RIGHT("→"), DOWN("↓"), LEFT("←");

    private final String arrow;

    Direction(String arrow) { this.arrow = arrow; }

    /**
     * Retrieves the arrow symbol associated with the direction.
     * @return the arrow symbol
     */
    public String getArrow() { return arrow; }
}
