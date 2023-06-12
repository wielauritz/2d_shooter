package components;

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

public class Leaderboard {

    public static JLabel scoreboardButton;
    public static JLabel leaderboardButton;
    public static JLabel backButton;


    /*
        Erstellt das Hauptmenü
     */
    public static JPanel create(boolean isMenu) {



            JPanel panel = new JPanel() {

                //Setzt das Hintergrundbild und den Titel für das Hauptmenü:

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    try {
                        if (isMenu) {
                            ImageIcon gifIcon = new ImageIcon("textures/components/TitleScreen/titlescreen.gif");
                            Image gifImage = gifIcon.getImage();
                            Image title = ImageIO.read(new File("textures/components/TitleScreen/title.png"));
                            g.drawImage(gifImage, -200, 0, getWidth() + 400, getHeight(), this);
                            g.drawImage(title, 100, 63, 544, 75, this);
                        } else {
                            ImageIcon gifIcon = new ImageIcon("textures/components/DeathScreen/deathscreen.gif");
                            Image gifImage = gifIcon.getImage();
                            g.drawImage(gifImage, 0, 0, getWidth(), getHeight(), this);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };

        panel.setLayout(null);

        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setBounds(0, 200, 750, 50);
        title.setFont(Program.gameFont.deriveFont(32f));
        title.setForeground(Color.WHITE);
        panel.add(title);

        //Erstellt den Zurück-Button:

        backButton = new JLabel("Hauptmenü", SwingConstants.CENTER);
        backButton.setBounds(170, 512, 400, 50);
        backButton.setFont(Program.gameFont.deriveFont(24f));
        backButton.setOpaque(true);
        backButton.setFocusable(false);
        panel.add(backButton);

        if (isMenu) {

            //Erstellt die Informationen:

            JLabel info = new JLabel("© Eric John & Lauritz Wiebusch | BETA-Version (!)", SwingConstants.CENTER);
            info.setBounds(0, 675, 750, 50);
            info.setFont(Program.gameFont.deriveFont(12f));
            info.setForeground(Color.WHITE);
            panel.add(info);
        }

                        // Create the scoreboard scroll pane
                        JScrollPane scoreboardScrollPane = new JScrollPane();
                        scoreboardScrollPane.setBounds(170, 302, 400, 190);
                        panel.add(scoreboardScrollPane);

                        // Create the scoreboard list
                        JList<String> scoreboardList = new JList<>(Database.createScoreboard().toArray(new String[0]));
                        scoreboardList.setFont(Program.gameFont.deriveFont(16f));
                        scoreboardScrollPane.setViewportView(scoreboardList);

                        Window.frame.repaint();
                        Window.frame.revalidate();

                        //Sound abspielen:

                        AudioOutput.playSound("audio/components/Settings/click.wav", 100);


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
