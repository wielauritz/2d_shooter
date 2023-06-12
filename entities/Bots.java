package entities;

import components.Game;

import javax.swing.*;
import java.awt.*;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static components.Game.botsList;
import static components.Window.frame;
import static entities.Player.player;

public class Bots {

    private JLabel bots;
    private static int BotsSize = 50;
    private static List<Component> obstacles;
    private int healthPoints = 100;
    private int id;
    private ScheduledExecutorService executorService; // Added executorService

    public JLabel generate(int id) {
        this.id = id; // Set the ID of the bot

        // Erzeugt einen neuen Bot:
        bots = new JLabel(new ImageIcon("textures/entities/Player/player.png"));

        // Positioniert den Bot im Fenster:
        int x = (650 - BotsSize - (BotsSize / 2)) / 2;
        int y = (650 - BotsSize - (BotsSize / 2)) / 2;
        bots.setBounds(x + 12, y, BotsSize, BotsSize);

        System.out.println("[Bots.java] Bot " + id + " erfolgreich erstellt.");

        startBotMovement(); // Start the bot movement

        return bots;
    }

    /*
        Speichert die Hindernispositionen
    */
    public static void setObstacles(List<Component> obstaclesList) {
        obstacles = obstaclesList;
    }

    /*
        Überprüft, ob der Spieler mit einem Hindernis in Berührung kommt
    */
    public boolean isCollidingWithObstacle(Component bot, Component obstacle) {
        if (obstacle.getWidth() < 159) {
            Rectangle botBounds = bot.getBounds();
            Rectangle obstacleBounds = obstacle.getBounds();
            Rectangle optimizedBounds = new Rectangle(obstacleBounds.x + 30, obstacleBounds.y + 30, obstacleBounds.width - 40, obstacleBounds.height - 40);
            return botBounds.intersects(optimizedBounds);
        }
        return false;
    }

    public void move(int x, int y) {
        // Neue Position des Bots berechnen:
        int newX = bots.getX() + x;
        int newY = bots.getY() + y;

        // Verhindert, dass sich der Bot aus dem Feld bewegt:
        if (newX >= 0 && newX + BotsSize <= 733) {
            bots.setLocation(newX, bots.getY());
        }

        if (newY >= 0 && newY + BotsSize <= 731) {
            bots.setLocation(bots.getX(), newY);
        }

        // Verhindert, dass sich der Bot durch Hindernisse bewegt:
        for (Component obstacle : obstacles) {
            if (isCollidingWithObstacle(bots, obstacle)) {
                bots.setLocation(player.getX() - x, bots.getY() - y);
                break;
            }
        }
    }

    private void startBotMovement() {
        executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> {
                int directionX = 0;
                int directionY = 0;
                int speed = 5;

                if (player.getX() < bots.getX()) {
                    directionX -= speed;
                } else if (player.getY() < bots.getY()) {
                    directionY -= speed;
                } else if (player.getX() > bots.getX()) {
                    directionX += speed;
                } else if (player.getY() > bots.getY()) {
                    directionY += speed;
                }

                if (directionX != 0 || directionY != 0) {
                    move(directionX, directionY);
                }
            });
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void deductHealth(int amount) {
        healthPoints -= amount;
        if (healthPoints <= 0) {
            // Bot is destroyed, perform any necessary actions
            destroyBot();
        }
    }

    private void destroyBot() {
        // Perform actions when the bot is destroyed (e.g., remove from game, update score, etc.)
        frame.getContentPane().remove(bots);
        botsList.remove(this);
    }

    public Rectangle getBounds() {
        return bots.getBounds();
    }

}
