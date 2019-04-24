package UI;

import gameStates.GameStateManager;
import input.Input;
import util.Camera;
import graphics.SpriteManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    //Test particle
    private static final String TITLE = "UI.Game Prototype";
    private Thread thread;
    private boolean running;
    public static boolean debugMode;

    //Size
    public static final int WIDTH = 320;
    public static final int HEIGHT = 220;
    public static final int SCALE = 4;

    // Camera
    public static Camera cam;

    //Resource Manager
    private SpriteManager spriteManager;

    // KeyListener
    private Input keyListener;

    // Background Image
    private BufferedImage img;

    // Current Status
    private GameStateManager gameStateManager;

    public Game() {
        running = false;
        debugMode = false;
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
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
        gameStateManager.handleKeyInput();
    }

    public void init() {
        spriteManager = new SpriteManager();
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        cam = new Camera();

        //Create level1
        gameStateManager = new GameStateManager();
        

        keyListener = new Input();

        //KeyInput listener
        addKeyListener(keyListener);
    }

    private synchronized void start() {
        if(running)return;
        running = true;
        thread = new Thread(this, "Thread");
        thread.start();
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
        init();
        requestFocus();
        long lastTime = System.nanoTime();
        final double amountOfTicks = 75;
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 1000000000.0 / amountOfTicks;
        int frames = 0;
        int ticks = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                update();
                ticks++;
                delta--;
            }
            paint();
            frames++;
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(frames + " Frame Per Second " + ticks + " Updates Per Second");
                frames = 0;
                ticks = 0;
            }
        }
        stop();
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame(TITLE);
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();
    }
}
