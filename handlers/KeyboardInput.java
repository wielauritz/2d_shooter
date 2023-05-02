package handlers;

import components.Window;
import entities.Player;
import entities.Projectile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardInput extends Window {

    /*
        Tastendruck erkennen und Spieler bewegen (Player.move(newX, newY);)
    */

    public static void KeyPress() {
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                    Player.move(0, -5);
                    System.out.println("[KeyboardInput.java] Spieler nach oben bewegt.");
                } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
                    Player.move(0, 5);
                    System.out.println("[KeyboardInput.java] Spieler nach unten bewegt.");
                } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
                    Player.move(-5, 0);
                    System.out.println("[KeyboardInput.java] Spieler nach links bewegt.");
                } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
                    Player.move(5, 0);
                    System.out.println("[KeyboardInput.java] Spieler nach rechts bewegt.");
                } else if (keyCode == KeyEvent.VK_SPACE) {

                    //Spielerposition abrufen:
                    Point playerPosition = new Point(Player.player.getX() + Player.playerSize / 2,
                            Player.player.getY() + Player.playerSize / 2);

                    //Winkel zwischen Spieler und Mauszeiger berechnen:
                    double angle = MouseInput.getPlayerToMouseAngle(playerPosition);

                    //Geschwindigkeiten für das Projektil berechnen:
                    int projectileSpeed = 30;
                    double speedX = Math.cos(angle) * projectileSpeed;
                    double speedY = Math.sin(angle) * projectileSpeed;

                    System.out.println("[KeyboardInput.java] Projektil-Speed auf x=" + speedX + " und y=" + speedY + " gesetzt.");

                    //Projektil erstellen und dem Panel hinzufügen:
                    JLabel projectile = Projectile.createProjectile(playerPosition.x, playerPosition.y);
                    frame.getContentPane().add(projectile);
                    System.out.println("[KeyboardInput.java] Projektil bei x=" + playerPosition.x + " und y=" + playerPosition.y + " eingefügt.");

                    //Timer zum Bewegen des Projektils erstellen:
                    Timer timer = new Timer(1, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // Position des Projektils aktualisieren
                            double newX = projectile.getX() + speedX;
                            double newY = projectile.getY() + speedY;
                            projectile.setLocation((int) newX, (int) newY);

                            //Projektil entfernen, wenn es außerhalb des Fensters ist:
                            if (newX < 0 || newX > frame.getWidth() || newY < 0 || newY > frame.getHeight()) {
                                projectile.setVisible(false);
                                ((Timer) e.getSource()).stop();
                                frame.getContentPane().remove(projectile);
                            }
                        }
                    });

                    timer.start();
                }
            }
        });
    }
}