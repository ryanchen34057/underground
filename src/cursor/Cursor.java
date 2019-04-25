package cursor;

import java.awt.image.BufferedImage;

public class Cursor {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    //State
    protected int pointer;

    //BufferedImage
    protected BufferedImage bufferedImage;

    public Cursor(int x, int y, int width, int height, BufferedImage bufferedImage) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bufferedImage = bufferedImage;   
    }
    
    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }
    
}
