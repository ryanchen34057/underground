package effects;

import character.Id;
import character.Player;
import graphics.FrameManager;
import util.Handler;

import java.awt.*;

public class DashEffect extends Effect{
    public static int EFFECT_SIZE = 100;
    private int direction;

    private DashEffect(int x, int y, int width, int height, int direction, Id id) {
        super(x, y, width, height, id);
        frame = 0;
        frameDelay = 0;
        this.direction = direction;
    }

    public static DashEffect getInstance(Player player) {
        return  new DashEffect(player.getX() + -player.getFacing() * player.getWidth() + 15
                , player.getY(), DashEffect.EFFECT_SIZE, DashEffect.EFFECT_SIZE, player.getFacing(), Id.dashEffect);
    }

    @Override
    public void paint(Graphics g) {
        if(direction == 1) {
            g.drawImage(FrameManager.getEffectFrame()[frame].getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
        }
        else {
            g.drawImage(FrameManager.getEffectFrame()[frame+15].getBufferedImage(), super.getX(), super.getY(),
                        super.getWidth(), super.getHeight(), null);
        }

    }

    @Override
    public void update() {
        frameDelay++;
        if (frameDelay >= 1) {
            frame++;
            if (frame >= FrameManager.effectFrame.length / 2) {
                frame = 0;
                Handler.effectRemove();
            }
            frameDelay = 0;
        }
    }
}
