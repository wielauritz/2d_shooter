package entities;

import javax.swing.*;
import java.awt.*;

import static entities.Player.player;

public class Bots {

    public static JLabel bots;
    public static int BotsSize = 50;
    public static java.util.List<Component> obstacles;
    private int id;

    /*
        Generiert die Bots
     */

    public JLabel generate(int id) {
        this.id = id; // Legt die ID des Bots fest

        //Erzeugt den Spieler:

        bots = new JLabel(new ImageIcon("textures/entities/Player/player.png"));

        //Positioniert den Spieler mittig im Fenster:

        int x = (650 - BotsSize - (BotsSize / 2)) / 2;
        int y = (650 - BotsSize - (BotsSize / 2)) / 2;

        bots.setBounds(x + 12, y, BotsSize, BotsSize);

        System.out.println("[bots.java] Spieler erfolgreich erstellt.");

        return bots;
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

    public static boolean isCollidingWithObstacle(Component Bots, Component obstacle) {
        if (obstacle.getWidth() < 159) {
            Rectangle BotsBounds = Bots.getBounds();
            Rectangle obstacleBounds = obstacle.getBounds();
            Rectangle optimizedBounds = new Rectangle(obstacleBounds.x + 30, obstacleBounds.y + 30, obstacleBounds.width - 40, obstacleBounds.height - 40);
            return BotsBounds.intersects(optimizedBounds);
        }
        return false;
    }

    /*
        Überprüft, ob der Spieler sich am Rand befindet
    */

    public static void move(int x, int y) {

        //Neue Position des Spielers berechnen:

        int newX = bots.getX() + x;
        int newY = bots.getY() + y;

        //Verhindert, dass sich der Spieler aus dem Feld bewegt:

        if (newX >= 0 && newX + BotsSize <= 733) {
            bots.setLocation(newX, bots.getY());
        }

        if (newY >= 0 && newY + BotsSize <= 731) {
            bots.setLocation(bots.getX(), newY);
        }

        //Verhindert, dass sich der Spieler durch Hindernisse bewegt:

        for (Component obstacle : obstacles) {
            if (isCollidingWithObstacle(bots, obstacle)) {
                bots.setLocation(player.getX() - x, bots.getY() - y);
                break;
            }
        }
    }

    //Lebenssystem 
    public static int healthPoints = 100;
    public static JLabel Botshealth;

}
