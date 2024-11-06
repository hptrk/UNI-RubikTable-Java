package rubiktable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents the main UI of the Rubik's Table game.
 * Initializes the game window, sets up the board and click counter,
 * and creates a menu to start new games of different sizes.
 * @author Horánszki Patrik (CJJ14N)
 */
public class GameUI {
    private JFrame frame;
    private TableUI tableUI;

    private final int INITIAL_BOARD_SIZE = 4;

    public GameUI() {
        frame = new JFrame("Rubik Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("icon.png");
        frame.setIconImage(icon.getImage());

        tableUI = new TableUI(INITIAL_BOARD_SIZE);
        frame.getContentPane().add(tableUI.getTablePanel(), BorderLayout.CENTER);
        frame.getContentPane().add(tableUI.getClickCountLabel(), BorderLayout.SOUTH);

        createMenu();

        frame.pack();
        frame.setVisible(true);
    }

    private void createMenu(){
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        JMenu newMenu = new JMenu("New");
        gameMenu.add(newMenu);
        int[] tableSizes = new int[]{2,4,6};
        // [2,4,6]
        for(int size : tableSizes) {
            JMenuItem sizeMenuItem = new JMenuItem(size + "x" + size);
            newMenu.add(sizeMenuItem);
            sizeMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().remove(tableUI.getTablePanel());
                    frame.getContentPane().remove(tableUI.getClickCountLabel());

                    tableUI = new TableUI(size);
                    frame.getContentPane().add(tableUI.getTablePanel(), BorderLayout.CENTER);
                    frame.getContentPane().add(tableUI.getClickCountLabel(), BorderLayout.SOUTH);
                    frame.pack();
                }
            });
        }
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { System.exit(0); }
        });
    }
}
