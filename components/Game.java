package components;

import entities.Obstacles;
import entities.Player;
import handlers.KeyboardInput;
import utils.RandomNumber;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static components.Window.frame;

public class Game {

public static JPanel create() {

    //Setzt ein Hintergrundbild und setzt den Spieler obendrauf:

    JPanel panel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                Image image = ImageIO.read(new File("textures/floor.png"));
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    };

    panel.setLayout(null);

    panel.add(Player.generate());

    //Generiert und speichert die Hindernisse:

    ArrayList<Component> obstaclesList = new ArrayList<>();

    obstaclesList.add(Obstacles.createObstacle(RandomNumber.generate(1,6)*90,RandomNumber.generate(1,6)*90));
    obstaclesList.add(Obstacles.createObstacle(RandomNumber.generate(1,6)*90,RandomNumber.generate(1,6)*90));
    obstaclesList.add(Obstacles.createObstacle(RandomNumber.generate(1,6)*90,RandomNumber.generate(1,6)*90));
    obstaclesList.add(Obstacles.createObstacle(RandomNumber.generate(1,6)*90,RandomNumber.generate(1,6)*90));
    obstaclesList.add(Obstacles.createObstacle(RandomNumber.generate(1,6)*90,RandomNumber.generate(1,6)*90));

    Player.setObstacles(obstaclesList);

    for (Component obstacle : obstaclesList) {
        panel.add(obstacle);
    }

    //Bindet das Overlay ein:

    Container c = Overlay.createHealthHUD();
    panel.add(c);
    panel.setComponentZOrder(c, 0);

    Overlay.updateHealthHUD(0);

    //Tastendruck erkennen:

    KeyboardInput.KeyPress();

    System.out.println("[Game.java] Spielfeld erfolgreich erstellt.");

    return panel;
}

}
