package entities;

import components.Game;
import components.Overlay;
import handlers.AudioOutput;
import handlers.Database;
import handlers.MouseInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static components.Game.botsList;
import static components.Window.frame;

/*
    PlayerProjectile.java
    Schießen von Spielern ermöglichen und animieren
    Geschrieben von Eric John und Lauritz Wiebusch
 */

public class PlayerProjectile {

    /*
        Erstellt ein Projektil im Spiel
    */

    public static int projectileSize = 20;

    public static JLabel createProjectile(int x, int y) {
        JLabel projectile = new JLabel(new ImageIcon("textures/entities/Projectile/projectile.png"));

        projectile.setBounds(x, y, projectileSize, projectileSize);

        return projectile;
    }

    /*
        Schießt ein Projektil im Spiel
    */

    public static void shootProjectile() {

        //Aktuelle Spielerposition abrufen:

        Point playerPosition = new Point(Player.player.getX() + Player.size / 2,
                Player.player.getY() + Player.size / 2);

        //Schusswinkel berechnen:

        double angle = MouseInput.playerToMouseAngle(playerPosition);

        //Schussgeschwindigkeit berechnen:

        int projectileSpeed = 30;
        double speedX = Math.cos(angle) * projectileSpeed;
        double speedY = Math.sin(angle) * projectileSpeed;

        //Projektil erstellen:

        JLabel projectile = createProjectile(playerPosition.x, playerPosition.y);
        frame.getContentPane().add(projectile);
        frame.getContentPane().setComponentZOrder(projectile, 0);

        //Schusssound abspielen:

        AudioOutput.playSound("audio/entities/Projectile/projectile.wav", 1250);

        //Munitonsanzeige aktualisieren:

        Overlay.updateAmmoHUD(1);

        //Timer für Projektilbewegung erstellen:

        Timer projectileTimer = new Timer(1, new ActionListener() {

            private boolean isCollidingWithBot(JLabel projectile, Bots bot) {

                Rectangle projectileBox = projectile.getBounds();
                Rectangle botBox = bot.getBounds();

                // Check for collision using bounding boxes
                return projectileBox.intersects(botBox);
            }

            public void actionPerformed(ActionEvent e) {

                //Projektilposition verändern:

                double newX = projectile.getX() + speedX;
                double newY = projectile.getY() + speedY;

                projectile.setLocation((int) newX, (int) newY);

                //Mögliche Kollision mit Hindernissen überprüfen:

                boolean collided = false;


                for (Component obstacle : Player.obstacles) {
                    if (Player.isCollidingWithObstacle(projectile, obstacle, false)) {

                        //Explosion anzeigen, wenn das Hindernis ein Fass ist:

                        if (obstacle.getWidth() == 64) {

                            JLabel explosion = new JLabel();
                            ImageIcon gifIcon = new ImageIcon("textures/entities/Obstacles/explosion.gif");
                            ImageIcon scaledIcon = new ImageIcon(gifIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

                            explosion.setIcon(scaledIcon);
                            explosion.setVisible(true);
                            explosion.setBounds(obstacle.getX() - 20, obstacle.getY() - 10, 100, 100);
                            frame.getContentPane().add(explosion);
                            frame.revalidate();

                            //Explosionsgeräusch abspielen:

                            AudioOutput.playSound("audio/entities/Projectile/explosion.wav", 3000);

                            //Das Hindernis entfernen, nachdem es explodiert ist:

                            obstacle.setVisible(false);
                            Game.obstaclesList.remove(obstacle);
                            Player.setObstacles(Game.obstaclesList);
                            Bots.setObstacles(Game.obstaclesList);
                            frame.getContentPane().remove(obstacle);

                            //Munition aufstocken:

                            Overlay.updateAmmoHUD(Overlay.ammoAmount - 64);

                            //Punktestand erhöhen:

                            Overlay.updateScoreHUD(15);
                            Database.updatePlayerLastScore(Player.name, Database.getPlayerLastScore(Player.name) + 15);

                            //Die Explosionsanimation stoppen:

                            Timer timer = new Timer(1000, ev -> {
                                frame.getContentPane().remove(explosion);
                                frame.repaint();
                                frame.revalidate();
                            });
                            timer.setRepeats(false);
                            timer.start();

                        } else if (obstacle.getWidth() == 100) {
                            ImageIcon icon = new ImageIcon("textures/entities/Obstacles/tree_burned.png");
                            ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH));
                            ((JLabel) obstacle).setIcon(scaledIcon);
                        }

                        collided = true;
                        break;
                    }
                }
                for (Bots bot : botsList) {
                    if (isCollidingWithBot(projectile, bot)) {
                        if (bot.getDirection() == 0) {
                            bot.setIcon(new ImageIcon(new ImageIcon("textures/entities/Bots/character_flipped_shot.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                        } else if (bot.getDirection() == 3) {
                            bot.setIcon(new ImageIcon(new ImageIcon("textures/entities/Bots/character_inverted_shot.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                        } else {
                            bot.setIcon(new ImageIcon(new ImageIcon("textures/entities/Bots/character_flipped_shot.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                        }
                        frame.revalidate();
                        collided = true;
                        bot.deductHealth(20);
                        break;
                    }
                }

                //Projektil bei Kollision entfernen:

                if (collided || newX < 0 || newX > 750 || newY < 0 || newY > 750) {
                    projectile.setVisible(false);
                    ((Timer) e.getSource()).stop();
                    frame.getContentPane().remove(projectile);

                } else {
                    projectile.setLocation((int) newX, (int) newY);
                }
            }
        });

        //Timer für Projektilbewegung starten:

        projectileTimer.start();
    }
}
