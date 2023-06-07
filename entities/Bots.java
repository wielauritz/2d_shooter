package entities;

import javax.swing.*;
import java.awt.*;

import static entities.Player.player;

public class Bots {

    public static JLabel bots;
    public static int BotsSize = 50;

    public static java.util.List<Component> obstacles;

    public static int healthPoints = 100;

    /*
        Generiert die Bots
    */

    public static JLabel Botshealth;

    /*
        Speichert die Hindernispositionen
    */

    private int id;

    /*
        Überprüft, ob der Spieler mit einem Hindernis in Berührung kommt
    */

    public static void setObstacles(java.util.List<Component> obstaclesList) {
        obstacles = obstaclesList;
    }

    /*
        Überprüft, ob der Spieler sich am Rand befindet
    */

    public static boolean isCollidingWithObstacle(Component Bots, Component obstacle) {
        if (obstacle.getWidth() < 159) {
            Rectangle BotsBounds = Bots.getBounds();
            Rectangle obstacleBounds = obstacle.getBounds();
            Rectangle optimizedBounds = new Rectangle(obstacleBounds.x + 30, obstacleBounds.y + 30, obstacleBounds.width - 40, obstacleBounds.height - 40);
            return BotsBounds.intersects(optimizedBounds);
        }
        return false;
    }

    public static void move(int x, int y) {

        //Neue Position des Bots berechnen:

        int newX = bots.getX() + x;
        int newY = bots.getY() + y;

        //Verhindert, dass sich der Bot aus dem Feld bewegt:

        if (newX >= 0 && newX + BotsSize <= 733) {
            bots.setLocation(newX, bots.getY());
        }

        if (newY >= 0 && newY + BotsSize <= 731) {
            bots.setLocation(bots.getX(), newY);
        }

        //Verhindert, dass sich der Bot durch Hindernisse bewegt:

        for (Component obstacle : obstacles) {
            if (isCollidingWithObstacle(bots, obstacle)) {
                bots.setLocation(player.getX() - x, bots.getY() - y);
                break;
            }
        }
    }

    public JLabel generate(int id) {
        this.id = id; // Legt die ID des Bots fest

        //Erzeugt einen neuen Bot:

        bots = new JLabel(new ImageIcon("textures/entities/Player/player.png"));

        //Positioniert den Bot im Fenster:

        int x = (650 - BotsSize - (BotsSize / 2)) / 2;
        int y = (650 - BotsSize - (BotsSize / 2)) / 2;

        bots.setBounds(x + 12, y, BotsSize, BotsSize);

        System.out.println("[Bots.java] Bot erfolgreich erstellt.");

        return bots;
    }

}
