package UI;

import audio.MusicPlayer;
import util.ThreadPool;

import javax.swing.*;
import java.awt.*;

public class Window {
    private static MusicPlayer musicPlayer;
    private static JFrame frame;
    private static Game game;
    private static int canvasWidth = 0;
    private static int canvasHeight = 0;
    private static final int DEFAULT_WIDTH = 320;
    private static final int DEFAULT_HEIGHT = 220;
    public static int gameWidth = 0;
    public static int gameHeight = 0;
    public static int scale = 3;
    public static int scaledGameWidth;
    public static int scaledGameHeight;
    public static int screenWidth;
    public static int screenHeight;

    public static void makeFullScreen() {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        if(graphicsDevice.isFullScreenSupported()) {
            frame.dispose();
            frame.setUndecorated(true);
            graphicsDevice.setFullScreenWindow(frame);
        }
    }

    public static String[] getSizeList() {
        String[] sizeList = new String[screenWidth / gameWidth];
        for(int i=0;i<screenWidth / gameWidth;i++) {
            sizeList[i] = "  " + (gameWidth*(i+1)) + " x " + (gameHeight*(i+1)) + "  ";
        }
        return sizeList;
    }

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
        pool.runTask(musicPlayer);
        pool.runTask(game);
        pool.join();

    }

    public static void main(String[] args) {
        new Window();
    }
}
