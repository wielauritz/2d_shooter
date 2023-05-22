package components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Menu {

    public static JLabel playButton;
    public static JLabel settingsButton;
    public static JLabel closeButton;


    /*
        Erstellt das Spielmenü
     */
    public static JPanel create() {

        JPanel panel = new JPanel() {

            //Setzt das Hintergrundbild und den Titel für das Hauptmenü:

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image image = ImageIO.read(new File("textures/titlescreen.png"));
                    Image image2 = ImageIO.read(new File("textures/title.png"));
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                    g.drawImage(image2, 100, 100, 544, 75, this);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };

        panel.setLayout(null);

        //Erstellt den Spielen-Button:

        playButton = new JLabel("Spielen", SwingConstants.CENTER);
        playButton.setBounds(170, 300, 400, 50);
        playButton.setFont(Program.gameFont.deriveFont(24f));
        playButton.setOpaque(true);
        playButton.setFocusable(false);
        panel.add(playButton);

        //Erstellt den Einstellungen-Button:

        settingsButton = new JLabel("Einstellungen", SwingConstants.CENTER);
        settingsButton.setBounds(170, 370, 400, 50);
        settingsButton.setFont(Program.gameFont.deriveFont(24f));
        settingsButton.setOpaque(true);
        settingsButton.setFocusable(false);
        panel.add(settingsButton);

        //Erstellt den Beenden-Button:

        closeButton = new JLabel("Beenden", SwingConstants.CENTER);
        closeButton.setBounds(170, 440, 400, 50);
        closeButton.setFont(Program.gameFont.deriveFont(24f));
        closeButton.setOpaque(true);
        closeButton.setFocusable(false);
        panel.add(closeButton);

        //Erstellt die Informationen:

        JLabel info = new JLabel("© Eric John, Tim Dreyer & Lauritz Wiebusch | BETA-Version (!)", SwingConstants.CENTER);
        info.setBounds(0, 675, 750, 50);
        info.setFont(Program.gameFont.deriveFont(12f));
        info.setForeground(Color.WHITE);
        panel.add(info);

        System.out.println("[Menu.java] Hauptmenü erfolgreich erstellt.");

        return panel;
    }

}
