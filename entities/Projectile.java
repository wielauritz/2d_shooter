package entities;

import algorithms.GameLoop;
import components.Overlay;
import handlers.MouseInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static components.Window.frame;

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

    public static void shootProjectile() {



        // Player position
        Point playerPosition = new Point(Player.player.getX() + Player.size / 2,
                Player.player.getY() + Player.size / 2);

        // Calculate angle
        double angle = MouseInput.getPlayerToMouseAngle(playerPosition);

        // Calculate projectile speed
        int projectileSpeed = 30;
        double speedX = Math.cos(angle) * projectileSpeed;
        double speedY = Math.sin(angle) * projectileSpeed;

        // Create and display the projectile
        JLabel projectile = Projectile.createProjectile(playerPosition.x, playerPosition.y);
        frame.getContentPane().add(projectile);
        frame.getContentPane().setComponentZOrder(projectile, 0);

        Overlay.updateAmmoHUD(1);

        // Create a timer for the projectile movement
        Timer projectileTimer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Update projectile position
                double newX = projectile.getX() + speedX;
                double newY = projectile.getY() + speedY;

                // Update projectile position
                projectile.setLocation((int) newX, (int) newY);

                // Check for collision with obstacles
                boolean collided = false;
                for (Component obstacle : Player.obstacles) {
                    if (Player.isCollidingWithObstacle(projectile, obstacle, false)) {
                        collided = true;
                        break;
                    }
                }


                // Handle collision and projectile removal
                if (collided || newX < 0 || newX > 750 || newY < 0 || newY > 750) {
                    projectile.setVisible(false);
                    ((Timer) e.getSource()).stop();
                    frame.getContentPane().remove(projectile);
                } else {
                    projectile.setLocation((int) newX, (int) newY);
                }
            }
        });

        // Start the projectile timer
        projectileTimer.start();
    }
}
