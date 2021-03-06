package fonts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.HashMap;

public class Words {
    String msg;
    int x;
    int y;
    int size;
    int sWidth;
    int sHeight;
    private static HashMap<Integer, Font> fontMap;
    private Font font;
    private Color color;

    public Words(String msg,int size,int x,int y){
        this.msg = msg;
        this.x = x;
        this.y = y;
        this.size = size;
        if(fontMap == null) {
            fontMap = new HashMap<>();
        }
        if(!fontMap.containsKey(size)){
            fontMap.put(size, Fonts.getBitFont(size, "/res/half_bold_pixel-7.ttf"));
        }
        font = fontMap.get(size);
        color = Color.LIGHT_GRAY;
    }
    public Words(String msg, int size) {
        this.msg = msg;
        this.size = size;
        if(fontMap == null) {
            fontMap = new HashMap<>();
        }
        if(!fontMap.containsKey(size)){
            fontMap.put(size, Fonts.getBitFont(size, "/res/half_bold_pixel-7.ttf"));
        }
        font = fontMap.get(size);
    }

    public void update(String msg) {
        this.msg = msg;
    }

    public void paint(Graphics g){
        g.setFont(font);
        g.setColor(color);
        FontMetrics fm = g.getFontMetrics();
        int sw = fm.stringWidth(msg);
        sWidth = sw;
        int sa = fm.getAscent();
        sHeight = sa;
        g.drawString(msg, x-sw/2,y-sa/2);
    }

    
    public void addWordY(int y) {
        this.y += y;
    }
    
    public void setWord(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

     public void setWordX(int x) {
            this.x = x;
      }
    
    public int getWordX(){
        return this.x;
    }
    public int getWordY(){
        return this.y;
    }
    public int getWidth(){
        
        return sWidth;    
    }
    public int getHeight(){
        return sHeight;
    }
    

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
