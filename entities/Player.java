package entities;

import components.Window;

import javax.swing.*;

public class Player {

    public static JLabel player;
    public static int playerSize = 50;

    /*
        Generiert den Spieler
     */

    public static JLabel generate() {

        //Erzeugt den Spieler:

        player = new JLabel(new ImageIcon("textures/player.png"));

        //Positioniert den Spieler mittig im Fenster:

        int x = (750 - playerSize - (playerSize / 2)) / 2;
        int y = (750 - playerSize - (playerSize / 2)) / 2;

        player.setBounds(x, y, playerSize, playerSize);

        System.out.println("[Player.java] Spieler erfolgreich erstellt.");

        return player;
    }

    /*
        Überprüft, ob der Spieler sich am Rand befindet
     */

    public static void move(int x, int y) {
        System.out.println("[Player.java] Spieler bewegt: x=" + player.getX() + " und y=" + player.getY());

        int newX = player.getX() + x;
        int newY = player.getY() + y;

        if (newX >= 0 && newX + playerSize <= 750) {
            player.setLocation(newX, player.getY());
        }

        if (newY >= 0 && newY + playerSize <= 720) {
            player.setLocation(player.getX(), newY);
        }
    }



}
