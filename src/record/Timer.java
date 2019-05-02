package record;

import fonts.Words;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class Timer {
    private static int MIN_TYPE = 1;
    private static int SEC_TYPE = 2;
    private static int MS_TYPE = 3;
    private Words word;
    private int min;
    private int sec;
    private int ms;
    private long startTime;
    private long usedTime;
    private long pauseStart;
    private DecimalFormat formatter;
    
    public Timer(){
        formatter = new DecimalFormat("00");
        min = 0;
        sec = 0;
        ms = 0;
        word = new Words(min+":"+sec+ ":" + ms,30,140,50);
        startTime = System.currentTimeMillis();
        pauseStart = 0;
    }

    public void timerPause(){
        if(pauseStart == 0){
            pauseStart = System.currentTimeMillis();//設定暫停的時間點
        }
    }
    public void pauseEnd(){
        if(pauseStart != 0){
            startTime  += (System.currentTimeMillis()-pauseStart);//切回遊戲，開始時間後移
            pauseStart = 0; //歸零
        }   
    }   
   
    public void update(){
        usedTime = System.currentTimeMillis()- startTime ;
        usedTime = trans(usedTime,MIN_TYPE,60*1000);
        usedTime = trans(usedTime,SEC_TYPE,1000);
        usedTime = trans(usedTime,MS_TYPE,1);
        word.setWord(formatter.format(min)+":"+formatter.format(sec)+":"+formatter.format(ms/10));
    }
    
    
    public void paint(Graphics g){
        word.paint(g);
    }
    
    public int trans(long usedTime,int type, int msValue){
        int n = (int)usedTime/msValue;
        if(type == MIN_TYPE){
            min = n;
        }
        if(type == SEC_TYPE){
            sec = n;
        }
        if(type == MS_TYPE){
            ms = n;
        }
        return (int)usedTime % msValue;
    }
}
