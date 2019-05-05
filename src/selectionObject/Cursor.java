package selectionObject;

import fonts.Words;
import graphics.SpriteManager;
import sun.util.locale.provider.SPILocaleProviderAdapter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Cursor {
    private int x;
    private int y;
    private int width;
    private int height;

    //State
    private int pointer;

    //BufferedImage
    private BufferedImage cursorImage;

    //Direction
    private int direction;

    public Cursor(int size, int direction) {
        this.width = size;
        this.height = size;
        this.pointer = 0;
        this.direction = direction;
        if(direction == -1) {
            this.cursorImage = SpriteManager.leftCursor.getBufferedImage();
        }
        else {
            this.cursorImage = SpriteManager.rightCursor.getBufferedImage();
        }

    }
    
    public void setPos(Words[] words){
        if(direction == 1) {
            this.x = words[pointer].getWordX() - words[pointer].getWidth()/2 - width;
            this.y = words[pointer].getWordY() - words[pointer].getHeight()/2 - height;
        }
        else {
            this.x = words[pointer].getWordX() + words[pointer].getWidth()/2 + width;
            this.y = words[pointer].getWordY() + words[pointer].getHeight()/2 - height;
        }

    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void chagePointer(int add,Words[] words){ 
        if(this.pointer+add<0 ||this.pointer+add>=words.length){
        
        }else{
            this.pointer += add;
           // System.out.print(pointer);
        }    
    }
    public int getPointer(){
        return this.pointer;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void paint(Graphics g){
        g.drawImage(cursorImage, x, y, width, height, null);
    }
   
}
