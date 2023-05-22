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

        player = new JLabel(new ImageIcon("textures/player.png"));

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
        Rectangle playerBounds = player.getBounds();
        Rectangle obstacleBounds = obstacle.getBounds();
        Rectangle optimizedBounds = new Rectangle(obstacleBounds.x + 10, obstacleBounds.y + 10, obstacleBounds.width - 20, obstacleBounds.height - 20);
        return playerBounds.intersects(optimizedBounds);
    }

    /*
        Überprüft, ob der Spieler sich am Rand befindet
    */

    public static void move(int x, int y) {
        /*System.out.println("[Player.java] Spieler bewegt: x=" + player.getX() + " und y=" + player.getY());*/

        int newX = player.getX() + x;
        int newY = player.getY() + y;

        //Verhindert, dass sich der Spieler aus dem Feld bewegt:

        if (newX >= 0 && newX + playerSize <= 733) {
            player.setLocation(newX, player.getY());
        }

        if (newY >= 0 && newY + playerSize <= 711) {
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
