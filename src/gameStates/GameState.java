package gameStates;

import map.Background;
import util.Camera;
import util.Handler;

import java.awt.*;

public abstract class GameState {
    protected Background background;
    protected Handler handler;
    protected Camera cam;

    public GameState() {
        handler = new Handler();
        cam = new Camera();
    }

    public abstract void handleKeyInput();
    public abstract void update();
    public abstract void paint(Graphics g);
}
