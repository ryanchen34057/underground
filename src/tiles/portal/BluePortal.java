package tiles.portal;

import UI.Game;
import enums.Id;
import graphics.FrameManager;
import tiles.Tile;

import java.awt.*;

public class BluePortal extends Tile {
    public static final int PORTAL_SIZE = 200;
    private int frame;
    private int frameDelay;
    public BluePortal(int x, int y, int width, int height, boolean breakable, Id id) {
        super(x, y, width, height, breakable, id);
        frame = 0;
        frameDelay = 0;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(FrameManager.getBluePortalFrame()[frame].getBufferedImage(), x, y,
                width, height, null);

        if (Game.debugMode) {
            g.setColor(Color.RED);
            g.drawRect(getX()+20, getY(), super.getWidth()-50, getHeight());
        }
    }

    @Override
    public void update() {
        frameDelay++;
        if (frameDelay >= 3) {
            frame++;
            if (frame >= FrameManager.getBluePortalFrame().length) {
                frame = 0;
            }
            frameDelay = 0;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+20, y, width-50, height);
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
