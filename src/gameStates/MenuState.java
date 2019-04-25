
package gameStates;

import UI.Game;
import cursor.Cursor;
import fonts.Words;
import gameStates.level1.Level1AState;
import gameStates.level1.Level1BState;
import input.Input;
import java.awt.Graphics;
import map.Background;


public class MenuState extends GameState{
    private Words wordTitle;
    private Words wordStart;
    private Words wordOption;
    private Words wordExit;
    private Cursor cursor;
    private Words[] words ;
    private boolean locked;
 
    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
        init(); 
    }
    
    public void init() {
        locked = false;
        background = new Background("/res/Cave1.png", Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
        wordTitle = new Words("UnderGround", 80, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2-120);
        wordStart = new Words("Start", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+80);
        wordOption = new Words("Option", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+140);
        wordExit = new Words("Exit", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+200);
        words = new Words[3];
        words[0] = wordStart;
        words[1] = wordOption;
        words[2] = wordExit;
        cursor = new Cursor(0,0,32);
       
    }

    @Override
    public void handleKeyInput() {
        if(!locked){
            if(Input.keys.get(7).down){//Enter
                switch(cursor.getPointer()){
                    case 0:
                        gameStateManager.setGameState(new Level1AState(gameStateManager));
                        locked = true;
                        break;
                    case 1:
                        break;
                    case 2:
                        System.exit(0);
                        break;
                }        
                
            }
            if(Input.keys.get(0).down){//上
                cursor.chagePointer(-1, words);
                System.out.println("up");
                locked = true;
            }
            if(Input.keys.get(1).down){//下
                cursor.chagePointer(1, words);
                System.out.println("down");
                locked = true;
            }
        }
        if(!Input.keys.get(0).down &&!Input.keys.get(1).down){//放開
            locked = false;
        }

    }
    @Override   
    public void update() {
        if(cursor != null){
            cursor.setPos(words);
        }
    }
    @Override
    public void paint(Graphics g) {
        background.paint(g);
        wordTitle.paint(g);
        wordStart.paint(g);
        wordOption.paint(g);
        wordExit.paint(g);
        cursor.paint(g);
    }
}
