package tiles.movable;

import character.Id;
import graphics.FrameManager;
import tiles.Tile;

import java.awt.*;

public class Torch extends Tile {
    public static int TILE_SIZE = 80;
    private int frame;
    private int frameDelay;
    public Torch(int x, int y, int width, int height, boolean breakable, Id id) {
        super(x, y, width, height, breakable, id);
        frame = 0;
        frameDelay = 0;

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(FrameManager.getTorchFrame()[frame].getBufferedImage(), x, y,
                width, height, null);
    }

    @Override
    public void update() {
        frameDelay++;
        if (frameDelay >= 10) {
            frame++;
            if (frame >= FrameManager.getTorchFrame().length) {
                frame = 0;
            }
            frameDelay = 0;
        }
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public Rectangle getBoundsTop() {
        return null;
    }

    @Override
    public Rectangle getBoundsBottom() {
        return null;
    }

    @Override
    public Rectangle getBoundsLeft() {
        return null;
    }

    @Override
    public Rectangle getBoundsRight() {
        return null;
    }
}
