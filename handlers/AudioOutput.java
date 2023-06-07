package handlers;

import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AudioOutput {

    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public static boolean soundsEnabled = true;

    /*
        Audioausgabe zum Abspielen von Geräuschen aus Dateien erstellen:
    */

    public static void playSound(String filePath, long durationInMilliseconds) {

        if (soundsEnabled) {

            Thread soundThread = new Thread(() -> {
                try {

                    //Zugriff auf die angegebene Datei:

                    File soundFile = new File(filePath);

                    //Datei laden:

                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

                    //Audioformat der Datei auslesen:

                    AudioFormat audioFormat = audioInputStream.getFormat();

                    //DataLine zum Abspielen der Datei erzeugen:

                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                    SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);

                    //DataLine öffnen:

                    sourceLine.open(audioFormat);

                    //Dateiwiedergabe mithilfe der DataLine starten:

                    sourceLine.start();

                    //Eigenschaften aus der Datei auslesen und diese in Stücken abspielen:
                    int bufferSize = 4096;
                    byte[] buffer = new byte[bufferSize];
                    int bytesRead = 0;

                    while (bytesRead != -1) {
                        bytesRead = audioInputStream.read(buffer, 0, bufferSize);

                        if (bytesRead >= 0) {
                            sourceLine.write(buffer, 0, bytesRead);
                        }
                    }

                    //DataLine schließen:

                    sourceLine.close();

                } catch (Exception e) { e.printStackTrace(); }
            });

            soundThread.start();

            System.out.println("[AudioOutput.java] Sound erfolgreich abgespielt.");

            //Executor erstellen, der die Wiedergabe nach einer festgelegten Zeit stoppt:

            executor.schedule(() -> {
                if (soundThread.isAlive()) {
                    soundThread.interrupt();
                }
            }, durationInMilliseconds, TimeUnit.MILLISECONDS);
        }
    }

    /*
        Deaktivieren der Audioausgabe:
    */

    public static void shutdown() {
        executor.shutdown();
    }
}
