package handlers;

import java.awt.*;

public class MouseInput {

    /*
        Letzte Position der Maus speichern:
    */

    private static Point lastPosition = MouseInfo.getPointerInfo().getLocation();

    /*
        Mausbewegung erkennen
    */

    public static String MouseMove() {

        //Alle 100ms Bewegungs-Check ausführen:

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Aktuelle Position der Maus speichern:

            Point currentPosition = MouseInfo.getPointerInfo().getLocation();

            //Wenn neue Maus-Position ungleich alte Mausposition:

            if (!currentPosition.equals(lastPosition)) {
                int directionX = currentPosition.x - lastPosition.x;
                int directionY = currentPosition.y - lastPosition.y;

                //Bewegung in die Konsole schreiben:

                System.out.printf("Maus bewegt: %d,%d -> %d,%d (%s)\n",
                        lastPosition.x, lastPosition.y,
                        currentPosition.x, currentPosition.y,
                        getDirection(directionX, directionY));

                //Hier andere Aktionen einfügen

                //Letzte Position aktualisieren:

                lastPosition = currentPosition;
            }
        }
    }

        /*
            Bewegungsrichtung der Maus feststellen
        */

        private static String getDirection(int directionX, int directionY) {
            if (directionX > 0 && directionY > 0) {
                return "rechts & runter";
            } else if (directionX > 0 && directionY == 0) {
                return "rechts";
            } else if (directionX > 0 && directionY < 0) {
                return "rechts & hoch";
            } else if (directionX == 0 && directionY > 0) {
                return "runter";
            } else if (directionX == 0 && directionY < 0) {
                return "hoch";
            } else if (directionX < 0 && directionY > 0) {
                return "links & runter";
            } else if (directionX < 0 && directionY == 0) {
                return "links";
            } else if (directionX < 0 && directionY < 0) {
                return "links & hoch";
            } else {
                return "unbekannt";
            }
        }
}
