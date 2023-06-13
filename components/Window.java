package components;

import entities.Player;
import handlers.Database;

import javax.swing.*;

/*
    Window.java
    Erstellen und initialisieren des Fensters mit ersten Inhalten nach dem Start
    Geschrieben von Lauritz Wiebusch
 */

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

        //Hauptmenü dem Fenster übergeben:

        frame.setContentPane(TitleScreen.create());

        //Setzt einen Standard-Spielernamen:

        Player.name = Database.getLastSeenPlayerName();

        System.out.println("[Window.java] Fenster erfolgreich erstellt.");
    }

    /*
        Öffnet das Programmfenster
    */

    public void open() {
        frame.setVisible(true);
        System.out.println("[Window.java] Fenster erfolgreich geöffnet.\n[Window.java] Spiel erfolgreich gestartet.");
    }
}