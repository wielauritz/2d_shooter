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
                if (keyCode == KeyEvent.VK_W) {
                    Player.move(0,-20);
                    System.out.println("up");
                }
                else if (keyCode == KeyEvent.VK_S) {
                    Player.move(0,20);
                    System.out.println("down");
                }
                else if (keyCode == KeyEvent.VK_A) {
                    Player.move(-20,0);
                    System.out.println("left");
                }
                else if (keyCode == KeyEvent.VK_D) {
                    Player.move(20,0);
                    System.out.println("right");
                }
            }
        });
    }

}
