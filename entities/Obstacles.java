package entities;

import javax.swing.*;
import java.awt.*;

public class Obstacles {

    public static JLabel createObstacle(int x, int y) {
        ImageIcon icon = new ImageIcon("textures/tree.png");
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(100, -1, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        JLabel obstacle = new JLabel(scaledIcon);
        obstacle.setBounds(x, y, 100, 100);

        System.out.println("[Obstacles.java] Hindernis erfolgreich erstellt.");

        return obstacle;
    }


}
