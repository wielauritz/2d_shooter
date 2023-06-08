package entities;

import javax.swing.*;
import java.awt.*;

public class Obstacles {

    /*
        Erstellt ein Fass als Hindernis im Spiel
    */

    public static JLabel generateBarrel(int x, int y) {
        ImageIcon icon = new ImageIcon("textures/entities/Obstacles/barrel.png");
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(64, -1, Image.SCALE_SMOOTH));

        JLabel barrel = new JLabel(scaledIcon);
        barrel.setBounds(x, y, 64, 64);

        System.out.println("[Obstacles.java] Baum erfolgreich erstellt.");

        return barrel;
    }

    /*
        Erstellt einen Baum als Hindernis im Spiel
    */

    public static JLabel generateTree(int x, int y) {
        ImageIcon icon = new ImageIcon("textures/entities/Obstacles/tree.png");
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH));

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
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(175, -1, Image.SCALE_SMOOTH));

        JLabel water = new JLabel(scaledIcon);
        water.setBounds(x, y, 160, 160);

        System.out.println("[Obstacles.java] Wasser erfolgreich erstellt.");

        return water;
    }


}
