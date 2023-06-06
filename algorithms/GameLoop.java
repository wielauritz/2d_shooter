package algorithms;

import components.Overlay;
import components.Window;
import entities.Bots;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static components.Window.frame;
import static entities.Bots.bots;
import static entities.Player.player;

public class GameLoop implements KeyListener {
    public static Timer playerMoveTimer;
    public static boolean timerRunning = true;
    private static Point lastPosition = MouseInfo.getPointerInfo().getLocation();
    private Set<Integer> pressedKeys = new HashSet<>();
    private boolean spacePressed = false;

    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();


    public GameLoop() {
        frame.addKeyListener(this);
        frame.requestFocusInWindow();

        //Timer für Spielerbewegung:
        
        playerMoveTimer = new Timer(16, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!KeyboardInput.enabled) return;

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
                
                if (spacePressed) {
                    spacePressed = false;
                    Projectile.shootProjectile();
                }
            }
        });
        playerMoveTimer.start();

        //Timer für Botbewegung:

        executorService = Executors.newSingleThreadScheduledExecutor();


        executorService.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> {
                //for (Component bots : Bots) {

                    int directionX = 0;
                    int directionY = 0;
                    int speed = 5;

                if (player.getX() < bots.getX()) {
                    directionX -= speed;
                } else if (player.getY() < bots.getY()) {
                    directionY -= speed;
                } else if (player.getX() > bots.getX()) {
                    directionX += speed;
                } else if (player.getY() > bots.getY()) {
                    directionY += speed;
                }
                if (directionX != 0 || directionY != 0) {
                    Bots.move(directionX, directionY);
                }



            });
        }, 0, 100, TimeUnit.MILLISECONDS);

        System.out.println("[GameLoop.java] Loop erfolgreich gestartet.");
    }

    public static void shutdownExecutorService() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    //Mausbewegungs-Listener:
    
    public static void MouseMove() {
        System.out.println("[GameLoop.java] Mausbewegungs-Listener aktiviert.");

        while (timerRunning) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Aktuelle Mausposition abrufen:
            
            Point currentPosition = MouseInfo.getPointerInfo().getLocation();

            if (!currentPosition.equals(lastPosition)) {
                
                //Berechnung der Änderung der Mausposition:
                
                int directionX = currentPosition.x - lastPosition.x;
                int directionY = currentPosition.y - lastPosition.y;
                
                lastPosition = currentPosition;
            }
        }
    }

    //Mausklick-Listener:
    
    public static void MouseClick() {
        System.out.println("[GameLoop.java] Mausklick-Listener aktiviert.");

        frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (MouseInput.enabled && e.getButton() == MouseEvent.BUTTON1) {
                    
                    //Position des Spielers abrufen:
                    
                    Point playerPosition = new Point(player.getX() + Player.size / 2,
                            player.getY() + Player.size / 2);

                    //Winkel zwischen Spieler und Mausposition berechnen:
                    
                    double angle = MouseInput.getPlayerToMouseAngle(playerPosition);

                    int projectileSpeed = 30;
                    double speedX = Math.cos(angle) * projectileSpeed;
                    double speedY = Math.sin(angle) * projectileSpeed;

                    //Projektil erstellen und zur Anzeige hinzufügen:
                    
                    JLabel projectile = Projectile.createProjectile(playerPosition.x, playerPosition.y);
                    frame.getContentPane().add(projectile);
                    frame.getContentPane().setComponentZOrder(projectile, 0);

                    //Timer für Projektilbewegung:
                    
                    Timer timer = new Timer(1, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            double newX = projectile.getX() + speedX;
                            double newY = projectile.getY() + speedY;

                            boolean collided = false;
                            
                            //Kollision mit Hindernissen überprüfen:
                            
                            for (Component obstacle : Player.obstacles) {
                                projectile.setLocation((int) newX, (int) newY);
                                if (Player.isCollidingWithObstacle(projectile, obstacle, false)) {
                                    collided = true;
                                    break;
                                }
                            }

                            //Wenn Kollision oder Projektil außerhalb des Fensters, stoppen und ausblenden:
                            
                            if (collided || newX < 0 || newX >= frame.getWidth() - Player.size ||
                                    newY < 0 || newY >= frame.getHeight() - Player.size) {
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
        
        //Nicht benötigt
        
    }
}
