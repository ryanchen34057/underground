package effects;

import enums.Direction;
import gameObject.character.Player;
import enums.Id;
import graphics.FrameManager;

import java.awt.*;

import static enums.Direction.DOWN;
import static enums.Direction.UP;

public class VerticalDashEffect extends Effect {
    private int direction;
    private Direction dir;
    public final static int EFFECT_SIZE = 150;
    public VerticalDashEffect(int x, int y, int width, int height, int direction,Direction dir,  Id id) {
        super(x, y, width, height, id);
        this.direction = direction;
        this.dir = dir;
        frame = 0;
        frameDelay = 0;
    }



    public static VerticalDashEffect getInstance(Player player, Direction direction) {
        return  new VerticalDashEffect(player.getX()
                , player.getY()-20, EFFECT_SIZE, EFFECT_SIZE, player.getFacing(), direction, Id.verticalDashEffect);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(FrameManager.getVerticalDashEffectFrame(dir)[frame].getBufferedImage(), x, y,
                    width, height, null);
    }

    @Override
    public void update() {
        frameDelay++;
        if (frameDelay >= 3) {
            frame++;
            if (frame >= 12) {
                frame = 0;
                die();
            }
            frameDelay = 0;
        }
    }
}
