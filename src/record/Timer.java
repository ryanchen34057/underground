package record;

import fonts.Words;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class Timer {
    
    private Words word;
    private int min;
    private int sec;
    private int ms;
    private long startTime;
    private DecimalFormat formatter;
    
    public Timer(){
        formatter = new DecimalFormat("00");
        min = 0;
        sec = 0;
        ms = 0;
        word = new Words(min+":"+sec+ ":" + ms,30,140,50);
        startTime = System.currentTimeMillis();
    }
    public void update(){
        long usedTime = System.currentTimeMillis()- startTime ;
        usedTime = trans(usedTime,1,60*1000);
        usedTime = trans(usedTime,2,1000);
        usedTime = trans(usedTime,3,1);
        word.setWord(formatter.format(min)+":"+formatter.format(sec)+":"+formatter.format(ms/10));
    }
    
    public void paint(Graphics g){
        word.paint(g);
    }
    
    public int trans(long usedTime,int type, int msValue){
        int n = (int)usedTime/msValue;
        if(type == 1){
            min = n;
        }
        if(type == 2){
            sec = n;
        }
        if(type == 3){
            ms = n;
        }
        return (int)usedTime % msValue;
    }
}
