package components;

import handlers.KeyboardInput;
import handlers.MouseInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import static handlers.KeyboardInput.playerMoveTimer;

public class DeathScreen {

    public static JLabel playButton;
    public static JLabel settingsButton;
    public static JLabel closeButton;


    /*
        Erstellt das Death-Screen-Menü
     */
    public static JPanel create() {

        JPanel panel = new JPanel();

        panel.setLayout(null);
        panel.setBackground(Color.RED);

        //Erstellt den Game-Over-Text:

        JLabel info = new JLabel("GAME OVER!", SwingConstants.CENTER);
        info.setBounds(0, 100, 750, 50);
        info.setFont(Program.gameFont.deriveFont(48f));
        info.setForeground(Color.WHITE);
        panel.add(info);

        //Erstellt den Spielen-Button:

        playButton = new JLabel("Neustarten", SwingConstants.CENTER);
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


         /*
            Mausklick im Death-Screen-Menü registrieren:
        */

        playButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("[MouseInput.java] Spielfeld wird erstellt, bitte warten...");

                //Spielfeld dem Fenster übergeben:

                Window.frame.removeAll();
                Window.frame.add(Game.create());
                Window.frame.revalidate();
                Window.frame.repaint();


                KeyboardInput.spacePressed = true;

                MouseInput.started = true;

                //Tastendruck simulieren, um das Fenster zu aktualisieren:


                System.out.println("[MouseInput.java] Spielfeld erfolgreich geladen.");

            }
        });

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("[MouseInput.java] Spiel wird beendet, bitte warten...");

                //Fenster schließen und Timer stoppen, damit das Spiel beendet werden kann:

                Window.frame.dispose();
                if (playerMoveTimer != null) {
                    playerMoveTimer.stop();
                }
                MouseInput.running = false;
            }
        });


        System.out.println("[DeathScreen.java] Death-Screen erfolgreich erstellt.");

        return panel;
    }


}
