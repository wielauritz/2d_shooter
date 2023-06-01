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

public class Game {

    public static JPanel create() {

        //Setzt ein Hintergrundbild und setzt den Spieler obendrauf:

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image image = ImageIO.read(new File("textures/floor_path.png"));
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

        obstaclesList.add(Obstacles.generateWater(400, 250));

        obstaclesList.add(Obstacles.generateTree(165, 482));
        obstaclesList.add(Obstacles.generateTree(648, 316));
        obstaclesList.add(Obstacles.generateTree(217, 570));
        obstaclesList.add(Obstacles.generateTree(212, 24));
        obstaclesList.add(Obstacles.generateTree(71, 625));
        obstaclesList.add(Obstacles.generateTree(528, 197));
        obstaclesList.add(Obstacles.generateTree(73, 413));
        obstaclesList.add(Obstacles.generateTree(447, 149));
        obstaclesList.add(Obstacles.generateTree(381, 150));
        obstaclesList.add(Obstacles.generateTree(629, 440));
        obstaclesList.add(Obstacles.generateTree(6, 567));
        obstaclesList.add(Obstacles.generateTree(547, 387));
        obstaclesList.add(Obstacles.generateTree(21, 81));
        obstaclesList.add(Obstacles.generateTree(70, 222));
        obstaclesList.add(Obstacles.generateTree(193, 396));
        obstaclesList.add(Obstacles.generateTree(599, 8));

        Player.setObstacles(obstaclesList);

        for (Component obstacle : obstaclesList) {
            panel.add(obstacle);
        }

        //Bindet das Overlay ein:

        Container c = Overlay.createHealthHUD();
        panel.add(c);
        panel.setComponentZOrder(c, 0);

        Container c2 = Overlay.createAmmoHUD();
        panel.add(c2);
        panel.setComponentZOrder(c2, 0);

        Overlay.updateHealthHUD(0);

        //Tastendruck erkennen:

        KeyboardInput.KeyPress();

        System.out.println("[Game.java] Spielfeld erfolgreich erstellt.");

        System.out.println("a" + panel.getComponentCount());

        return panel;
    }

}
