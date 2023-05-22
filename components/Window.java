package components;

import entities.Obstacles;
import entities.Player;
import handlers.KeyboardInput;
import handlers.MouseInput;
import utils.RandomNumber;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Window {

    public static JFrame frame;
    public static JPanel panel;

    /*
        Erstellt das Programm-Fenster
    */

    public Window() {

        frame = new JFrame("2D Shooter");
        frame.setSize(750, 750);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        System.out.println("[Window.java] Fenster erfolgreich erstellt.");

/*
            //Setzt ein Hintergrundbild und setzt den Spieler obendrauf:

            panel = new JPanel() {
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

            JLabel playButton = new JLabel("Spielen", SwingConstants.CENTER);
            playButton.setBounds(170,300,400,50);
            playButton.setFont(Program.gameFont.deriveFont(24f));
            playButton.setOpaque(true);
            playButton.setFocusable(false);
            panel.add(playButton);

            JLabel settingsButton = new JLabel("Einstellungen", SwingConstants.CENTER);
            settingsButton.setBounds(170,370,400,50);
            settingsButton.setFont(Program.gameFont.deriveFont(24f));
            settingsButton.setOpaque(true);
            settingsButton.setFocusable(false);
            panel.add(settingsButton);

            JLabel closeButton = new JLabel("Beenden", SwingConstants.CENTER);
            closeButton.setBounds(170,440,400,50);
            closeButton.setFont(Program.gameFont.deriveFont(24f));
            closeButton.setOpaque(true);
            closeButton.setFocusable(false);
            panel.add(closeButton);

            closeButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("[Window.java] Spiel wird beendet, bitte warten...");
                    frame.dispose();
                    MouseInput.run = false;
                    KeyboardInput.playerMoveTimer.stop();
                }
            });

 /*

        //Setzt ein Hintergrundbild und setzt den Spieler obendrauf:

            panel = new JPanel() {
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

            panel.add(Player.generate());

            //Generiert und speichert die Hindernisse:

            ArrayList<Component> obstaclesList = new ArrayList<>();

            obstaclesList.add(Obstacles.createObstacle(RandomNumber.generate(1,6)*90,RandomNumber.generate(1,6)*90));
            obstaclesList.add(Obstacles.createObstacle(RandomNumber.generate(1,6)*90,RandomNumber.generate(1,6)*90));
            obstaclesList.add(Obstacles.createObstacle(RandomNumber.generate(1,6)*90,RandomNumber.generate(1,6)*90));
            obstaclesList.add(Obstacles.createObstacle(RandomNumber.generate(1,6)*90,RandomNumber.generate(1,6)*90));
            obstaclesList.add(Obstacles.createObstacle(RandomNumber.generate(1,6)*90,RandomNumber.generate(1,6)*90));

            Player.setObstacles(obstaclesList);

            for (Component obstacle : obstaclesList) {
                panel.add(obstacle);
            }

            //Bindet das Overlay ein:

            Container c = Overlay.createHealthHUD();
            panel.add(c);
            panel.setComponentZOrder(c, 0);

            Overlay.updateHealthHUD(0);

            System.out.println("[Window.java] Fenster erfolgreich erstellt.");

         */
        frame.setContentPane(Menu.create());
    }

    /*
        Öffnet das Programmfenster
    */

    public void open() {
        frame.setVisible(true);
        System.out.println("[Window.java] Fenster erfolgreich geöffnet.");
    }
}
