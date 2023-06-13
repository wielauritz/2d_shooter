package handlers;

import algorithms.GameLoop;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
    KeyboardInput.java
    Aktivieren der Tastatureingabeerkennung und weitergeben von Ereignissen an die GameLoop und dazugehörige Methoden zur Veränderung des Spiels
    Geschrieben von Lauritz Wiebusch
 */

public class KeyboardInput implements KeyListener {

    public static boolean enabled = false;

    /*
        Importieren und übergeben von Eingaben an die GameLoop
     */

    private GameLoop gameLoop;

    public KeyboardInput(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        gameLoop.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        gameLoop.keyReleased(e);
    }
}