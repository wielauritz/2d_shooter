package handlers;

import components.DeathScreen;
import components.Game;
import components.Overlay;
import components.Window;
import entities.Player;
import entities.Projectile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static components.Menu.closeButton;
import static components.Menu.playButton;
import static components.Window.frame;
import static handlers.KeyboardInput.playerMoveTimer;

public class MouseInput {

    public static boolean started = false;

    public static boolean running = true;

    /*
        Letzte Position der Maus speichern:
    */

    private static Point lastPosition = MouseInfo.getPointerInfo().getLocation();

    /*
        Mausbewegung erkennen
    */

    public static void MouseMove() {

        System.out.println("[MouseInput.java] Mausbewegungs-Listener aktiviert.");

        //Alle 100ms Bewegungs-Check ausführen:

        while (running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Aktuelle Position der Maus speichern:

            Point currentPosition = MouseInfo.getPointerInfo().getLocation();

            //Wenn neue Maus-Position ungleich alte Mausposition:

            if (!currentPosition.equals(lastPosition)) {
                int directionX = currentPosition.x - lastPosition.x;
                int directionY = currentPosition.y - lastPosition.y;

                // - - - Hier andere Aktionen einfügen - - - \\

                //Letzte Position aktualisieren:

                lastPosition = currentPosition;
            }
        }
    }

    public static void MouseClick() {

        System.out.println("[MouseInput.java] Mausklick-Listener aktiviert.");

        /*
            Mausklick im Hauptmenü registrieren:
        */

        playButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("[MouseInput.java] Spielfeld wird erstellt, bitte warten...");

                //Spielfeld dem Fenster übergeben:

                Window.frame.setContentPane(Game.create());
                started = true;

                //Tastendruck simulieren, um das Fenster zu aktualisieren:

                KeyboardInput.spacePressed = true;

                System.out.println("[MouseInput.java] Spielfeld erfolgreich geladen.");

            }
        });

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("[MouseInput.java] Spiel wird beendet, bitte warten...");

                //Fenster schließen und Timer stoppen, damit das Spiel beendet werden kann:

                Window.frame.dispose();
                if (playerMoveTimer != null) {
                    playerMoveTimer.stop();
                }
                running = false;
            }
        });

        components.Window.frame.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (started) {
                    if (e.getButton() == MouseEvent.BUTTON1) {

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
            }
        });
    }

        /*
            Bewegungsrichtung der Maus feststellen
        */

    private static String getDirection(int directionX, int directionY) {
        if (directionX > 0 && directionY > 0) {
            return "rechts & runter";
        } else if (directionX > 0 && directionY == 0) {
            return "rechts";
        } else if (directionX > 0 && directionY < 0) {
            return "rechts & hoch";
        } else if (directionX == 0 && directionY > 0) {
            return "runter";
        } else if (directionX == 0 && directionY < 0) {
            return "hoch";
        } else if (directionX < 0 && directionY > 0) {
            return "links & runter";
        } else if (directionX < 0 && directionY == 0) {
            return "links";
        } else if (directionX < 0 && directionY < 0) {
            return "links & hoch";
        } else {
            return "unbekannt";
        }
    }

    public static double getPlayerToMouseAngle(Point playerPosition) {

        // Mausposition relativ zum Fenster berechnen:

        Point mousePositionOnScreen = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePositionOnScreen, components.Window.frame.getContentPane());
        Point mousePosition = mousePositionOnScreen;

        // Winkel berechnen:

        double angle = Math.atan2(mousePosition.y - playerPosition.y, mousePosition.x - playerPosition.x);

        return angle;
    }


}
