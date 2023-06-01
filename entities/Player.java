package entities;

import javax.swing.*;
import java.awt.*;

public class Player {

    public static JLabel player;
    public static int playerSize = 50;
    public static java.util.List<Component> obstacles;

    /*
        Generiert den Spieler
     */

    public static JLabel generate() {

        //Erzeugt den Spieler:

        player = new JLabel(new ImageIcon("textures/entities/Player/player.png"));

        //Positioniert den Spieler mittig im Fenster:

        int x = (750 - playerSize - (playerSize / 2)) / 2;
        int y = (750 - playerSize - (playerSize / 2)) / 2;

        player.setBounds(x + 12, y, playerSize, playerSize);

        System.out.println("[Player.java] Spieler erfolgreich erstellt.");

        return player;
    }

    /*
        Speichert die Hindernispositionen
     */

    public static void setObstacles(java.util.List<Component> obstaclesList) {
        obstacles = obstaclesList;
    }

    /*
        Überprüft, ob der Spieler mit einem Hindernis in Berührung kommt
    */

    public static boolean isCollidingWithObstacle(Component player, Component obstacle) {
        if (obstacle.getWidth() < 159) {
            Rectangle playerBounds = player.getBounds();
            Rectangle obstacleBounds = obstacle.getBounds();
            Rectangle optimizedBounds = new Rectangle(obstacleBounds.x + 30, obstacleBounds.y + 30, obstacleBounds.width - 40, obstacleBounds.height - 40);
            return playerBounds.intersects(optimizedBounds);
        }
        return false;
    }

    /*
        Überprüft, ob der Spieler sich am Rand befindet
    */

    public static void move(int x, int y) {

        //Neue Position des Spielers berechnen:

        int newX = player.getX() + x;
        int newY = player.getY() + y;

        //Verhindert, dass sich der Spieler aus dem Feld bewegt:

        if (newX >= 0 && newX + playerSize <= 733) {
            player.setLocation(newX, player.getY());
        }

        if (newY >= 0 && newY + playerSize <= 731) {
            player.setLocation(player.getX(), newY);
        }

        //Verhindert, dass sich der Spieler durch Hindernisse bewegt:

        for (Component obstacle : obstacles) {
            if (isCollidingWithObstacle(player, obstacle)) {
                player.setLocation(player.getX() - x, player.getY() - y);
                break;
            }
        }
    }
}
