package components;

import entities.Player;
import handlers.KeyboardInput;

import javax.swing.*;
import java.awt.*;

public class Overlay {

    private static int healthPoints = 100;
    public static JLabel health;

    /*
        Generiert die Lebensanzeige
    */

    public static JLabel createHealthHUD() {
        health = new OutlinedLabel(healthPoints + "/100 HP");
        health.setForeground(Color.WHITE);
        health.setFont(Program.gameFont.deriveFont(24f));
        health.setBounds(0, 0, 200, 25);

        return health;
    }

    /*
        Aktualisiert die Lebensanzeige
     */

    public static JLabel updateHealthHUD(int difference) {

        healthPoints = healthPoints - difference;

        if (healthPoints <= 0) {

            Window.panel.removeAll();
            Window.panel.setBackground(Color.RED);

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
            return health;
    }

    /*
        Setzt einen Schatten hinter die Lebensanzeige
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
