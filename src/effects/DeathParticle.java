package effects;

import graphics.FrameManager;
import util.Handler;

import java.awt.*;


public class DeathParticle extends ParticleSystem {
    public static final int EFFECT_SIZE = 64;
    public static final int RANGE  = 70;
    private static final int DELAY = 1;
    private int xD;
    private int yD;
    private int distance;
    private int delayCount;
    private int frame;

    public DeathParticle(int x, int y, int destX, int destY, int size, int life) {
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
            Handler.particles.remove(this);
            frame = 0;
        }
    }

    private boolean getDest(int d, int v, int dest){
        return (d >= 0)?v <= dest:v >= dest;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(FrameManager.getDeathFrame()[frame].getBufferedImage(), x, y, size, size, null);
    }

    @Override
    public String toString() { return x + "," + y; }
}
