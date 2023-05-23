package components;

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

        //Menü dem Fenster übergeben:

        frame.setContentPane(Menu.create());

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
