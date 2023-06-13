package handlers;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
    AudioOutput.java
    Aktivieren der Tonausgabe sowie Verarbeitung und abspielen von Tondateien
    Geschrieben von Lauritz Wiebusch
 */

public class AudioOutput {

    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public static boolean soundsEnabled = Database.areSoundsEnabled();
    public static boolean musicEnabled = Database.isMusicEnabled();

    private static AudioInputStream audioInputStream;

    private static SourceDataLine sourceLine;

    private static Thread soundThread;
    private static Thread musicThread;

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

                    // Set the volume to 75% (0.75):

                    FloatControl gainControl = (FloatControl) sourceLine.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(20f * (float) Math.log10(0.75));

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

                } catch (Exception ignored) {
                }
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
        Audioausgabe zum Abspielen von Spielmusik erstellen:
    */

    public static void playMusic() {

        // Previous playback stop:

        if (musicThread != null && musicThread.isAlive()) {
            musicThread.interrupt();
        }

        // Access the music file:

        musicThread = new Thread(() -> {
            File musicFile = new File("audio/components/Program/music.wav");
            AudioFormat audioFormat = null;
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];

            // Read the audio format of the file:

            try {
                audioFormat = AudioSystem.getAudioInputStream(musicFile).getFormat();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            while (!Thread.currentThread().isInterrupted()) {
                if (musicEnabled) {
                    try {

                        // Load the file:

                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);

                        // Create the DataLine for playing the file:

                        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                        SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);

                        // Open the DataLine:

                        sourceLine.open(audioFormat);

                        // Set the volume to 25% (0.25):

                        FloatControl gainControl = (FloatControl) sourceLine.getControl(FloatControl.Type.MASTER_GAIN);
                        gainControl.setValue(20f * (float) Math.log10(0.25));

                        // Start playing the music using the DataLine:

                        sourceLine.start();

                        int bytesRead;
                        while ((audioInputStream != null) && (bytesRead = audioInputStream.read(buffer, 0, bufferSize)) != -1 && musicEnabled) {
                            sourceLine.write(buffer, 0, bytesRead);
                        }

                        // Close the DataLine:

                        sourceLine.drain();
                        sourceLine.stop();
                        sourceLine.close();
                        audioInputStream.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });

        musicThread.start();
    }


    /*
        Deaktivieren der Audioausgabe:
    */

    public static void shutdown() {
        // Stop the music thread if it's running
        if (musicThread != null && musicThread.isAlive()) {
            musicThread.interrupt();
            musicThread = null; // Set musicThread to null after interruption
        }

        // Close the audioInputStream if it's not null
        if (audioInputStream != null) {
            try {
                audioInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            audioInputStream = null; // Set audioInputStream to null after closing
        }
    }


}
