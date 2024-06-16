package src;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {

    // Method to play a sound file
    public static void playSound(String soundFile) {
        try {
            // Create a File object located in the resources/sound
            File file = new File("resources/sound/" + soundFile);
            
            // get an audio input stream from the sound file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            
            // Get a Clip instance for playing the sound
            Clip clip = AudioSystem.getClip();
            
            // Open the audio stream to the clip
            clip.open(audioStream);
            
            // Play the sound
            clip.start();
        } catch (Exception e) {
            // Print stack trace if an exception happen
            e.printStackTrace();
        }
    }
}
