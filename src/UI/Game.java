package UI;

import audio.SoundEffectPlayer;
import gameStates.GameStateManager;
import input.Input;
import graphics.SpriteManager;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    public static final String TITLE = "UnderGound";
    private Thread thread;
    private boolean running;
    public static boolean debugMode;
    public static boolean infinityMode;

    //Size
    public static final float DEFAULT_WIDTH = 1280.0f;
    public static final float DEFAULT_HEIGHT = 880.0f;
    public static float widthRatio;
    public static float heightRatio;

    //Resource Manager
    private SpriteManager spriteManager;

    // KeyListener
    private Input keyListener;

    // Current Status
    private GameStateManager gameStateManager;

    private SoundEffectPlayer soundEffectPlayer;

    private static int defaultUpdates = 75;
    public static final int UPDATES = 150;
    public static float UpdatesRatio = defaultUpdates /(float) UPDATES;

    public Game() {
        widthRatio = (float)(Window.scaledGameWidth)/(DEFAULT_WIDTH);
        heightRatio = (float)(Window.scaledGameHeight)/(DEFAULT_HEIGHT);
        running = false;
        debugMode = true;
//        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
//        setPreferredSize(size);
//        setMaximumSize(size);
//        setMinimumSize(size);
    }

    public void paint() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());
        gameStateManager.paint(g);
        g.dispose();
        bs.show();
    }

    public void update() {
        gameStateManager.update();
    }

    public void init() {
        spriteManager = new SpriteManager();

        //Create level
        gameStateManager = new GameStateManager();

        keyListener = new Input();

        //KeyInput listener
        addKeyListener(keyListener);

        //SoundEffect player
        soundEffectPlayer = new SoundEffectPlayer();
    }

    private synchronized void stop() {
        if(!running)return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        running = true;
        init();
        requestFocus();
        long lastTime = System.nanoTime();
        final double amountOfTicks = UPDATES;
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 1000000000.0 / amountOfTicks;
        int frames = 0;
        int updates = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                update();
                updates++;
                delta--;
            }
            paint();
            frames++;
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println(frames + " Frame Per Second " + updates + " Updates Per Second");
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }
}
