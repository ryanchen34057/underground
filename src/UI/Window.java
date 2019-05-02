package UI;

import audio.MusicPlayer;
import util.ThreadPool;

import javax.swing.*;

public class Window {
    public static MusicPlayer musicPlayer;
    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool(2);
        Game game = new Game();
        JFrame frame = new JFrame(Game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
//        game.start();
        musicPlayer = new MusicPlayer();
        musicPlayer.add("Desert-Theme");
        pool.runTask(musicPlayer);
        pool.runTask(game);
        pool.join();
    }
}
