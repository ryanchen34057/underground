package effects;

import UI.Game;
import gameObject.character.Player;
import graphics.FrameManager;
import java.awt.*;


public class DeathParticle extends ParticleSystem {
    public static final int EFFECT_SIZE = (int)(64 * Game.widthRatio);
    public static final int RANGE  = 70;
    private static final int DELAY = 1;
    private int xD;
    private int yD;
    private int distance;
    private int delayCount;
    private int frame;

    private DeathParticle(int x, int y, int destX, int destY, int size, int life) {
        super(x, y, destX, destY, size, life);
        xD = (destX - x)>0?1:-1;
        yD = (destY - y)>0?1:-1;
        if(Math.abs((destX - x)) != RANGE && Math.abs((destY - y)) != RANGE){
            distance = 3;
        }else{
            distance = 4;
        }
        delayCount = 0;
        frame = 0;
    }

    public static DeathParticle getInstance(Player player, int index) { return new DeathParticle(player.getX()-(int)player.getVelX(),
            player.getY()-(int)player.getVelY(), (int)(DeathParticle.RANGE * Math.cos(index * 45 * 2 * Math.PI / 360) + player.getX()-(int)player.getVelX()),
            (int)(DeathParticle.RANGE * Math.sin(index * 45 * 2 * Math.PI / 360) + player.getY()-(int)player.getVelY()), EFFECT_SIZE, 5); }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(FrameManager.getDeathFrame()[frame].getBufferedImage(), x, y, size, size, null);
    }

    @Override
    public void update() {
        if(delayCount++ < DELAY){
            return;
        }
        if(frame < 7) {
            frame++;
        }
        delayCount = 0;
        if(getDest(xD, x, destX)) {
            x += xD * distance;
        }
        if(getDest(yD, y, destY)){
            y += yD * distance;
        }
        if(!getDest(xD, x, destX) && !getDest(yD, y, destY)){
            frame = 0;
            die();
        }
    }

    private boolean getDest(int d, int v, int dest){
        return (d >= 0)?v <= dest:v >= dest;
    }

    @Override
    public String toString() { return x + "," + y; }
}
