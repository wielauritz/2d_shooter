package components;

import javax.swing.*;

public class Blank {

    /*
        Erstellt einen leeren Canvas
     */

    public static JPanel panel() {

        JPanel panel = new JPanel();

        panel.setLayout(null);

        System.out.println("[Blank.java] Leerer Canvas erfolgreich erstellt.");

        return panel;
    }

}

