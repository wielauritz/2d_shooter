package handlers;

import components.Window;
import entities.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardInput extends Window {

    /*
        Tastendruck erkennen und Spieler bewegen (Player.move(newX, newY);)
    */

    public static void KeyPress() {
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_W) {
                    Player.move(0,-5);
                    System.out.println("up");
                }
                else if (keyCode == KeyEvent.VK_S) {
                    Player.move(0,5);
                    System.out.println("down");
                }
                else if (keyCode == KeyEvent.VK_A) {
                    Player.move(-5,0);
                    System.out.println("left");
                }
                else if (keyCode == KeyEvent.VK_D) {
                    Player.move(5,0);
                    System.out.println("right");
                } else if (keyCode == KeyEvent.VK_UP) {
                    Player.move(0,-5);
                    System.out.println("up");
                }
                else if (keyCode == KeyEvent.VK_DOWN) {
                    Player.move(0,5);
                    System.out.println("down");
                }
                else if (keyCode == KeyEvent.VK_LEFT) {
                    Player.move(-5,0);
                    System.out.println("left");
                }
                else if (keyCode == KeyEvent.VK_RIGHT) {
                    Player.move(5,0);
                    System.out.println("right");
                }
            }
        });
    }

}
