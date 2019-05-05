package effects;

import UI.Game;
import enums.Id;
import gameObject.ICollidable;
import gameObject.character.Player;
import gameObject.tiles.movable.FallingRock;
import graphics.FrameManager;

import java.awt.*;

public class LandingEffect extends Effect {
    public static int EFFECT_SIZE = (int)(250 * Game.widthRatio);
    private LandingEffect(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        frame = 0;
        frameDelay = 0;
    }

    public static LandingEffect getInstance(ICollidable ic) {
        if(ic instanceof Player) {
            return new LandingEffect(((Player) ic).getX()+(int)(Player.WIDTH*0.16), ((Player) ic).getY()+(int)(Player.HEIGHT*0.3125), (int)(64 * Game.widthRatio), (int)(64 * Game.widthRatio), Id.landingEffect);
        }
        if(ic instanceof FallingRock) {
            return new LandingEffect(((FallingRock) ic).getX()-(int)(((FallingRock) ic).getWidth()*0.29), ((FallingRock) ic).getY()-(int)(((FallingRock) ic).getHeight()*0.21), EFFECT_SIZE, EFFECT_SIZE, Id.rockLandingEffect);
        }
        return null;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(FrameManager.getEffectFrame(id)[frame].getBufferedImage(), x, y,
                width, height, null);
    }

    @Override
    public void update() {
        frameDelay++;
        if (frameDelay >= 1) {
            frame++;
            if (frame >= 17) {
                frame = 0;
                die();
            }
            frameDelay = 0;
        }
    }
}
