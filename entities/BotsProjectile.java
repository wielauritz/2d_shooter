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
    BotsProjectile.java
    Schießen von Bots ermöglichen und animieren
    Geschrieben von Eric John und Lauritz Wiebusch
 */

public class BotsProjectile {

    /*
        Erstellt ein Projektil im Spiel
    */

    public static int projectileSize = 20;

    public static JLabel botscreateProjectile(int x, int y) {
        JLabel botsprojectile = new JLabel(new ImageIcon("textures/entities/Projectile/projectile.png"));

        botsprojectile.setBounds(x, y, projectileSize, projectileSize);

        return botsprojectile;
    }

    /*
        Schießt ein Projektil im Spiel
    */

    public static void shootProjectile(JLabel bot) {

        //Aktuelle Spielerposition abrufen:


        Point playerPosition = new Point(Player.player.getX() + Player.size / 2,
                Player.player.getY() + Player.size / 2);

        //Schusswinkel berechnen:

        double angle = Math.atan2(playerPosition.y - bot.getX(), playerPosition.x - bot.getX());

        //Schussgeschwindigkeit berechnen:

        int projectileSpeed = 30;
        double speedX = Math.cos(angle) * projectileSpeed;
        double speedY = Math.sin(angle) * projectileSpeed;

        //Projektil erstellen:

        JLabel botsprojectile = botscreateProjectile(bot.getX(), bot.getY());
        frame.getContentPane().add(botsprojectile);
        frame.getContentPane().setComponentZOrder(botsprojectile, 0);

        //Schusssound abspielen:

        AudioOutput.playSound("audio/entities/Projectile/projectile.wav", 1250);

        Overlay.updateAmmoHUD(1);

        //Timer für Projektilbewegung erstellen:

        Timer projectileTimer = new Timer(1, new ActionListener() {

            private boolean isCollidingWithPlayer(JLabel projectile) {

                Rectangle projectileBox = projectile.getBounds();
                Rectangle playerBox = Player.player.getBounds();

                //Kollision überprüfen:

                return projectileBox.intersects(playerBox);
            }

            public void actionPerformed(ActionEvent e) {

                //Projektilposition verändern:

                double newX = botsprojectile.getX() + speedX;
                double newY = botsprojectile.getY() + speedY;

                botsprojectile.setLocation((int) newX, (int) newY);

                //Kollision mit Spieler überprüfen:

                if (isCollidingWithPlayer(botsprojectile)) {
                    Overlay.updateHealthHUD(3);

                    //Projektil bei Kollision entfernen:

                    botsprojectile.setVisible(false);
                    ((Timer) e.getSource()).stop();
                    frame.getContentPane().remove(botsprojectile);
                    return;
                }

                //Mögliche Kollision mit Hindernissen überprüfen:

                boolean collided = false;

                for (Component obstacle : Player.obstacles) {
                    if (Player.isCollidingWithObstacle(botsprojectile, obstacle, false)) {


                        collided = true;
                        break;
                    }
                }

                //Projektil bei Kollision entfernen:

                if (collided || newX < 0 || newX > 750 || newY < 0 || newY > 750) {
                    botsprojectile.setVisible(false);
                    ((Timer) e.getSource()).stop();
                    frame.getContentPane().remove(botsprojectile);
                } else {
                    botsprojectile.setLocation((int) newX, (int) newY);
                }
            }
        });

        //Timer für Projektilbewegung starten:

        projectileTimer.start();
    }
}
