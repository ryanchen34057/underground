package effects;

import character.Player;
import enums.Id;
import graphics.FrameManager;
import util.Handler;

import java.awt.*;

public class VerticalDashEffect extends Effect {
    private int direction;
    public final static int EFFECT_SIZE = 150;
    public VerticalDashEffect(int x, int y, int width, int height, int direction, Id id) {
        super(x, y, width, height, id);
        this.direction = direction;
        frame = 0;
        frameDelay = 0;
    }



    public static VerticalDashEffect getInstance(Player player) {
        return  new VerticalDashEffect(player.getX()
                , player.getY()-20, EFFECT_SIZE, EFFECT_SIZE, player.getFacing(), Id.verticalDashEffect);
    }

    @Override
    public void paint(Graphics g) {
        if(direction == 1) {
            g.drawImage(FrameManager.getEffectFrame(id)[frame].getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
        }
        else {
            g.drawImage(FrameManager.getEffectFrame(id)[frame+12].getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
        }
    }

    @Override
    public void update() {
        frameDelay++;
        if (frameDelay >= 3) {
            frame++;
            if (frame >= 12) {
                frame = 0;
                Handler.effectRemove();
            }
            frameDelay = 0;
        }
    }
}
