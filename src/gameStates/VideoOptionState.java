package gameStates;

import com.sun.media.jfxmedia.track.VideoResolution;

import java.awt.*;
import java.util.ArrayList;

public class VideoOptionState extends GameState {
    private ArrayList<Dimension> sizes;
    public VideoOptionState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        sizes = new ArrayList<>();


    }

    @Override
    public void handleKeyInput() {

    }

    @Override
    public void update() {

    }

    @Override
    public void paint(Graphics g) {

    }
}
