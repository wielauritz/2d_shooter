package components;

import algorithms.GameLoop;
import handlers.KeyboardInput;
import handlers.MouseInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import static algorithms.GameLoop.playerMoveTimer;

public class TitleScreen {

    public static JLabel playButton;
    public static JLabel settingsButton;
    public static JLabel closeButton;


    /*
        Erstellt das Hauptmenü
     */
    public static JPanel create() {

        JPanel panel = new JPanel() {

            //Setzt das Hintergrundbild und den Titel für das Hauptmenü:

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    ImageIcon gifIcon = new ImageIcon("textures/components/TitleScreen/titlescreen.gif");
                    Image gifImage = gifIcon.getImage();
                    Image title = ImageIO.read(new File("textures/components/TitleScreen/title.png"));
                    g.drawImage(gifImage, -200, 0, getWidth() + 400, getHeight(), this);
                    g.drawImage(title, 100, 63, 544, 75, this);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };

        panel.setLayout(null);

        //Erstellt den Spielen-Button:

        playButton = new JLabel("Spielen", SwingConstants.CENTER);
        playButton.setBounds(170, 342, 400, 50);
        playButton.setFont(Program.gameFont.deriveFont(24f));
        playButton.setOpaque(true);
        playButton.setFocusable(false);
        panel.add(playButton);

        //Erstellt den Einstellungen-Button:

        settingsButton = new JLabel("Einstellungen", SwingConstants.CENTER);
        settingsButton.setBounds(170, 412, 400, 50);
        settingsButton.setFont(Program.gameFont.deriveFont(24f));
        settingsButton.setOpaque(true);
        settingsButton.setFocusable(false);
        panel.add(settingsButton);

        //Erstellt den Beenden-Button:

        closeButton = new JLabel("Beenden", SwingConstants.CENTER);
        closeButton.setBounds(170, 482, 400, 50);
        closeButton.setFont(Program.gameFont.deriveFont(24f));
        closeButton.setOpaque(true);
        closeButton.setFocusable(false);
        panel.add(closeButton);

        //Erstellt die Informationen:

        JLabel info = new JLabel("© Eric John & Lauritz Wiebusch | BETA-Version (!)", SwingConstants.CENTER);
        info.setBounds(0, 675, 750, 50);
        info.setFont(Program.gameFont.deriveFont(12f));
        info.setForeground(Color.WHITE);
        panel.add(info);

        /*
            Mausklick im Hauptmenü registrieren:
        */

        playButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("[Menu.java] Spielfeld wird erstellt, bitte warten...");

                //Timer stoppen, damit sich das Spielertempo nicht verdoppelt:

                if (playerMoveTimer != null) {
                    playerMoveTimer.stop();
                }
                GameLoop.timerRunning = false;

                //Spielfeld dem Fenster übergeben:

                Window.frame.setContentPane(Game.create());
                Window.frame.revalidate();

                //Eingaben aktivieren:

                KeyboardInput.enabled = true;

                MouseInput.enabled = true;

                System.out.println("[Menu.java] Spielfeld erfolgreich geladen.");

            }
        });

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("[Menu.java] Spiel wird beendet, bitte warten...");

                //Fenster schließen:

                Window.frame.dispose();

                //Timer stoppen, damit das Spiel beendet werden kann:

                if (playerMoveTimer != null) {
                    playerMoveTimer.stop();
                }
                GameLoop.timerRunning = false;
            }
        });

        System.out.println("[Menu.java] Hauptmenü erfolgreich erstellt.");

        return panel;
    }

}
