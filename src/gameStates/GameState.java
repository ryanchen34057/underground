package gameStates;

import gameStates.level1.Level1AState;
import map.Background;
import util.Camera;
import util.Handler;

import java.awt.*;

public abstract class GameState {
    protected GameStateManager gameStateManager;
    protected Background background;
    protected Handler handler;
    protected int deathDelay;
    public static final int DEATH_DELAY_TIME = 20;
    public static  Camera cam;

    public GameState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        handler = new Handler();
        cam = new Camera();
        deathDelay = 0;
    }

    public abstract void handleKeyInput();
    public abstract void update();
    public abstract void paint(Graphics g);
}
