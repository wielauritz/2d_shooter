package entities;

import components.Overlay;

import javax.swing.*;
import java.awt.*;
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
        bots = new JLabel();
        ImageIcon icon = new ImageIcon("textures/entities/Bots/character.png");
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(50, -1, Image.SCALE_SMOOTH));
        bots.setIcon(scaledIcon);

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
        Überprüft, ob der Bot mit einem Hindernis in Berührung kommt
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

        //Dreht den Spieler in Laufrichtung:

        if (x < 0) {
            // Nach links:
            bots.setIcon(new ImageIcon(new ImageIcon("textures/entities/Bots/character_inverted.png").getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH)));
        } else if (x > 0) {
            // Nach rechts:
            bots.setIcon(new ImageIcon(new ImageIcon("textures/entities/Bots/character.png").getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH)));
        } else if (y < 0) {
            //Nach oben:
            bots.setIcon(new ImageIcon(new ImageIcon("textures/entities/Bots/character_flipped.png").getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH)));
        } else if (y > 0) {
            //Nach unten:
            bots.setIcon(new ImageIcon(new ImageIcon("textures/entities/Bots/character.png").getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH)));
        }
    }

    private void startBotMovement() {
        executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> {
                int speed = 1;
                int deltaX = player.getX() - bots.getX();
                int deltaY = player.getY() - bots.getY();

                int directionX = Integer.compare(deltaX, 0) * speed;
                int directionY = Integer.compare(deltaY, 0) * speed;

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
        Overlay.updateScoreHUD(50);
    }

    public Rectangle getBounds() {
        return bots.getBounds();
    }

}
