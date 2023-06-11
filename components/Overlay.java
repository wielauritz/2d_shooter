package components;

import algorithms.GameLoop;
import entities.Player;
import handlers.Database;
import handlers.KeyboardInput;
import handlers.MouseInput;

import javax.swing.*;
import java.awt.*;

public class Overlay {

    public static JLabel score;
    public static JLabel health;
    public static JLabel ammo;

    public static int scorePoints = 0;
    public static int healthPoints = 128;
    public static int ammoAmount = 64;

    /*
        Generiert die Lebensanzeige
    */

    public static JLabel createScoreHUD() {
        score = new OutlinedLabel("SCORE: " + scorePoints + "/" + Database.getPlayerHighscore(Player.name));
        score.setForeground(Color.WHITE);
        score.setFont(Program.gameFont.deriveFont(24f));
        score.setBounds(0, 0, 300, 25);

        System.out.println("[Overlay.java] Score-Overlay erfolgreich erstellt.");

        return score;
    }

    /*
        Generiert die Lebensanzeige
    */

    public static JLabel createHealthHUD() {
        health = new OutlinedLabel(healthPoints + "/128 HP");
        health.setForeground(Color.WHITE);
        health.setFont(Program.gameFont.deriveFont(24f));
        health.setBounds(0, 696, 200, 25);

        System.out.println("[Overlay.java] Health-Overlay erfolgreich erstellt.");

        return health;
    }

    /*
        Generiert die Munitionsanzeige
    */

    public static JLabel createAmmoHUD() {
        ammo = new OutlinedLabel(ammoAmount + "/64 AM");
        ammo.setForeground(Color.WHITE);
        ammo.setFont(Program.gameFont.deriveFont(24f));
        ammo.setBounds(545, 696, 200, 25);
        ammo.setHorizontalAlignment(SwingConstants.RIGHT);

        System.out.println("[Overlay.java] Ammo-Overlay erfolgreich erstellt.");

        return ammo;
    }

    /*
        Aktualisiert die Score-Anzeige
     */

    public static void updateScoreHUD(int difference) {

        scorePoints = scorePoints + difference;

        //Überprüfen des Highscores:

        if (scorePoints > Database.getPlayerHighscore(Player.name)) {
            Database.updatePlayerHighscore(Player.name, scorePoints);
        }


        score.setText("SCORE: " + scorePoints + "/" + Database.getPlayerHighscore(Player.name));
        score.repaint();

        System.out.println("[Overlay.java] Score-Overlay erfolgreich aktualisiert.");

    }

    /*
        Aktualisiert die Lebensanzeige
     */

    public static void updateHealthHUD(int difference) {

        healthPoints = healthPoints - difference;

        if (healthPoints <= 0) {

            //Death-Screen-Menü dem Fenster übergeben:

            Window.frame.setContentPane(DeathScreen.create());
            Window.frame.revalidate();
            Window.frame.repaint();

            //Eingaben sperren:

            KeyboardInput.enabled = false;
            MouseInput.enabled = false;

            //Executor stoppen und zurücksetzen:

            Player.shutdownExecutorService();
            GameLoop.shutdownExecutorService();

        } else {
            health.setText(healthPoints + "/128 HP");
            health.repaint();
        }

        System.out.println("[Overlay.java] Health-Overlay erfolgreich aktualisiert.");

    }

    /*
        Aktualisiert die Lebensanzeige
     */

    public static void updateAmmoHUD(int difference) {

        ammoAmount = ammoAmount - difference;

        if (ammoAmount <= 0) {

            //Death-Screen-Menü dem Fenster übergeben:

            Window.frame.setContentPane(DeathScreen.create());
            Window.frame.revalidate();
            Window.frame.repaint();

            //Eingaben sperren:

            KeyboardInput.enabled = false;
            MouseInput.enabled = false;

            //Executor stoppen und zurücksetzen:

            Player.shutdownExecutorService();
            GameLoop.shutdownExecutorService();

        } else {
            ammo.setText(ammoAmount + "/64 AM");
            ammo.repaint();
        }

        System.out.println("[Overlay.java] Ammo-Overlay erfolgreich aktualisiert.");

    }

    /*
        Setzt die Lebensanzeige zurück
    */

    public static void resetHealthHUD() {
        healthPoints = 128;
    }

    /*
        Setzt die Lebensanzeige zurück
    */

    public static void resetAmmoHUD() {
        ammoAmount = 64;
    }

    /*
        Setzt die Score-Anzeige zurück
    */

    public static void resetScoreHUD() {
        scorePoints = 0;
    }

    /*
        Setzt einen Schatten hinter die Anzeigen
    */

    private static class OutlinedLabel extends JLabel {
        private static final long serialVersionUID = 1L;

        public OutlinedLabel(String text) {
            super(text);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //Setzt den Stil des Schattens:

            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.BLACK);

            //Ruft die Textbreite ab:

            int textWidth = g2d.getFontMetrics().stringWidth(getText());
            int textX = 0;

            //Berechnet die Schattenposition mit der Textbreite:

            if (getHorizontalAlignment() == SwingConstants.CENTER) {
                textX = (getWidth() - textWidth) / 2;
            } else if (getHorizontalAlignment() == SwingConstants.RIGHT) {
                textX = getWidth() - textWidth;
            }

            //Zeichnet den Schatten mit definiertem Stil:

            g2d.drawGlyphVector(getFont().createGlyphVector(g2d.getFontRenderContext(), getText()), textX, getFont().getSize());

            super.paintComponent(g2d);

            g2d.dispose();
        }
    }
}