package entities;

import javax.swing.*;

public class Player {

    public static JLabel player;
    public static int playerSize = 50;
    public static JLabel generate() {

    //Erstellt den Spieler:

        player = new JLabel(new ImageIcon("textures/player.png"));

        //Positioniert den Spieler mittig im Fenster

        int x = (750 - playerSize - (playerSize / 2)) / 2;
        int y = (750 - playerSize - (playerSize / 2)) / 2;

        System.out.println(x + "-" + y);

        player.setBounds(x, y, playerSize, playerSize);

        return player;
    }

    public static void move(int x, int y) {
        player.setBounds(player.getX()+x, player.getY()+y, playerSize, playerSize);
    }
}
