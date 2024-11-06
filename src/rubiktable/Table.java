package rubiktable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the game table and the operations to manipulate rows and columns.
 * @author Horánszki Patrik (CJJ14N)
 */
public class Table {
    private Color[][] table;
    private Color[] colors = {RED, BLUE, PURPLE, GREEN, YELLOW, ORANGE};
    private final int tableSize;

    // Custom color definitions
    public static final Color RED = new Color(215, 90, 90);
    public static final Color BLUE = new Color(90, 131, 215);
    public static final Color PURPLE = new Color(169, 123, 196);
    public static final Color GREEN = new Color(123, 196, 126);
    public static final Color YELLOW = new Color(242, 213, 102);
    public static final Color ORANGE = new Color(242, 160, 90);

    /**
     * Constructs the game table with a specified size.
     * Initializes the table with colors for each row and shuffles it to create the starting configuration.
     * @param tableSize the size of the game table
     */
    public Table(int tableSize) {
        this.tableSize = tableSize;

        table = new Color[tableSize][tableSize];
        for (int i = 0; i < tableSize; i++) {
            for (int j = 0; j < tableSize; j++) {
                table[i][j] = colors[i];
            }
        }
        while(isFinished()){
            shuffleTable();
        }
    }

    public Color getColor(int x, int y) {return table[x][y];}
    public int getTableSize() {return tableSize;}

    /**
     * Moves a row or column based on the given direction and index.
     * @param direction the direction of movement (UP, DOWN, LEFT, RIGHT)
     * @param indexToMove the index of the row or column to shift
     */
    public void move(Direction direction, int indexToMove) {
        switch (direction.name()) {
            case "UP" -> moveColumnUp(indexToMove);
            case "DOWN" -> moveColumnDown(indexToMove);
            case "LEFT" -> moveRowLeft(indexToMove);
            case "RIGHT" -> moveRowRight(indexToMove);
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

    /**
     * Checks if the game is completed by verifying if each row or column has uniform colors.
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
        return isFinishedVertical() || isFinishedHorizontal();
    }

    private boolean isFinishedVertical(){
        for (int j = 0; j < tableSize; j++) {
            Color matchColor = table[0][j];
            for (int i = 1; i < tableSize; i++) {
                if (!matchColor.equals(table[i][j])) {return false;}
            }
        }
        return true;
    }

    private boolean isFinishedHorizontal(){
        for (int i = 0; i < tableSize; i++) {
            Color matchColor = table[i][0];
            for (int j = 1; j < tableSize; j++) {
                if (!matchColor.equals(table[i][j])) {return false;}
            }
        }
        return true;
    }

    /**
     * Shuffles the table to randomize the colors for a new game start.
     */
    private void shuffleTable() {
        // 2D array to simple array
        List<Color> colorList = new ArrayList<>(tableSize*tableSize);
        for (Color[] row : table) {
            Collections.addAll(colorList, row);
        }
        Collections.shuffle(colorList);

        // Fill the table again with the shuffled colors
        for (int i = 0; i < tableSize; i++) {
            for (int j = 0; j < tableSize; j++) {
                table[i][j] = colorList.get(i * tableSize + j);
            }
        }
    }
    private Color[] shiftRight(Color[] colors){
        Color last = colors[colors.length-1];
        for(int i=colors.length-1; i>0; i--){
            colors[i] = colors[i-1];
        }
        colors[0] = last;
        return colors;
    }
    private Color[] shiftLeft(Color[] colors){
        Color first = colors[0];
        for(int i=0; i<colors.length-1; i++){
            colors[i] = colors[i+1];
        }
        colors[colors.length-1] = first;
        return colors;
    }
 
    // ------ Moving rows/columns ------

    private void moveColumnUp(int columnIndex) {
        Color[] colors = new Color[tableSize];
        for (int i = 0; i < tableSize; i++) {
            colors[i] = table[i][columnIndex];
        }
        colors = shiftLeft(colors);
        for (int i = 0; i < tableSize; i++) {
            table[i][columnIndex] = colors[i];
        }
    }

    private void moveColumnDown(int columnIndex) {
        Color[] colors = new Color[tableSize];
        for (int i = 0; i < tableSize; i++) {
            colors[i] = table[i][columnIndex];
        }
        colors = shiftRight(colors);
        for (int i = 0; i < tableSize; i++) {
            table[i][columnIndex] = colors[i];
        }
    }

    private void moveRowLeft(int rowIndex) {
        Color[] colors = new Color[tableSize];
        for (int j = 0; j < tableSize; j++) {
            colors[j] = table[rowIndex][j];
        }
        colors = shiftLeft(colors);
        for (int j = 0; j < tableSize; j++) {
            table[rowIndex][j] = colors[j];
        }
    }

    private void moveRowRight(int rowIndex) {
        Color[] colors = new Color[tableSize];
        for (int j = 0; j < tableSize; j++) {
            colors[j] = table[rowIndex][j];
        }
        colors = shiftRight(colors);
        for (int j = 0; j < tableSize; j++) {
            table[rowIndex][j] = colors[j];
        }
    }
}
