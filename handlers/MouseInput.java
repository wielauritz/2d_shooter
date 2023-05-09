package handlers;

import entities.Player;
import entities.Projectile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput {

    /*
        Letzte Position der Maus speichern:
    */

    private static Point lastPosition = MouseInfo.getPointerInfo().getLocation();

    /*
        Mausbewegung erkennen
    */

    public static String MouseMove() {

        //Alle 100ms Bewegungs-Check ausführen:

        while (true) {
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

                //Bewegung in die Konsole schreiben:

                /*System.out.println("[MouseInput.java] Maus bewegt: " + currentPosition.x + " und y=" + currentPosition.y + " (" + getDirection(directionX, directionY) + ")");*/

                //Hier andere Aktionen einfügen

                //Letzte Position aktualisieren:

                lastPosition = currentPosition;
            }
        }
    }

    public static void MouseClick() {
        components.Window.frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println("BOGUS");
                if (e.getButton() == MouseEvent.BUTTON1) {
                    System.out.println("BENIS");
                    //Spielerposition abrufen:
                    Point playerPosition = new Point(Player.player.getX() + Player.playerSize / 2,
                            Player.player.getY() + Player.playerSize / 2);

                    //Winkel zwischen Spieler und Mauszeiger berechnen:
                    double angle = getPlayerToMouseAngle(playerPosition);

                    //Geschwindigkeiten für das Projektil berechnen:
                    int projectileSpeed = 30;
                    int speedX = (int) Math.round(Math.cos(angle) * projectileSpeed);
                    int speedY = (int) Math.round(Math.sin(angle) * projectileSpeed);

                    //Projektil erstellen und dem Panel hinzufügen:
                    JLabel projectile = Projectile.createProjectile(playerPosition.x, playerPosition.y);
                    components.Window.frame.getContentPane().add(projectile);
                    System.out.println("[MouseInput.java] Projektil bei x=" + playerPosition.x + " und y=" + playerPosition.y + " eingefügt.");

                    //Timer zum Bewegen des Projektils erstellen:
                    Timer timer = new Timer(1, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //Position des Projektils aktualisieren:
                            int newX = projectile.getX() + speedX;
                            int newY = projectile.getY() + speedY;
                            projectile.setLocation(newX, newY);

                            //Projektil entfernen, wenn es außerhalb des Fensters ist:
                            if (newX < 0 || newX > components.Window.frame.getWidth() || newY < 0 || newY > components.Window.frame.getHeight()) {
                                projectile.setVisible(false);
                                ((Timer) e.getSource()).stop();
                                components.Window.frame.getContentPane().remove(projectile);
                            }
                        }
                    });

                    timer.start();
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

        System.out.println("[MouseInput.java] Projektilwinkel: " + angle);

        return angle;
    }


}
