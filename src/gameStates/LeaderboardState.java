package gameStates;

import UI.Game;
import fonts.Words;
import static gameStates.GameState.locked;
import input.Input;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import map.Background;
import selectionObject.Cursor;

public class LeaderboardState extends GameState{
    private static int MIN_TYPE = 1;
    private static int SEC_TYPE = 2;
    private static int MS_TYPE = 3;
    private GameStateManager gameStateManager;
    private Words wordTitle;
    private Words wordRecord1;
    private Words wordRecord2;
    private Words wordRecord3;
    private Words wordBack;
    //讀檔
    private BufferedReader brfile;
    private int[] bestRecord;
    //光標
    private Words[] words ;
    private Cursor cursor;
    
    
    public LeaderboardState(GameStateManager gameStateManager) {
        super(gameStateManager);
        init();
        this.gameStateManager = gameStateManager;    
    }
    
    public void init() {
        
        background = new Background("/res/Cave1.png", Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
        wordTitle = new Words("Leaderboard", 60, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2-180);
        this.getRecordFile();
        wordRecord1 = new Words("Record1", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+-40);
        wordRecord2 = new Words("Record2", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+20);
        wordRecord3 = new Words("Record3", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+80);
        wordBack = new Words("Back", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+200);
        
         
        words = new Words[1];
        words[0] = wordBack;
        cursor = new Cursor(0,0,32);
    }
    public void getRecordFile(){
        try{
            brfile= new BufferedReader(new FileReader("BestRecord.txt"));
            int count = 0;
            while (brfile.ready()) {                
                bestRecord[count++] = Integer.valueOf(brfile.readLine());    
            }

            brfile.close();
        }catch(IOException ex){
            System.out.println("找不到資料!");   
        }    
    }
 
    @Override
    public void handleKeyInput() {
        if(!locked){
            if(Input.keys.get(7).down){//Enter
                switch(cursor.getPointer()){
                    case 0:
                        gameStateManager.back();
                        locked = true;
                        locked = true;
                        break;
                }            
            }
            if(Input.keys.get(0).down){//上
                cursor.chagePointer(-1, words);
                locked = true;
            }
            if(Input.keys.get(1).down){//下
                cursor.chagePointer(1, words);
                locked = true;
            }
        }
        if(!Input.keys.get(0).down &&!Input.keys.get(1).down && !Input.keys.get(7).down){//放開
            locked = false;
        }
    }
    @Override   
    public void update() {
        if(cursor != null){
            cursor.setPos(words);
        }
        handleKeyInput();
    }
    @Override
    public void paint(Graphics g) {
        background.paint(g);
        wordTitle.paint(g);
        wordRecord1.paint(g);
        wordRecord2.paint(g);
        wordRecord3.paint(g);
        wordBack.paint(g);
        cursor.paint(g);
    }
}
