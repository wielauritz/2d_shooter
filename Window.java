import javax.swing.JFrame;

public class Window {

    private JFrame frame;

    /*
        Erstellt das Programmfenster
    */

    public Window() {
        frame = new JFrame("2D Shooter");
        frame.setSize(750, 750);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
        Öffnet das Programmfenster
    */

    public void open() {
        frame.setVisible(true);
    }
}