
package fonts;

import UI.Game;
import util.Camera;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;



public class Words {
    String msg;
    int x;
    int y;
    int size;
    
    
    public Words(String msg,int size,int x,int y){
        this.msg = msg;
        this.x = x;
        this.y = y;
        this.size = size;
    }
    public void paint(Graphics g){
        Font font1 = Fonts.getBitFont(size);
        g.setFont(font1);
        g.setColor(Color.LIGHT_GRAY);
        FontMetrics fm = g.getFontMetrics();
        int sw = fm.stringWidth(msg);
        int sa = fm.getAscent();
        g.drawString(msg, x-sw/2,y-sa/2);
    }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
