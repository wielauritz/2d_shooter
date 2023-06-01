package components;

import handlers.KeyboardInput;
import handlers.MouseInput;

import javax.swing.*;
import java.awt.*;

public class Overlay {

    private static int healthPoints = 100;
    public static JLabel health;

    private static int ammoAmount = 50;
    public static JLabel ammo;

    /*
        Generiert die Lebensanzeige
    */

    public static JLabel createHealthHUD() {
        health = new OutlinedLabel(healthPoints + "/100 HP");
        health.setForeground(Color.WHITE);
        health.setFont(Program.gameFont.deriveFont(24f));
        health.setBounds(0, 0, 200, 25);

        System.out.println("[Overlay.java] Health-Overlay erfolgreich erstellt.");

        return health;
    }

    /*
        Generiert die Munitionsanzeige
    */

    public static JLabel createAmmoHUD() {
        ammo = new OutlinedLabel(ammoAmount + "/50 AM");
        ammo.setForeground(Color.WHITE);
        ammo.setFont(Program.gameFont.deriveFont(24f));
        ammo.setBounds(545, 0, 200, 25);
        ammo.setHorizontalAlignment(SwingConstants.RIGHT);

        System.out.println("[Overlay.java] Ammo-Overlay erfolgreich erstellt.");

        return ammo;
    }

    /*
        Aktualisiert die Lebensanzeige
     */

    public static JLabel updateHealthHUD(int difference) {

        healthPoints = healthPoints - difference;

        if (healthPoints <= 0) {

            //Death-Screen-Menü dem Fenster übergeben:

            Window.frame.setContentPane(DeathScreen.create());
            Window.frame.revalidate();
            Window.frame.repaint();

            //Eingaben sperren:

            KeyboardInput.enabled = true;

            MouseInput.enabled = true;

        } else {
            health.setText(healthPoints + "/100 HP");
            health.repaint();
        }

        System.out.println("[Overlay.java] Health-Overlay erfolgreich aktualisiert.");

        return health;
    }

    /*
        Aktualisiert die Lebensanzeige
     */

    public static JLabel updateAmmoHUD(int difference) {

        ammoAmount = ammoAmount - difference;

        if (ammoAmount <= 0) {

            //Death-Screen-Menü dem Fenster übergeben:

            Window.frame.setContentPane(DeathScreen.create());
            Window.frame.revalidate();
            Window.frame.repaint();

            //Eingaben sperren:

            KeyboardInput.enabled = true;

            MouseInput.enabled = true;

        } else {
            ammo.setText(ammoAmount + "/50 AM");
            ammo.repaint();
        }

        System.out.println("[Overlay.java] Ammo-Overlay erfolgreich aktualisiert.");

        return ammo;
    }

    /*
        Setzt die Lebensanzeige zurück
    */

    public static void resetHealthHUD() {

        healthPoints = 100;

    }

    /*
        Setzt die Lebensanzeige zurück
    */

    public static void resetAmmoHUD() {

        ammoAmount = 50;

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
