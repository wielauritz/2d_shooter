import components.Window;
import handlers.KeyboardInput;

public class Program {

    /*
        Startet das Programm
    */

    public static void main(String[] args) {
        Window window = new Window();
        window.open();
        KeyboardInput.KeyPress();
    }
}