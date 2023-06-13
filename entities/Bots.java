package entities;

import components.Overlay;
import handlers.AudioOutput;

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
    private static int StartX = 0;
    private static int StartY = 0;
    private ScheduledExecutorService projectileExecutorService; // Added projectileExecutorService

    public JLabel generate(int id) {
        this.id = id; // Set the ID of the bot

        // Create a new Bot
        bots = new JLabel();
        ImageIcon icon = new ImageIcon("textures/entities/Bots/character.png");
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(50, -1, Image.SCALE_SMOOTH));
        bots.setIcon(scaledIcon);

        // Position the Bot in the window
        int x = (StartX - BotsSize - (BotsSize / 2)) / 2;
        int y = (StartY - BotsSize - (BotsSize / 2)) / 2;
        bots.setBounds(x + 12, y, BotsSize, BotsSize);

        if (StartX == 0 && StartY == 0) {
            StartX = 1500;
            StartY = 1500;
        } else if (StartX == 1500 && StartY == 1500) {
            StartX = 0;
            StartY = 750;
        } else if (StartX == 0 && StartY == 750) {
            StartX = 1500;
            StartY = 550;
        } else if (StartX == 1500 && StartY == 550) {
            StartX = 0;
            StartY = 0;
        }

        startBotMovement(); // Start the bot movement
        startShooting(); // Start shooting projectiles

        return bots;
    }

    /*
        Set the obstacle positions
    */
    public static void setObstacles(List<Component> obstaclesList) {
        obstacles = obstaclesList;
    }

    /*
        Check if the Bot collides with an obstacle
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
        // Calculate the new position of the Bot
        int newX = bots.getX() + x;
        int newY = bots.getY() + y;

        bots.setLocation(newX, newY);

        // Prevent the Bot from moving through obstacles

        // Rotate the Bot in the direction of movement
        if (x < 0) {
            // To the left
            bots.setIcon(new ImageIcon(new ImageIcon("textures/entities/Bots/character_inverted.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        } else if (x > 0) {
            // To the right
            bots.setIcon(new ImageIcon(new ImageIcon("textures/entities/Bots/character.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        } else if (y < 0) {
            // Upwards
            bots.setIcon(new ImageIcon(new ImageIcon("textures/entities/Bots/character_flipped.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        } else if (y > 0) {
            // Downwards
            bots.setIcon(new ImageIcon(new ImageIcon("textures/entities/Bots/character.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        }
    }

    private void startBotMovement() {
        executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> {
                int speed = 1;
                int deltaX = player.getX() - bots.getX();
                int deltaY = player.getY() - bots.getY();

                int distance = (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                if (distance < 100) {
                    int directionX = Integer.compare(-deltaX, 0) * speed;
                    int directionY = Integer.compare(-deltaY, 0) * speed;
                    move(directionX, directionY);
                } else {
                    int directionX = Integer.compare(deltaX, 0) * speed;
                    int directionY = Integer.compare(deltaY, 0) * speed;
                    move(directionX, directionY);
                }
            });
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    private void startShooting() {
        projectileExecutorService = Executors.newSingleThreadScheduledExecutor();

        projectileExecutorService.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> {
                int deltaX = player.getX() - bots.getX();
                int deltaY = player.getY() - bots.getY();

                int distance = (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                if (distance <= 200) {
                    BotsProjectile projectile = new BotsProjectile();
                    System.out.println("schuss");
                    // Use the projectile object as needed (e.g., add it to the game, perform collision checks, etc.)
                }
            });
        }, 0, 2, TimeUnit.SECONDS);
    }

    public void deductHealth(int amount) {
        healthPoints -= amount;

        if (healthPoints <= 0) {
            // Bot is destroyed, perform any necessary actions
            destroyBot();
        }
    }

    private void destroyBot() {
        // Perform actions when the bot is destroyed (e.g., remove from the game, update score, etc.)
        frame.getContentPane().remove(bots);
        botsList.remove(this);
        Overlay.updateScoreHUD(50);
    }

    public Rectangle getBounds() {
        return bots.getBounds();
    }

    public int getX() {
        return getX();
    }

    public int getY() {
        return getY();
    }
}
