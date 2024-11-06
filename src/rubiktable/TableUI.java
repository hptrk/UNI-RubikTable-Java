package rubiktable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Sets up the game board's graphical elements.
 * Manages the visual representation of the game table and click counter.
 * @author Horánszki Patrik (CJJ14N)
 */
public class TableUI {
    private JPanel[][] panels;
    private JPanel tablePanel;
    private JLabel clickCountLabel;

    private int clickCount;
    private Table table;

    /**
     * Constructs a TableUI with a specified size.
     * Initializes the game table, click counter, and color panels.
     * @param tableSize the size of the game table
     */
    public TableUI(int tableSize) {
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(tableSize+2, tableSize+2));
        panels = new JPanel[tableSize][tableSize];
        clickCountLabel = createClickCountLabel();

        clickCount = 0;
        table = new Table(tableSize);

        fillTable(tableSize);
    }

    public JPanel getTablePanel() {return tablePanel;}
    public JLabel getClickCountLabel() {return clickCountLabel;}

    private void fillTable(int tableSize) {
        for (int i = 0; i < tableSize+2; i++) {
            for (int j = 0; j < tableSize+2; j++) {

                // ------ FIRST ROW ------
                if (i==0){
                    if (j==0 || j==tableSize+1){
                        // Corners
                        JButton button = createTransparentButton();
                        tablePanel.add(button);
                    } else{
                        // Up arrows
                        JButton button = createArrowButton(Direction.UP, j-1);
                        tablePanel.add(button);
                    }
                }
                // ------ LAST ROW ------
                else if(i==tableSize+1){
                    if(j==0 || j==tableSize+1){
                        // Corners
                        JButton button = createTransparentButton();
                        tablePanel.add(button);
                    } else{
                        // Down arrows
                        JButton button = createArrowButton(Direction.DOWN, j-1);
                        tablePanel.add(button);
                    }
                }
                // ------ MIDDLE ROWS ------
                else{
                    // Left arrows
                    if(j==0){
                        JButton button = createArrowButton(Direction.LEFT, i-1);
                        tablePanel.add(button);
                    }
                    // Right arrows
                    else if(j == tableSize+1){
                        JButton button = createArrowButton(Direction.RIGHT, i-1);
                        tablePanel.add(button);
                    }
                    // Cells of Rubik Table
                    else{
                        JPanel cell = new JPanel();
                        cell.setBackground(table.getColor(i-1,j-1));
                        panels[i-1][j-1] = cell;
                        tablePanel.add(cell);
                    }
                }
            }
        }
    }
    /**
     * Creates a transparent button with no background or border.
     * Used for corner cells of the game board.
     * @return a transparent JButton
     */
    private JButton createTransparentButton(){
        JButton button = new JButton();
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(new Color(217, 217, 217));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 100));
        return button;
    }
    /**
     * Creates an arrow button for shifting rows or columns.
     * @param direction the direction of movement (up, down, left, or right)
     * @param index the index of the row or column to shift
     * @return JButton with an arrow symbol indicating direction
     */
    private JButton createArrowButton(Direction direction, int index) {
        JButton button = new JButton(direction.getArrow());
        // Set appearance
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setForeground(Color.BLACK);
        button.setPreferredSize(new Dimension(100, 100));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        Color lightGrey = new Color(217, 217, 217);
        Color darkGrey = new Color(166, 166, 166);
        button.setBackground(lightGrey);
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(darkGrey);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(lightGrey);
            }
        });

        // Click event listener
        button.addActionListener(new ArrowActionListener(direction, index));
        return button;
    }

    /**
     * Refreshes the UI to reflect the current state of the game table.
     * Updates the color of each cell based on the latest table configuration.
     */
    private void refreshTableUI(){
        for (int i=0; i<panels.length; i++){
            for (int j=0; j<panels.length; j++){
                panels[i][j].setBackground(table.getColor(i,j));
            }
        }
    }

    /**
     * Creates the click counter display.
     * @return JLabel containing the current click count
     */
    private JLabel createClickCountLabel(){
        clickCountLabel = new JLabel("Click Count: 0");
        clickCountLabel.setHorizontalAlignment(JLabel.CENTER);
        clickCountLabel.setBackground(new Color(217, 217, 217));
        clickCountLabel.setOpaque(true);
        clickCountLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        return clickCountLabel;
    }

    /**
     * Manages the action of moving a row or column in the specified direction.
     */
    class ArrowActionListener implements ActionListener {
        private Direction direction;
        private int index;
        public ArrowActionListener(Direction direction, int index) {
            this.direction = direction;
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            table.move(direction, index);
            refreshTableUI();
            clickCountLabel.setText("Click Count: " + ++clickCount);

            if (table.isFinished()){
                JOptionPane.showMessageDialog(tablePanel,
                        "You've completed the game in " + clickCount + " clicks!",
                        "Well done!",
                        JOptionPane.PLAIN_MESSAGE);
                table = new Table(table.getTableSize());
                clickCount = 0;
                clickCountLabel.setText("Click Count: " + clickCount);
                refreshTableUI();
            }
        }
    }
}
