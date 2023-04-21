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

        player.setBounds(x, y, playerSize, playerSize);

        return player;
    }

    public static void move(int x, int y) {
        System.out.println(player.getX() + "-" + player.getY());
        if ((player.getX() > 0 && player.getX() < 750) && (player.getY() > 0 && player.getY() < 750)) {
            player.setBounds(player.getX() + x, player.getY() + y, playerSize, playerSize);
        } else {
            //Zurücksetzen des Spielers, sodass er sich wieder bewegen kann
        }
    }
}
