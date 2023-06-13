package components;

import algorithms.GameLoop;
import handlers.AudioOutput;
import handlers.Database;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/*
    Program.java
    Hauptdatei, die das Programm startet und alles relevante vorbereitet
    Geschrieben von Lauritz Wiebusch
 */

public class Program {

    /*
        Startet das Programm
    */

    public static Font gameFont;

    public static Window window;

    public static void main(String[] args) {

        //Schriftart laden:

        System.out.println("[Program.java] Spiel wird gestartet, bitte warten...");

        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("textures/font.otf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(gameFont);
            System.out.println("[Program.java] Spielschrift erfolgreich geladen.");
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            System.out.println("[Program.java] Spielschrift konnte nicht geladen werden.");
        }

        //Datenbank laden:

        Database.open();

        //Fenster erstellen:

        window = new Window();
        window.open();

        //Mausklick erkennen:

        GameLoop.MouseClick();

        //Spielmusik starten:

        AudioOutput.playMusic();

    }
}