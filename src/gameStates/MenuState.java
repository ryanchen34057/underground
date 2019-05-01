
package gameStates;

import UI.Game;
import UI.Window;
import selectionObject.Cursor;
import fonts.Words;
import input.Input;

import java.awt.*;

import map.Background;


public class MenuState extends GameState{
    private GameStateManager gameStateManager;
    private Words[] words ;
    private Cursor cursor;
    private Words wordTitle;
    private Words wordStart;
    private Words wordOption;
    private Words wordExit;
    private Words wordLeaderboard;



    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
        init();
        this.gameStateManager = gameStateManager;
    }
    
    public void init() {
        background = new Background("/res/Cave1.png", Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
        wordTitle = new Words("UnderGround", 80, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2-120);
        wordStart = new Words("Start", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+80);
        wordLeaderboard = new Words("Leaderboard", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+140);
        wordOption = new Words("Option", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+200);
        wordExit = new Words("Exit", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+260);
        words = new Words[4];
        words[0] = wordStart;
        words[1] = wordLeaderboard;
        words[2] = wordOption;
        words[3] = wordExit;
        cursor = new Cursor(0,0,32);
    }

    @Override
    public void handleKeyInput() {
        if(!locked){
            if(Input.keys.get(7).down){//Enter
                switch(cursor.getPointer()){
                    case 0:
                        gameStateManager.setGameState(new SaveSlotState(gameStateManager));
                        locked = true;
                        break;
                    case 1:
                        
                        break;
                    case 2:
                        gameStateManager.setGameState(new OptionState(gameStateManager));
                        locked = true;
                        break;
                    case 3:
                        System.exit(0);
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
        wordStart.paint(g);
        wordLeaderboard.paint(g);
        wordOption.paint(g);
        wordExit.paint(g);
        cursor.paint(g);
    }

    @Override
    public String toString(){
        return "MenuState";
    }
}
