package handlers;

import components.Window;
import entities.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardInput extends Window {

    public static void KeyPress() {
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_UP) {
                    Player.move(0,-2);
                    System.out.println("Up Arrrow-Key is pressed!");
                }
                else if (keyCode == KeyEvent.VK_DOWN) {
                    Player.move(0,2);
                    System.out.println("Down Arrrow-Key is pressed!");
                }
                else if (keyCode == KeyEvent.VK_LEFT) {
                    Player.move(-2,0);
                    System.out.println("Left Arrrow-Key is pressed!");
                }
                else if (keyCode == KeyEvent.VK_RIGHT) {
                    Player.move(2,0);
                    System.out.println("Right Arrrow-Key is pressed!");
                }
            }
        });
    }

}
