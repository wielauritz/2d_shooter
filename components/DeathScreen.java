package components;

import handlers.KeyboardInput;
import handlers.MouseInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static handlers.KeyboardInput.playerMoveTimer;

public class DeathScreen {

    public static JLabel playButton;
    public static JLabel backButton;
    public static JLabel closeButton;


    /*
        Erstellt das Death-Screen-Menü
     */
    public static JPanel create() {

        JPanel panel = new JPanel() {

            //Setzt das Hintergrundbild und den Titel für das Hauptmenü:

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon gifIcon = new ImageIcon("textures/components/DeathScreen/deathscreen.gif");
                Image gifImage = gifIcon.getImage();
                g.drawImage(gifImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setLayout(null);
        panel.setBackground(Color.RED);

        //Erstellt den Game-Over-Text:

        JLabel info = new JLabel("GAME OVER!", SwingConstants.CENTER);
        info.setBounds(0, 100, 750, 50);
        info.setFont(Program.gameFont.deriveFont(48f));
        info.setForeground(Color.WHITE);
        panel.add(info);

        //Erstellt den Spielen-Button:

        playButton = new JLabel("Neu starten", SwingConstants.CENTER);
        playButton.setBounds(170, 270, 400, 50);
        playButton.setFont(Program.gameFont.deriveFont(24f));
        playButton.setOpaque(true);
        playButton.setFocusable(false);
        panel.add(playButton);

        //Erstellt den Einstellungen-Button:

        backButton = new JLabel("Hauptmenü", SwingConstants.CENTER);
        backButton.setBounds(170, 340, 400, 50);
        backButton.setFont(Program.gameFont.deriveFont(24f));
        backButton.setOpaque(true);
        backButton.setFocusable(false);
        panel.add(backButton);

        //Erstellt den Beenden-Button:

        closeButton = new JLabel("Beenden", SwingConstants.CENTER);
        closeButton.setBounds(170, 410, 400, 50);
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
                System.out.println("[DeathScreen.java] Spielfeld wird erstellt, bitte warten...");

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        //Timer stoppen, damit sich das Spielertempo nicht verdoppelt:

                        if (playerMoveTimer != null) {
                            playerMoveTimer.stop();
                        }
                        MouseInput.timerRunning = false;

                        //Anzeigen zurücksetzen:

                        Overlay.resetHealthHUD();
                        Overlay.resetAmmoHUD();

                        //Spielfeld dem Fenster übergeben:

                        JPanel gamePanel = Game.create();
                        Window.frame.setContentPane(gamePanel);
                        Window.frame.repaint();
                        Window.frame.revalidate();

                        //Eingaben sperren:

                        KeyboardInput.enabled = true;

                        MouseInput.enabled = true;
                    }
                });

                System.out.println("[DeathScreen.java] Spielfeld erfolgreich geladen.");
            }
        });

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        //Anzeigen zurücksetzen:

                        Overlay.resetHealthHUD();
                        Overlay.resetAmmoHUD();

                        //Hauptmenü dem Fenster übergeben:

                        JPanel menuPanel = TitleScreen.create();
                        Window.frame.setContentPane(menuPanel);
                        Window.frame.repaint();
                        Window.frame.revalidate();

                        //Eingaben sperren:

                        KeyboardInput.enabled = false;

                        MouseInput.enabled = false;
                    }
                });
            }
        });

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("[DeathScreen.java] Spiel wird beendet, bitte warten...");

                //Fenster schließen:

                Window.frame.dispose();

                //Timer stoppen, damit das Spiel beendet werden kann:

                if (playerMoveTimer != null) {
                    playerMoveTimer.stop();
                }
                MouseInput.timerRunning = false;
            }
        });

        System.out.println("[DeathScreen.java] Death-Screen erfolgreich erstellt.");

        return panel;
    }


}
