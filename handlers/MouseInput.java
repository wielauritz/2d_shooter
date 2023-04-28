package handlers;

public class MouseInput extends Window{

PointerInfo a = MouseInfo.getPointerInfo();
Point b = a.getLocation();
int x = (int) b.getX();
int y = (int) b.getY();
Robot r = new Robot();
r.mouseMove(x, y - 50);

}
