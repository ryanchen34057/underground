package cursor;

import fonts.Words;
import graphics.SpriteManager;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Cursor {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    //State
    protected int pointer;

    //BufferedImage
    protected BufferedImage cursorImage;
    
    public Cursor() {
        
    }

    public Cursor(int x, int y, int size) {
        this.width = size;
        this.height = size;
        this.x  = x ;
        this.y  = y ;
        this.pointer = 0;
        this.cursorImage = SpriteManager.cursor;
    }
    
    public void setPos(Words[] words){
        this.x = words[pointer].getWordX() - words[pointer].getWidth()/2 - width;
        this.y = words[pointer].getWordY() - words[pointer].getHeight()/2 - height;
    }
    public void chagePointer(int add,Words[] words){ 
        if(this.pointer+add<0 ||this.pointer+add>=words.length){
        
        }else{
            this.pointer += add;
            System.out.print(pointer);
        }    
    }
    public int getPointer(){
        return this.pointer;
    }
   
    public void paint(Graphics g){
        g.drawImage(cursorImage, x, y, width, height, null);
    }
   
}
