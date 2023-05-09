package components;

import components.Window;
import handlers.KeyboardInput;
import handlers.MouseInput;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Program {

    /*
        Startet das Programm
    */

    public static Font gameFont;

    public static void main(String[] args) {

        //Schriftart laden:

        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("textures/font.otf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(gameFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }

        //Fenster erstellen:

        Window window = new Window();
        window.open();

        //Tastendruck erkennen:

        KeyboardInput.KeyPress();

        //Mausbewegung erkennen:

        MouseInput.MouseMove();

        //Mausklick erkennen:

        MouseInput.MouseClick();

    }
}