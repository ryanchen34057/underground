package UI;

import audio.MusicPlayer;
import util.ThreadPool;

import javax.swing.*;
import java.awt.*;

public class Window {
    public static MusicPlayer musicPlayer;
    public static JFrame frame;
    public static Game game;
    public static int canvasWidth = 0;
    public static int canvasHeight = 0;
    public static final int DEFAULT_WIDTH = 320;
    public static final int DEFAULT_HEIGHT = 220;
    public static int gameWidth = 0;
    public static int gameHeight = 0;
    public static int scale = 3;
    public static int scaledGameWidth;
    public static int scaledGameHeight;
    public static int screenWidth;
    public static int screenHeight;


    private static void getBestSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        boolean done = false;
        while(!done) {
            canvasWidth += DEFAULT_WIDTH;
            canvasHeight += DEFAULT_HEIGHT;
            if(canvasWidth > screenSize.width || canvasHeight > screenSize.height) {
                canvasWidth -= DEFAULT_WIDTH;
                canvasHeight -= DEFAULT_HEIGHT;
                done = true;
            }
        }
        int xDiff = screenSize.width - canvasWidth;
        int yDiff = screenSize.height - canvasHeight;
        int factor = canvasWidth / DEFAULT_WIDTH;

        gameWidth = canvasWidth / factor + xDiff / factor;
        gameHeight = canvasHeight / factor + yDiff / factor;
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
    }


    public static String[] getSizeList() {
        String[] sizeList = new String[2];
        for(int i=2;i<4;i++) {
            sizeList[i-2] = "  " + (Window.gameWidth*(i+1)) + " x " + (Window.gameHeight*(i+1)) + "  ";
        }
        return sizeList;
    }

    public Window() {
        getBestSize();
        ThreadPool pool = new ThreadPool(2);
        scaledGameWidth = gameWidth * scale;
        scaledGameHeight = gameHeight * scale;
        game = new Game();
        frame = new JFrame(Game.TITLE);
        game.setPreferredSize(new Dimension(scaledGameWidth, scaledGameHeight));
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        musicPlayer = new MusicPlayer();
        musicPlayer.add("background");
        musicPlayer.add("lastLevelBackground");
        musicPlayer.add("endGame");
        pool.runTask(musicPlayer);
        pool.runTask(game);
        pool.join();

    }

    public static void main(String[] args) {
        new Window();
    }
}
