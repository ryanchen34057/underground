package gameStates;

import map.Background;
import java.awt.*;

public abstract class GameState {
    protected GameStateManager gameStateManager;
    protected Background background;
    protected int deathDelay;
    public static final int DEATH_DELAY_TIME = 20;


    public GameState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        deathDelay = 0;
    }

    public abstract void init();
    public abstract void handleKeyInput();
    public abstract void update();
    public abstract void paint(Graphics g);
}
