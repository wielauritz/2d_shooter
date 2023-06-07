package handlers;

import algorithms.GameLoop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static entities.Player.player;

public class MouseInput extends MouseAdapter {
    private GameLoop gameLoop;

    public static boolean enabled = false;

    public MouseInput(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public void mouseMoved(MouseEvent e) { }

    public void mouseClicked(MouseEvent e) {
        gameLoop.MouseClick();
    }

    public static double playerToMouseAngle(Point playerPosition) {

        // Mausposition relativ zum Fenster berechnen:

        Point mousePositionOnScreen = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePositionOnScreen, components.Window.frame.getContentPane());

        // Winkel berechnen:

        double angle = Math.atan2(mousePositionOnScreen.y - playerPosition.y, mousePositionOnScreen.x - playerPosition.x);

        //Spieler in Winkelrichtung schauen lassen:

        int directionX = (int) (mousePositionOnScreen.x - playerPosition.x);
        int directionY = (int) (mousePositionOnScreen.y - playerPosition.y);

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

