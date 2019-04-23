package effects;

import enums.Id;
import gameObject.ICollidable;
import gameObject.character.Player;
import gameObject.tiles.movable.FallingRock;
import graphics.FrameManager;

import java.awt.*;

public class LandingEffect extends Effect {
    public static int EFFECT_SIZE = 100;
    private LandingEffect(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        frame = 0;
        frameDelay = 0;
    }

    public static LandingEffect getInstance(ICollidable ic) {
        if(ic instanceof Player) {
            return new LandingEffect(((Player) ic).getX()+15, ((Player) ic).getY()+30, 64, 64, Id.landingEffect);
        }
        if(ic instanceof FallingRock) {
            return new LandingEffect(((FallingRock) ic).getX()-55, ((FallingRock) ic).getY()-40, 250, 250, Id.rockLandingEffect);
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
