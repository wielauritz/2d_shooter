package components;

import algorithms.GameLoop;
import entities.Bots;
import entities.Player;
import handlers.AudioOutput;
import handlers.Database;
import handlers.KeyboardInput;
import handlers.MouseInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static algorithms.GameLoop.playerMoveTimer;

/*
    TitleScreen.java
    Startbildschirm erstellen und Menü einbinden
    Geschrieben von Lauritz Wiebusch
 */

public class TitleScreen {

    public static JLabel playButton;
    public static JLabel leaderboardButton;
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
        playButton.setBounds(170, 302, 400, 50);
        playButton.setFont(Program.gameFont.deriveFont(24f));
        playButton.setOpaque(true);
        playButton.setFocusable(false);
        panel.add(playButton);

        //Erstellt den Statistiken-Button:

        leaderboardButton = new JLabel("Leaderboard", SwingConstants.CENTER);
        leaderboardButton.setBounds(170, 372, 400, 50);
        leaderboardButton.setFont(Program.gameFont.deriveFont(24f));
        leaderboardButton.setOpaque(true);
        leaderboardButton.setFocusable(false);
        panel.add(leaderboardButton);

        //Erstellt den Einstellungen-Button:

        settingsButton = new JLabel("Einstellungen", SwingConstants.CENTER);
        settingsButton.setBounds(170, 442, 400, 50);
        settingsButton.setFont(Program.gameFont.deriveFont(24f));
        settingsButton.setOpaque(true);
        settingsButton.setFocusable(false);
        panel.add(settingsButton);

        //Erstellt den Beenden-Button:

        closeButton = new JLabel("Beenden", SwingConstants.CENTER);
        closeButton.setBounds(170, 512, 400, 50);
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
                System.out.println("[TitleScreen.java] Spielfeld wird erstellt, bitte warten...");

                //Spielfeld dem Fenster übergeben:

                Window.frame.setContentPane(Game.create());
                Window.frame.revalidate();

                //Eingaben aktivieren:

                KeyboardInput.enabled = true;
                MouseInput.enabled = true;

                //Sound abspielen:

                AudioOutput.playSound("audio/components/TitleScreen/click.wav", 100);

                //Aktuell eingeloggten Spieler speichern:

                Database.updatePlayerLastSeen(Player.name, System.currentTimeMillis());
                Database.updatePlayerLastScore(Player.name, 0);

                System.out.println("[TitleScreen.java] Spielfeld erfolgreich geladen.");

            }
        });

        leaderboardButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("[TitleScreen.java] Statistiken werden erstellt, bitte warten...");

                //Statistik dem Fenster übergeben:

                Window.frame.setContentPane(Leaderboard.create(true));
                Window.frame.revalidate();

                //Sound abspielen:

                AudioOutput.playSound("audio/components/TitleScreen/click.wav", 100);

            }
        });

        settingsButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("[TitleScreen.java] Einstellungen werden erstellt, bitte warten...");

                //Spielfeld dem Fenster übergeben:

                Window.frame.setContentPane(Settings.create());
                Window.frame.revalidate();

                //Sound abspielen:

                AudioOutput.playSound("audio/components/TitleScreen/click.wav", 100);

            }
        });

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("[TitleScreen.java] Spiel wird beendet, bitte warten...");

                //Fenster schließen:

                Window.frame.dispose();

                //Sound abspielen:

                AudioOutput.playSound("audio/components/TitleScreen/click.wav", 100);

                AudioOutput.shutdown();

                //Letzten eingeloggten Spieler speichern:

                Database.updatePlayerLastSeen(Player.name, System.currentTimeMillis());

                //Datenbankverbindung beenden:

                Database.close();

                //Spiel beenden:

                System.out.println("[TitleScreen.java] Spiel erfolgreich beendet.");

                System.exit(0);
            }
        });

        System.out.println("[TitleScreen.java] Hauptmenü erfolgreich erstellt.");

        return panel;
    }

}
