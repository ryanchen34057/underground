package audio;

import javax.sound.sampled.*;
import java.io.File;


public class AudioFile implements LineListener{
    private File soundFile;
    private AudioInputStream ais;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip clip;
    private  FloatControl gainControl;
    private boolean playing;
    public static float volume;
    private static final float DEFAULT_VOLUME = -10;

    public AudioFile(String fileName) {
        soundFile = new File(fileName);
        try {
            ais = AudioSystem.getAudioInputStream(soundFile);
            format = ais.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.addLineListener(this);
            clip.open(ais);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume = DEFAULT_VOLUME;

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public void play() {
        play(volume);
    }

    public void play(float volume) {
        gainControl.setValue(volume);
        clip.start();
        playing = true;
    }

    public void stop() {
        clip.stop();
    }

    @Override
    public void update(LineEvent event) {
        if(event.getType() == LineEvent.Type.START) {
            playing = true;
        }
        else if(event.getType() == LineEvent.Type.STOP) {
            clip.stop();
            clip.flush();
            clip.setFramePosition(0);
            playing = false;
        }
    }
}
