package entities;

import javax.swing.*;
import java.awt.*;

public class Obstacles {

    /*
        Erstellt ein Hindernis im Spiel
    */

    public static JLabel generateTree(int x, int y) {
        ImageIcon icon = new ImageIcon("textures/tree.png");
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(100, -1, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        JLabel tree = new JLabel(scaledIcon);
        tree.setBounds(x, y, 100, 100);

        System.out.println("[Obstacles.java] Baum erfolgreich erstellt." + x + " " + y);

        return tree;
    }

    public static JLabel generateWater(int x, int y) {
        ImageIcon icon = new ImageIcon("textures/water.png");
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(150, -1, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        JLabel water = new JLabel(scaledIcon);
        water.setBounds(x, y, 140, 140);

        System.out.println("[Obstacles.java] Wasser erfolgreich erstellt." + x + " " + y);

        return water;
    }


}
