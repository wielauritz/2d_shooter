package entities;

import javax.swing.*;

public class Grid extends JFrame {
    private static final int GRID_SIZE = 750;
    private static final int CELL_SIZE = 50;

    public Grid() {
        setSize(GRID_SIZE, GRID_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        addObstacles();

        setVisible(true);
    }

    private void addObstacles() {
        add(Obstacles.generateTree(100, 100));
        add(Obstacles.generateTree(200, 200));
        add(Obstacles.generateWater(400, 400));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Grid::new);
    }
}
