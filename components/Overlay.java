package components;

import handlers.KeyboardInput;

import javax.swing.*;
import java.awt.*;

public class Overlay {

    private static int healthPoints = 100;
    public static JLabel health;

    private static int ammoAmount = 51;
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
        ammo.setBounds(573, 0, 200, 25);

        System.out.println("[Overlay.java] Ammo-Overlay erfolgreich erstellt.");

        return ammo;
    }

    /*
        Aktualisiert die Lebensanzeige
     */

    public static JLabel updateHealthHUD(int difference) {

        healthPoints = healthPoints - difference;

        if (healthPoints <= 0) {

            Window.frame.setContentPane(Blank.panel());
            //Window.panel.setBackground(Color.RED);

            health = new OutlinedLabel("Du bist gestorben!");
            health.setForeground(Color.WHITE);
            health.setFont(Program.gameFont.deriveFont(24f));
            health.setBounds(180, 325, 500, 25);

            Window.panel.add(health);
            Window.panel.repaint();

            KeyboardInput.enabled = false;

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

            Window.frame.setContentPane(Blank.panel());
            //Window.panel.setBackground(Color.RED);

            ammo = new OutlinedLabel("Keine Munition mehr!");
            ammo.setForeground(Color.WHITE);
            ammo.setFont(Program.gameFont.deriveFont(24f));
            ammo.setBounds(180, 325, 500, 25);

            Window.panel.add(ammo);
            Window.panel.repaint();

            KeyboardInput.enabled = false;

        } else {
            ammo.setText(ammoAmount + "/50 AM");
            ammo.repaint();
        }

        System.out.println("[Overlay.java] Ammo-Overlay erfolgreich aktualisiert.");

        return ammo;
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

            //Zeichnet den Schatten:

            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.BLACK);
            g2d.drawGlyphVector(getFont().createGlyphVector(g2d.getFontRenderContext(), getText()), 0, getFont().getSize());

            super.paintComponent(g2d);

            g2d.dispose();
        }
    }
}
