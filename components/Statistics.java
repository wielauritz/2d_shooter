package components;

import entities.Player;
import handlers.AudioOutput;
import handlers.Database;
import handlers.KeyboardInput;
import handlers.MouseInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Statistics {

    public static JLabel scoreboardButton;
    public static JLabel leaderboardButton;
    public static JLabel backButton;


    /*
        Erstellt das Hauptmenü
     */
    public static JPanel createMenu() {

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

        //Erstellt den Scoreboard-Button:

        scoreboardButton = new JLabel("Scoreboard", SwingConstants.CENTER);
        scoreboardButton.setBounds(170, 342, 400, 50);
        scoreboardButton.setFont(Program.gameFont.deriveFont(24f));
        scoreboardButton.setOpaque(true);
        scoreboardButton.setFocusable(false);
        panel.add(scoreboardButton);

        //Erstellt den Leaderboard-Button:

        leaderboardButton = new JLabel("Leaderboard", SwingConstants.CENTER);
        leaderboardButton.setBounds(170, 412, 400, 50);
        leaderboardButton.setFont(Program.gameFont.deriveFont(24f));
        leaderboardButton.setOpaque(true);
        leaderboardButton.setFocusable(false);
        panel.add(leaderboardButton);

        //Erstellt den Zurück-Button:

        backButton = new JLabel("Hauptmenü", SwingConstants.CENTER);
        backButton.setBounds(170, 482, 400, 50);
        backButton.setFont(Program.gameFont.deriveFont(24f));
        backButton.setOpaque(true);
        backButton.setFocusable(false);
        panel.add(backButton);

        //Erstellt die Informationen:

        JLabel info = new JLabel("© Eric John & Lauritz Wiebusch | BETA-Version (!)", SwingConstants.CENTER);
        info.setBounds(0, 675, 750, 50);
        info.setFont(Program.gameFont.deriveFont(12f));
        info.setForeground(Color.WHITE);
        panel.add(info);

        /*
            Mausklick im Hauptmenü registrieren:
        */

        scoreboardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        // Create the scoreboard scroll pane
                        JScrollPane scoreboardScrollPane = new JScrollPane();
                        scoreboardScrollPane.setBounds(170, 302, 400, 260);
                        panel.add(scoreboardScrollPane);

                        // Create the scoreboard list
                        JList<String> scoreboardList = new JList<>(Database.createScoreboard().toArray(new String[0]));
                        scoreboardList.setFont(Program.gameFont.deriveFont(16f));
                        scoreboardScrollPane.setViewportView(scoreboardList);
                        panel.remove(scoreboardButton);
                        panel.remove(leaderboardButton);

                        Window.frame.repaint();
                        Window.frame.revalidate();

                        //Sound abspielen:

                        AudioOutput.playSound("audio/components/Settings/click.wav", 100);
                    }
                });
            }
        });

        leaderboardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        //Sound abspielen:

                        AudioOutput.playSound("audio/components/Settings/click.wav", 100);
                    }
                });
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

                        //Sound abspielen:

                        AudioOutput.playSound("audio/components/Settings/click.wav", 100);
                    }
                });
            }
        });

        System.out.println("[Statistics.java] Statistiken erfolgreich erstellt.");

        return panel;
    }

}
