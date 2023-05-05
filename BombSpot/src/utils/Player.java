package utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Player {
    public static void playSound(String file, float volume, boolean loop){
        File soundFile = new File(file);
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
        if (loop){
            Clip finalClip = clip;
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        finalClip.setFramePosition(0);
                        finalClip.start();
                    }
                }
            });
        }
        clip.start();
    }
}
