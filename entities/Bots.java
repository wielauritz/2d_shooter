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

/*
    Bots.java
    Erstellen und aktualisieren der Bots hinsichtlich Texturen und Bewegung
    Geschrieben von Eric John und Lauritz Wiebusch
 */

public class Bots {
    public JLabel bots;
    private static int BotsSize = 50;
    private static List<Component> obstacles;
    private int healthPoints = 100;
    private int id;
    private ScheduledExecutorService executorService;
    private static int StartX = 0;
    private static int StartY = 0;
    public static ScheduledExecutorService projectileExecutorService;

    public int direction = 2;

    public JLabel generate(int id) {
        this.id = id;

        //Neuen Bot erstellen:

        bots = new JLabel();
        ImageIcon icon = new ImageIcon("textures/entities/Bots/character.png");
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(50, -1, Image.SCALE_SMOOTH));
        bots.setIcon(scaledIcon);

        //Bots positionieren:

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

        startBotMovement();
        startShooting();


        return bots;
    }

    /*
        Hindernispositionen setzen
    */
    public static void setObstacles(List<Component> obstaclesList) {
        obstacles = obstaclesList;
    }

    /*
        Kollision mit Hindernissen überprüfen
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

        //Neue Position des Bots berechnen:

        int newX = bots.getX() + x;
        int newY = bots.getY() + y;

        bots.setLocation(newX, newY);

        //Dreht den Bot in Laufrichtung:

        if (x < 0) {
            //Nach links:
            bots.setIcon(new ImageIcon(new ImageIcon("textures/entities/Bots/character_inverted.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            direction = 3;
        } else if (x > 0) {
            //Nach rechts:
            bots.setIcon(new ImageIcon(new ImageIcon("textures/entities/Bots/character.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            direction = 1;
        } else if (y < 0) {
            //Nach oben:
            bots.setIcon(new ImageIcon(new ImageIcon("textures/entities/Bots/character_flipped.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            direction = 0;
        } else if (y > 0) {
            //Nach unten:
            bots.setIcon(new ImageIcon(new ImageIcon("textures/entities/Bots/character.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            direction = 2;
        }
    }

    private void startBotMovement() {
        executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> {
                int speed = 2;
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
                    BotsProjectile.shootProjectile(bots);
                }
            });
        }, 0, 2, TimeUnit.SECONDS);
    }

    public void deductHealth(int amount) {
        healthPoints -= amount;

        if (healthPoints <= 0) {
            destroyBot();
        }
    }

    public void destroyBot() {
        frame.getContentPane().remove(bots);
        botsList.remove(this);
        Overlay.updateScoreHUD(50);
    }

    public void setIcon(ImageIcon icon) {
        bots.setIcon(icon);
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

    public int getDirection() {
        return direction;
    }
}
