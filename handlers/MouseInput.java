package handlers;

import algorithms.GameLoop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static entities.Player.player;

public class MouseInput extends MouseAdapter {

    public static boolean enabled = false;

    /*
        Importieren und übergeben von Eingaben an die GameLoop
    */

    public void mouseClicked(MouseEvent e) {
        GameLoop.MouseClick();
    }

    public void mouseMoved(MouseEvent e) { }

    /*
        Mauswinkelberechnung zur Schussrichtungsberechnung und Spielerdrehung
     */

    public static double playerToMouseAngle(Point playerPosition) {

        //Mausposition relativ zum Fenster berechnen:

        Point mousePositionOnScreen = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePositionOnScreen, components.Window.frame.getContentPane());

        //Winkel berechnen:

        double angle = Math.atan2(mousePositionOnScreen.y - playerPosition.y, mousePositionOnScreen.x - playerPosition.x);

        //Spieler in Winkelrichtung drehen:

        int directionX = mousePositionOnScreen.x - playerPosition.x;
        int directionY = mousePositionOnScreen.y - playerPosition.y;

        if ((directionX > 0 && directionY > 0) || (directionX > 0 && directionY == 0) || (directionX == 0 && directionY > 0)) {
            player.setIcon(new ImageIcon("textures/entities/Player/character.png"));
        } else if ((directionX < 0 && directionY > 0) || (directionX < 0 && directionY == 0)) {
            player.setIcon(new ImageIcon("textures/entities/Player/character_flipped.png"));
        } else if ((directionX < 0 && directionY < 0) || (directionX == 0 && directionY < 0) || (directionX > 0 && directionY < 0)) {
            player.setIcon(new ImageIcon("textures/entities/Player/character_inverted.png"));
        } else {
            player.setIcon(new ImageIcon("textures/entities/Player/character.png"));
        }
        return angle;
    }
}

