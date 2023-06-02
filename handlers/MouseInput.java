package handlers;

import algorithms.GameLoop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
    private GameLoop gameLoop;

    public static boolean enabled = false;

    public MouseInput(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public void mouseMoved(MouseEvent e) {
        // Not used
    }

    public void mouseClicked(MouseEvent e) {
        gameLoop.MouseClick();
    }

    public static double getPlayerToMouseAngle(Point playerPosition) {

        // Mausposition relativ zum Fenster berechnen:

        Point mousePositionOnScreen = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePositionOnScreen, components.Window.frame.getContentPane());
        Point mousePosition = mousePositionOnScreen;

        // Winkel berechnen:

        double angle = Math.atan2(mousePosition.y - playerPosition.y, mousePosition.x - playerPosition.x);

        return angle;
    }


}

