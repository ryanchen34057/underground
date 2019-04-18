package tiles.portal;

import UI.Game;
import character.Id;
import graphics.FrameManager;
import tiles.Tile;

import java.awt.*;

public class PurplePortal extends Tile {
    public static final int PORTAL_SIZE = 200;
    private int frame;
    private int frameDelay;
    public PurplePortal(int x, int y, int width, int height, boolean breakable, Id id) {
        super(x, y, width, height, breakable, id);
        frame = 0;
        frameDelay = 0;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(FrameManager.getPurplePortalFramePortalFrame()[frame].getBufferedImage(), x, y,
                width, height, null);

        if (Game.debugMode) {
            g.setColor(Color.MAGENTA);
            g.drawRect(x+20, y, width-50, height);
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
