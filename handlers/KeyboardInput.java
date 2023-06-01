package handlers;

import components.Overlay;
import components.Window;
import entities.Player;
import entities.Projectile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class KeyboardInput extends Window {

    private static Set<Integer> pressedKeys = new HashSet<>();

    public static boolean spacePressed = false;

    public static boolean enabled = true;

    public static Timer playerMoveTimer;

    /*
        Tastendruck registrieren
    */

    public static void KeyPress() {

        System.out.println("[KeyboardInput.java] Tastatur-Listener aktiviert.");

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                pressedKeys.add(keyCode);
                if (keyCode == KeyEvent.VK_SPACE) {
                    spacePressed = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                pressedKeys.remove(keyCode);
                if (keyCode == KeyEvent.VK_SPACE) {
                    spacePressed = false;
                }
            }
        });

        /*
            Timer mit 16 Millisekunden für ca. 60fps zum flüssigen Bewegen des Spielers
        */

        playerMoveTimer = new Timer(16, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (!enabled) return;

                int directionX = 0;
                int directionY = 0;
                int speed = 5;

                //Gedrückte Tasten überprüfen und Spielerbewegung berechnen:

                for (Integer keyCode : pressedKeys) {
                    if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                        directionY -= speed;
                    } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
                        directionY += speed;
                    } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
                        directionX -= speed;
                    } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
                        directionX += speed;
                    }
                }

                //Spieler bewegen:

                if (directionX != 0 || directionY != 0) {
                    Player.move(directionX, directionY);
                }

                if (spacePressed) {
                    shootProjectile();
                }
            }
        });
        playerMoveTimer.start();

    }

    /*
        Schießt das Projektil
    */

    public static void shootProjectile() {


            //Mehr als ein Projektil pro Leertasten-Klick verhindern:

            if (!spacePressed) {
                return;
            }
            spacePressed = false;

            //Spielerposition abrufen:

            Point playerPosition = new Point(Player.player.getX() + Player.playerSize / 2,
                    Player.player.getY() + Player.playerSize / 2);

            //Winkel zwischen Spieler und Mauszeiger berechnen:

            double angle = MouseInput.getPlayerToMouseAngle(playerPosition);

            //Geschwindigkeiten für das Projektil berechnen:

            int projectileSpeed = 30;
            double speedX = Math.cos(angle) * projectileSpeed;
            double speedY = Math.sin(angle) * projectileSpeed;

            //Projektil erstellen und anzeigen:

            JLabel projectile = Projectile.createProjectile(playerPosition.x, playerPosition.y);
            frame.getContentPane().add(projectile);
            frame.getContentPane().setComponentZOrder(projectile, 0);

            Overlay.updateAmmoHUD(1);

            //Timer zum Bewegen des Projektils erstellen:


            Timer timer = new Timer(1, new ActionListener() {
                public void actionPerformed(ActionEvent e) {


                    // Position des Projektils aktualisieren:

                    double newX = projectile.getX() + speedX;
                    double newY = projectile.getY() + speedY;

                    //Überprüfen, ob das Projektil ein Hindernis berührt:

                    boolean collided = false;
                    for (Component obstacle : Player.obstacles) {
                        projectile.setLocation((int) newX, (int) newY);
                        if (Player.isCollidingWithObstacle(projectile, obstacle)) {
                            collided = true;
                            break;
                        }
                    }

                    if (collided) {
                        projectile.setLocation((int) (projectile.getX() - speedX), (int) (projectile.getY() - speedY));
                    } else {
                        projectile.setLocation((int) newX, (int) newY);
                    }

                    //Projektil entfernen, wenn es ein Hindernis berührt:

                    if (newX < 0 || newX > 750 || newY < 0 || newY > 750 || collided) {

                        projectile.setVisible(false);
                        ((Timer) e.getSource()).stop();
                        frame.getContentPane().remove(projectile);
                    }

                    //Projektil entfernen, wenn es außerhalb des Fensters ist:

                    if (newX < 0 || newX > 750 || newY < 0 || newY > 750) {

                        projectile.setVisible(false);
                        ((Timer) e.getSource()).stop();
                        frame.getContentPane().remove(projectile);
                    }
                }
            });
            timer.start();
    }
}
