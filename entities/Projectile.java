package entities;

import javax.swing.*;

public class Projectile {

    /*
        Erstellt ein Projektil im Spiel
    */

    public static int projectileSize = 20;

    public static JLabel createProjectile(int x, int y) {
        JLabel projectile = new JLabel(new ImageIcon("textures/entities/Projectile/projectile.png"));

        projectile.setBounds(x, y, projectileSize, projectileSize);

        return projectile;
    }
}
