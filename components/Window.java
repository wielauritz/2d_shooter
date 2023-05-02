package components;

import entities.Player;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Window {

    public static JFrame frame;

    /*
        Erstellt das Programm-Fenster
    */

    public Window() {
        frame = new JFrame("2D Shooter");
        frame.setSize(750, 750);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Setzt ein Hintergrundbild und setzt den Spieler obendrauf:

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    try {
                        Image image = ImageIO.read(new File("textures/floor_v3.png"));
                        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };

            panel.setLayout(null);
            panel.add(Player.generate());

            System.out.println("[Window.java] Fenster erfolgreich erstellt.");

        frame.setContentPane(panel);
    }

    /*
        Öffnet das Programmfenster
    */

    public void open() {
        frame.setVisible(true);
    }
}
