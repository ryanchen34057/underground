package audio;

import java.util.ArrayList;

public class MusicPlayer implements Runnable {
    private static ArrayList<AudioFile> musicFiles;
    private static AudioFile currentSong;
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

    public static void changeSong(int index) {
        currentSong = musicFiles.get(index);
    }

    @Override
    public void run() {
        if(currentSong == null) {
            currentSong = musicFiles.get(0);
        }
        while(running) {
            if(!isOn) {
                currentSong.stop();
            }
            else {
                currentSong.play();
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
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
