package handlers;

import algorithms.GameLoop;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
    private GameLoop gameLoop;

    public static boolean enabled = true;

    public KeyboardInput(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public void keyTyped(KeyEvent e) {
        // Not used
    }

    public void keyPressed(KeyEvent e) {
        gameLoop.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        gameLoop.keyReleased(e);
    }
}