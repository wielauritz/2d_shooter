package handlers;

import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AudioOutput {

    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public static void playSound(String filePath, long durationInMilliseconds) {
        Thread soundThread = new Thread(() -> {
            try {
                // Create a file object with the provided file path
                File soundFile = new File(filePath);

                // Load the sound file
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

                // Get the audio format of the sound file
                AudioFormat audioFormat = audioInputStream.getFormat();

                // Create a data line to play the sound
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);

                // Open the data line
                sourceLine.open(audioFormat);

                // Start playing the sound
                sourceLine.start();

                // Read the sound data and play it in chunks
                int bufferSize = 4096;
                byte[] buffer = new byte[bufferSize];
                int bytesRead = 0;

                while (bytesRead != -1) {
                    bytesRead = audioInputStream.read(buffer, 0, bufferSize);

                    if (bytesRead >= 0) {
                        sourceLine.write(buffer, 0, bytesRead);
                    }
                }

                // Close the data line
                sourceLine.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        soundThread.start();

        System.out.println("[AudioOutput.java] Sound erfolgreich abgespielt.");

        // Schedule a task to stop the sound after the specified duration
        executor.schedule(() -> {
            if (soundThread.isAlive()) {
                soundThread.interrupt();
            }
        }, durationInMilliseconds, TimeUnit.MILLISECONDS);
    }

    public static void shutdown() {
        executor.shutdown();
    }
}
