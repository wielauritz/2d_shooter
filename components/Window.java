package components;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Window {

    private JFrame frame;
    private JLabel player;

    /*
        Erstellt das Programm-Fenster
    */

    public Window() {
        frame = new JFrame("2D Shooter");
        frame.setSize(750, 750);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Erstellt den Spieler:

            player = new JLabel(new ImageIcon("textures/player.png"));

            //Positioniert den Spieler mittig im Fenster:

            int playerSize = 50;

            int x = (frame.getWidth() - playerSize - (playerSize / 2)) / 2;
            int y = (frame.getHeight() - playerSize - (playerSize / 2)) / 2;

            player.setBounds(x, y, playerSize, playerSize);

            //Setzt ein Hintergrundbild und setzt den Spieler oben drauf:

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    try {
                        Image image = ImageIO.read(new File("textures/floor.png"));
                        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };

            panel.setLayout(null);
            panel.add(player);

        frame.setContentPane(panel);
    }

    /*
        Öffnet das Programmfenster
    */

    public void open() {
        frame.setVisible(true);
    }
}