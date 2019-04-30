
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

    }
    public void update(String msg) {
        this.msg = msg;
    }
    public void paint(Graphics g){
        g.setFont(font);
        g.setColor(Color.LIGHT_GRAY);
        FontMetrics fm = g.getFontMetrics();
        int sw = fm.stringWidth(msg);
        sWidth = sw;
        int sa = fm.getAscent();
        sHeight = sa;
        g.drawString(msg, x-sw/2,y-sa/2);
    }
    public void setWord(String msg){
        this.msg = msg;
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
    

    public void updatePos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
