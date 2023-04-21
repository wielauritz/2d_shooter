package entities;

import javax.swing.*;
import java.awt.*;

public class Player {

    public static JLabel generate() {

    //Erstellt den Spieler:

    JLabel player = new JLabel(new ImageIcon("textures/player.png"));

    //Positioniert den Spieler mittig im Fenster:

    int playerSize = 50;

    int x = (750 - playerSize - (playerSize / 2)) / 2;
    int y = (750 - playerSize - (playerSize / 2)) / 2;

    System.out.println(x + "-" + y);

    player.setBounds(x, y, playerSize, playerSize);

    return player;
    }
}
