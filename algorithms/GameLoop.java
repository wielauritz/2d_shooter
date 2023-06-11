package algorithms;

import entities.Bots;
import entities.Player;
import entities.Projectile;
import handlers.KeyboardInput;
import handlers.MouseInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static components.Game.botsList;
import static components.Window.frame;
import static entities.Player.player;

public class GameLoop implements KeyListener {

    public static Timer playerMoveTimer;
    public static boolean timerRunning = true;
    private static final Point lastPosition = MouseInfo.getPointerInfo().getLocation();
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final Set<Integer> pressedKeys = new HashSet<>();
    private boolean spacePressed = false;

    public GameLoop() {
        frame.addKeyListener(this);
        frame.requestFocusInWindow();

        //Timer für Spielerbewegung:

        playerMoveTimer = new Timer(16, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (KeyboardInput.enabled) {

                    int directionX = 0;
                    int directionY = 0;
                    int speed = 5;

                    //Tastenabfragen für Richtung der Spielerbewegung:

                    for (Integer keyCode : pressedKeys) {
                        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                            directionY -= speed;
                        } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
                            directionY += speed;
                        } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
                            directionX -= speed;
                        } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
                            directionX += speed;
                        }
                    }

                    //Spielerbewegung basierend auf den abgefragten Tasten:

                    if (directionX != 0 || directionY != 0) {
                        Player.move(directionX, directionY);
                    }

                    //Schießen des Projektils, wenn Leertaste gedrückt wurde:

                    if (spacePressed && KeyboardInput.enabled) {
                        spacePressed = false;
                        Projectile.shootProjectile();
                    }
                }
            }
        });
        playerMoveTimer.start();



        System.out.println("[GameLoop.java] Loop erfolgreich gestartet.");
    }

    public static void shutdownExecutorService() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    //Mausklick-Listener:

    public static void MouseClick() {
        System.out.println("[GameLoop.java] Mausklick-Listener aktiviert.");

        frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (MouseInput.enabled && e.getButton() == MouseEvent.BUTTON1) {
                    Projectile.shootProjectile();
                }
            }
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyboardInput.enabled) {
            pressedKeys.add(e.getKeyCode());
        }

        //Leertaste gedrückt:

        if (KeyboardInput.enabled && e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (KeyboardInput.enabled) {
            pressedKeys.remove(e.getKeyCode());
        }

        //Leertaste losgelassen:

        if (KeyboardInput.enabled && e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}