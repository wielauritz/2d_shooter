package entities;

import javax.swing.*;
import java.awt.*;

public class Obstacles {

    /*
        Erstellt einen Baum als Hindernis im Spiel
    */

    public static JLabel generateTree(int x, int y) {
        ImageIcon icon = new ImageIcon("textures/entities/Obstacles/tree.png");
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(100, -1, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        JLabel tree = new JLabel(scaledIcon);
        tree.setBounds(x, y, 100, 100);

        System.out.println("[Obstacles.java] Baum erfolgreich erstellt.");

        return tree;
    }

    /*
        Erstellt Wasser als Hindernis im Spiel
    */

    public static JLabel generateWater(int x, int y) {
        ImageIcon icon = new ImageIcon("textures/entities/Obstacles/water.png");
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(175, -1, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        JLabel water = new JLabel(scaledIcon);
        water.setBounds(x, y, 160, 160);

        System.out.println("[Obstacles.java] Wasser erfolgreich erstellt.");

        return water;
    }


}
