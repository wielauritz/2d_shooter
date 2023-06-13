package components;

import algorithms.GameLoop;
import entities.Bots;
import entities.Player;
import handlers.AudioOutput;
import handlers.Database;
import handlers.KeyboardInput;
import handlers.MouseInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static algorithms.GameLoop.playerMoveTimer;

/*
    WinScreen.java
    Gewonnen-Bildschirm erstellen und Menü einbinden
    Geschrieben von Lauritz Wiebusch
 */

public class WinScreen {

    public static JLabel playButton;
    public static JLabel leaderboardButton;
    public static JLabel backButton;
    public static JLabel closeButton;

    /*
        Erstellt das Win-Screen-Menü
    */

    public static JPanel create() {

        JPanel panel = new JPanel() {

            //Setzt das Hintergrundbild und den Titel für das Hauptmenü:

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon gifIcon = new ImageIcon("textures/components/WinScreen/winscreen.gif");
                Image gifImage = gifIcon.getImage();
                g.drawImage(gifImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setLayout(null);
        panel.setBackground(Color.RED);

        //Erstellt den Gewonnen-Text:

        JLabel info = new JLabel("DU HAST GEWONNEN!", SwingConstants.CENTER);
        info.setBounds(0, 100, 750, 50);
        info.setFont(Program.gameFont.deriveFont(48f));
        info.setForeground(Color.WHITE);
        panel.add(info);

        JLabel score = new JLabel("SCORE: " + Database.getPlayerLastScore(Player.name), SwingConstants.CENTER);
        score.setBounds(0, 175, 750, 50);
        score.setFont(Program.gameFont.deriveFont(32f));
        score.setForeground(Color.WHITE);
        panel.add(score);

        //Erstellt den Spielen-Button:

        playButton = new JLabel("Neu starten", SwingConstants.CENTER);
        playButton.setBounds(170, 270, 400, 50);
        playButton.setFont(Program.gameFont.deriveFont(24f));
        playButton.setOpaque(true);
        playButton.setFocusable(false);
        panel.add(playButton);

        //Erstellt den Statistiken-Button:

        leaderboardButton = new JLabel("Leaderboard", SwingConstants.CENTER);
        leaderboardButton.setBounds(170, 340, 400, 50);
        leaderboardButton.setFont(Program.gameFont.deriveFont(24f));
        leaderboardButton.setOpaque(true);
        leaderboardButton.setFocusable(false);
        panel.add(leaderboardButton);

        //Erstellt den Hauptmenü-Button:

        backButton = new JLabel("Hauptmenü", SwingConstants.CENTER);
        backButton.setBounds(170, 410, 400, 50);
        backButton.setFont(Program.gameFont.deriveFont(24f));
        backButton.setOpaque(true);
        backButton.setFocusable(false);
        panel.add(backButton);

        //Erstellt den Beenden-Button:

        closeButton = new JLabel("Beenden", SwingConstants.CENTER);
        closeButton.setBounds(170, 480, 400, 50);
        closeButton.setFont(Program.gameFont.deriveFont(24f));
        closeButton.setOpaque(true);
        closeButton.setFocusable(false);
        panel.add(closeButton);

        /*
            Mausklicks im Win-Screen-Menü registrieren:
        */

        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("[WinScreen.java] Spielfeld wird erstellt, bitte warten...");

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //Spielfeld dem Fenster übergeben:

                        JPanel gamePanel = Game.create();
                        Window.frame.setContentPane(gamePanel);
                        Window.frame.repaint();
                        Window.frame.revalidate();

                        //Eingaben entsperren:

                        KeyboardInput.enabled = true;
                        MouseInput.enabled = true;

                        //Sound abspielen:

                        AudioOutput.playSound("audio/components/WinScreen/click.wav", 100);

                        //Aktuell eingeloggten Spieler speichern:

                        Database.updatePlayerLastSeen(Player.name, System.currentTimeMillis());
                        Database.updatePlayerLastScore(Player.name, 0);
                    }
                });

                System.out.println("[WinScreen.java] Spielfeld erfolgreich geladen.");
            }
        });

        leaderboardButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("[WinScreen.java] Statistiken werden erstellt, bitte warten...");

                //Statistik dem Fenster übergeben:

                Window.frame.setContentPane(Leaderboard.create(false));
                Window.frame.revalidate();

                //Sound abspielen:

                AudioOutput.playSound("audio/components/WinScreen/click.wav", 100);

            }
        });

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //Hauptmenü dem Fenster übergeben:

                        JPanel menuPanel = TitleScreen.create();
                        Window.frame.setContentPane(menuPanel);
                        Window.frame.repaint();
                        Window.frame.revalidate();

                        //Sound abspielen:

                        AudioOutput.playSound("audio/components/WinScreen/click.wav", 100);
                    }
                });
            }
        });

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("[WinScreen.java] Spiel wird beendet, bitte warten...");

                //Fenster schließen:

                Window.frame.dispose();

                //Sound abspielen:

                AudioOutput.playSound("audio/components/WinScreen/click.wav", 100);

                AudioOutput.shutdown();

                //Letzten eingeloggten Spieler speichern:

                Database.updatePlayerLastSeen(Player.name, System.currentTimeMillis());

                //Datenbankverbindung beenden:

                Database.close();

                //Spiel beenden:

                System.out.println("[WinScreen.java] Spiel erfolgreich beendet.");

                System.exit(0);
            }
        });

        //Anzeigen zurücksetzen:

        Overlay.resetHealthHUD();
        Overlay.resetAmmoHUD();
        Overlay.resetScoreHUD();

        Player.isInWater = false;

        //Timer stoppen, damit sich das Spielertempo nicht verdoppelt:

        if (playerMoveTimer != null) {
            playerMoveTimer.stop();
        }
        GameLoop.timerRunning = false;

        //Bots entferenen:

        Game.botCount = 0;

        for (Bots bot : new ArrayList<>(Game.botsList)) {
            bot.destroyBot();
        }

        Bots.projectileExecutorService.shutdown();

        //Sound abspielen:

        AudioOutput.playSound("audio/components/WinScreen/youwon.wav", 4100);

        //Eingaben deaktivieren:

        KeyboardInput.enabled = false;
        MouseInput.enabled = false;

        System.out.println("[WinScreen.java] Win-Screen erfolgreich erstellt.");

        return panel;
    }


}
