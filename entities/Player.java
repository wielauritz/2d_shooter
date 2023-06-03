package entities;

import components.Overlay;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Player {

    public static JLabel player;
    public static int playerSize = 50;
    public static java.util.List<Component> obstacles;
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

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

        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
        // Create a new executor
        executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> {
                for (Component obstacle : obstacles) {
                    if (obstacle.getBounds().width == 160 && player.getBounds().intersects(obstacle.getBounds())) {
                        Overlay.updateHealthHUD(1);
                    }
                }
            });
        }, 0, 100, TimeUnit.MILLISECONDS);

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
        Rectangle optimizedBounds = new Rectangle(obstacleBounds.x + 30, obstacleBounds.y + 30, obstacleBounds.width - 40, obstacleBounds.height - 40);

        if (obstacle.getWidth() == 160) { // It's water
            if (playerBounds.intersects(optimizedBounds)) {
                Overlay.updateHealthHUD(1);
                return false; // The player can move over the water but take damage
            }
        } else if (obstacle.getWidth() == 100) { // It's a tree
            if (playerBounds.intersects(optimizedBounds)) {
                return true; // The player cannot move over a tree
            }
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

    public static void shutdownExecutorService() {
        if (executorService != null) {
            executorService.shutdown();
        }
        System.out.println("stop");
    }
}
