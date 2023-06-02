package algorithms;

import components.Overlay;
import components.Window;
import entities.Player;
import entities.Projectile;
import handlers.KeyboardInput;
import handlers.MouseInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import static components.Window.frame;

public class GameLoop implements KeyListener {
    public static Timer playerMoveTimer;
    public static boolean timerRunning = true;
    private static Point lastPosition = MouseInfo.getPointerInfo().getLocation();
    private Set<Integer> pressedKeys = new HashSet<>();
    private boolean spacePressed = false;

    public GameLoop() {
        frame.addKeyListener(this);
        frame.requestFocusInWindow();

        playerMoveTimer = new Timer(16, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!KeyboardInput.enabled) return;

                int directionX = 0;
                int directionY = 0;
                int speed = 5;

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

                if (directionX != 0 || directionY != 0) {
                    Player.move(directionX, directionY);
                }

                if (spacePressed) {
                    spacePressed = false;
                    Projectile.shootProjectile();
                }
            }
        });
        playerMoveTimer.start();
        System.out.println("[GameLoop.java] Loop erfolgreich gestartet.");
    }

    public static void MouseMove() {
        System.out.println("[GameLoop.java] Mausbewegungs-Listener aktiviert.");

        while (timerRunning) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Point currentPosition = MouseInfo.getPointerInfo().getLocation();

            if (!currentPosition.equals(lastPosition)) {
                int directionX = currentPosition.x - lastPosition.x;
                int directionY = currentPosition.y - lastPosition.y;

                // - - - Hier andere Aktionen einfügen - - - \\

                lastPosition = currentPosition;
            }
        }
    }

    public static void MouseClick() {
        System.out.println("[GameLoop.java] Mausklick-Listener aktiviert.");

        frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (MouseInput.enabled && e.getButton() == MouseEvent.BUTTON1) {
                    Point playerPosition = new Point(Player.player.getX() + Player.playerSize / 2,
                            Player.player.getY() + Player.playerSize / 2);

                    double angle = MouseInput.getPlayerToMouseAngle(playerPosition);

                    int projectileSpeed = 30;
                    double speedX = Math.cos(angle) * projectileSpeed;
                    double speedY = Math.sin(angle) * projectileSpeed;

                    JLabel projectile = Projectile.createProjectile(playerPosition.x, playerPosition.y);
                    frame.getContentPane().add(projectile);
                    frame.getContentPane().setComponentZOrder(projectile, 0);

                    Timer timer = new Timer(1, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            double newX = projectile.getX() + speedX;
                            double newY = projectile.getY() + speedY;

                            boolean collided = false;
                            for (Component obstacle : Player.obstacles) {
                                projectile.setLocation((int) newX, (int) newY);
                                if (Player.isCollidingWithObstacle(projectile, obstacle)) {
                                    collided = true;
                                    break;
                                }
                            }

                            if (collided || newX < 0 || newX >= frame.getWidth() - Player.playerSize ||
                                    newY < 0 || newY >= frame.getHeight() - Player.playerSize) {
                                projectile.setLocation(-100, -100);
                                Overlay.updateAmmoHUD(1);
                                ((Timer) e.getSource()).stop();
                            } else {
                                projectile.setLocation((int) newX, (int) newY);
                            }
                        }
                    });

                    timer.start();
                }
            }
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyboardInput.enabled) {
            pressedKeys.add(e.getKeyCode());
        }

        if (KeyboardInput.enabled && e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (KeyboardInput.enabled) {
            pressedKeys.remove(e.getKeyCode());
        }

        if (KeyboardInput.enabled && e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}