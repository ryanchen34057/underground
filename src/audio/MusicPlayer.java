package audio;

import java.util.ArrayList;

public class MusicPlayer implements Runnable {
    private ArrayList<AudioFile> musicFiles;
    private int currentSongIndex;
    public static boolean running;
    public static boolean isOn;

    public MusicPlayer() {
        running = true;
        isOn = true;
        musicFiles = new ArrayList<>();
    }

    public void add(String file) {
        musicFiles.add(new AudioFile("./src/audio/res/" + file + ".wav"));
    }

    public int size() {
        return musicFiles.size();
    }

    @Override
    public void run() {
        AudioFile song = musicFiles.get(currentSongIndex);
        while(running) {
            if(!isOn) {
                song.stop();
            }
            else {
                song.play();
            }
//            if(!song.isPlaying()) {
//                currentSongIndex++;
//                if(currentSongIndex >= musicFiles.size()) {
//                    currentSongIndex = 0;
//                }
//                song = musicFiles.get(currentSongIndex);
//                song.play();
//            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
