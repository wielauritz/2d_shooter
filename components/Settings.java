package components;

import entities.Player;
import handlers.AudioOutput;
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

public class Settings {

    public static JTextField nameField;
    public static JLabel soundsButton;
    public static JLabel musicButton;
    public static JLabel backButton;


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

        //Erstellt das Namen-Eingabefeld

        nameField = new JTextField(Player.name, SwingConstants.CENTER);
        nameField.setBounds(170, 302, 400, 50);
        nameField.setFont(Program.gameFont.deriveFont(24f));
        nameField.setOpaque(true);
        nameField.setFocusable(true);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(nameField);

        //Erstellt den Geräusche-Button:

        soundsButton = new JLabel("Sounds - " + (AudioOutput.soundsEnabled ? "AN" : "AUS"), SwingConstants.CENTER);
        soundsButton.setBounds(170, 372, 400, 50);
        soundsButton.setFont(Program.gameFont.deriveFont(24f));
        soundsButton.setOpaque(true);
        soundsButton.setFocusable(false);
        panel.add(soundsButton);

        //Erstellt den Musik-Button:

        musicButton = new JLabel("Musik - " + (AudioOutput.musicEnabled ? "AN" : "AUS"), SwingConstants.CENTER);
        musicButton.setBounds(170, 442, 400, 50);
        musicButton.setFont(Program.gameFont.deriveFont(24f));
        musicButton.setOpaque(true);
        musicButton.setFocusable(false);
        panel.add(musicButton);

        //Erstellt den Zurück-Button:

        backButton = new JLabel("Hauptmenü", SwingConstants.CENTER);
        backButton.setBounds(170, 512, 400, 50);
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

        nameField.getDocument().addDocumentListener(new DocumentListener() {
            private void update(DocumentEvent e) {
                try {
                    Player.name = e.getDocument().getText(0, e.getDocument().getLength());
                    AudioOutput.playSound("audio/components/Settings/typewriter.wav", 500);
                    System.out.println("[Settings.java] Spielername erfolgreich zu \"" + Player.name + "\" aktualisiert.");
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update(e);
            }
        });

        /*
            Mausklick im Hauptmenü registrieren:
        */

        soundsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        //Sounds (de)aktivieren:

                        AudioOutput.soundsEnabled = !AudioOutput.soundsEnabled;

                        //Button aktualisieren:

                        soundsButton.setText("Sounds - " + (AudioOutput.soundsEnabled ? "AN" : "AUS"));

                        //Sound abspielen:

                        AudioOutput.playSound("audio/components/Settings/click.wav", 100);
                    }
                });
            }
        });

        musicButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        //Sounds (de)aktivieren:

                        AudioOutput.musicEnabled = !AudioOutput.musicEnabled;

                        //Button aktualisieren:

                        musicButton.setText("Musik - " + (AudioOutput.musicEnabled ? "AN" : "AUS"));

                        System.out.println(AudioOutput.musicEnabled + "!");

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

        System.out.println("[Settings.java] Einstellungen erfolgreich erstellt.");

        return panel;
    }

}
