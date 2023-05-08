package entities;

import javax.swing.*;

public class Obstacles {

    public static JLabel createObstacle(int x, int y) {
        JLabel obstacle = new JLabel(new ImageIcon("textures/obstacle.png"));;
        obstacle.setBounds(x, y, 20, 50);

        System.out.println("[Obstacles.java] Hindernis erfolgreich erstellt.");

        return obstacle;
    }

}
