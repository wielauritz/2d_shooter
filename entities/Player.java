package entities;

import components.Overlay;
import components.Program;
import handlers.AudioOutput;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Player {

    public static JLabel player;

    public static int size = 50;

    public static String name;

    public static JLabel nameTag;

    public static boolean isInWater = false;

    public static java.util.List<Component> obstacles;

    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    /*
        Generiert den Spieler
     */

    public static JLabel generate() {

        //Erzeugt den Spieler:

        player = new JLabel(new ImageIcon("textures/entities/Player/character.png"));

        //Positioniert den Spieler mittig im Fenster:

        int x = (750 - size - (size / 2)) / 2;
        int y = (750 - size - (size / 2)) / 2;

        player.setBounds(x + 12, y, size, size);

        nameTag = new JLabel(name, SwingUtilities.CENTER);
        nameTag.setFont(Program.gameFont.deriveFont(10f));
        nameTag.setForeground(Color.WHITE);

        nameTag.setBounds(x - 19, y - 20, 111, 20);

        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
        // Neuen Executor erstellen:
        executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> {
                for (Component obstacle : obstacles) {
                    Rectangle obstacleBounds = obstacle.getBounds();
                    Rectangle optimizedBounds = new Rectangle(obstacleBounds.x + 30, obstacleBounds.y + 20, obstacleBounds.width - 60, obstacleBounds.height - 60);
                    if (obstacle.getBounds().width == 160 && player.getBounds().intersects(optimizedBounds)) {
                        Overlay.updateHealthHUD(1);
                        isInWater = true;
                    }
                }
            });
        }, 0, 100, TimeUnit.MILLISECONDS);

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

    public static boolean isCollidingWithObstacle(Component player, Component obstacle, boolean isPlayer) {
        Rectangle playerBounds = player.getBounds();
        Rectangle obstacleBounds = obstacle.getBounds();
        Rectangle waterBounds = new Rectangle(obstacleBounds.x + 30, obstacleBounds.y + 20, obstacleBounds.width - 60, obstacleBounds.height - 60);
        Rectangle treeBounds = new Rectangle(obstacleBounds.x + 30, obstacleBounds.y + 30, obstacleBounds.width - 40, obstacleBounds.height - 40);

        //Wenn der Spieler mit Wasser in Berührung kommt Schaden hinzufügen:

        if (obstacle.getWidth() == 160 && isPlayer) {
            if (playerBounds.intersects(waterBounds)) {
                Overlay.updateHealthHUD(1);
                if (!isInWater) {
                    AudioOutput.playSound("audio/entities/Obstacles/water.wav", 2000);
                    isInWater = true;
                }
                return false;
            } else {
                isInWater = false;
            }

        //Spieler nicht durch Bäume laufen lassen:

        } else if (obstacle.getWidth() == 100) {
            if (playerBounds.intersects(treeBounds)) {
                //isInWater = false;
                return true;
            }
        } else {
            isInWater = false;
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

        if (newX >= 0 && newX + size <= 745) {
            player.setLocation(newX, player.getY());
        }

        if (newY >= 0 && newY + size <= 718) {
            player.setLocation(player.getX(), newY);
        }

        //Verhindert, dass sich der Spieler durch Hindernisse bewegt:

        for (Component obstacle : obstacles) {
            if (isCollidingWithObstacle(player, obstacle, true)) {
                player.setLocation(player.getX() - x, player.getY() - y);
                break;
            }
        }

        //Dreht den Spieler in Laufrichtung:

        if (x < 0) {
            // Nach links:
            player.setIcon(new ImageIcon("textures/entities/Player/character_flipped.png"));
        } else if (x > 0) {
            // Nach rechts:
            player.setIcon(new ImageIcon("textures/entities/Player/character.png"));
        } else if (y < 0) {
            //Nach oben:
            player.setIcon(new ImageIcon("textures/entities/Player/character_inverted.png"));
        } else if (y > 0) {
            //Nach unten:
            player.setIcon(new ImageIcon("textures/entities/Player/character.png"));
        }


        nameTag.setLocation(newX - 29, player.getY() - 20);
    }

    public static void shutdownExecutorService() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
