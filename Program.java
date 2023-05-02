import components.Window;
import handlers.KeyboardInput;
import handlers.MouseInput;

import java.awt.*;

public class Program {

    /*
        Startet das Programm
    */

    public static void main(String[] args) {

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