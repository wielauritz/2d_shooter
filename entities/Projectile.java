package entities;

import javax.swing.*;
import java.awt.*;

public class Projectile {

    /*
        Erstellt ein Projektil im Spiel
    */

    public static int projectileSize = 20;

    public static JLabel createProjectile(int x, int y) {
        JLabel projectile = new JLabel(new ImageIcon("textures/projectile.png"));;
        projectile.setBounds(x, y, projectileSize, projectileSize);

        System.out.println("[Projectile.java] Projektil erfolgreich erstellt.");

        return projectile;
    }
}
