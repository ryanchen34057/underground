
package gameStates;

import UI.Game;
import selectionObject.Cursor;
import fonts.Words;
import input.Input;
import java.awt.Graphics;

import map.Background;
import record.Record;


public class PauseState extends GameState{
    private GameStateManager gameStateManager;
    private Words[] words ;
    private Words wordTitle;
    private Words wordResume;
    private Words wordRetry;
    private Words wordSelect;
    private Words wordMenu;
    private Words wordSaveQuit;
    private Cursor cursor;
    
    public PauseState(GameStateManager gameStateManager) {
        super(gameStateManager);
        this.gameStateManager = gameStateManager;
        init(); 
    }
    
    public void init() {
        background = new Background("/res/Cave1.png", 2);
        wordTitle = new Words("Pause", 60, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2-180);
        wordResume = new Words("Resume", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2-40);
        wordRetry = new Words("Retry", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+20);
        wordSelect = new Words("Select Level", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+80);
        wordMenu = new Words("Back to Menu", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+140);
        wordSaveQuit = new Words("Save & Quit", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+200);
        words = new Words[5];
        words[0] = wordResume;
        words[1] = wordRetry;
        words[2] = wordSelect;
        words[3] = wordMenu;
        words[4] = wordSaveQuit;
        cursor = new Cursor(0,0,32);
    }
    
    @Override
    public void handleKeyInput() {
        if(!locked){
            if(Input.keys.get(7).down){//Enter
                switch(cursor.getPointer()){
                    case 0:
                        gameStateManager.back();
                        locked = true;
                        break;
                    case 1:
                        break;
                    case 2:
                        gameStateManager.toMenu();
                        locked = true;
                        break;
                    case 3:
                        break;
                    case 4:
                        gameStateManager.saveAndWriteRecord(new Record(gameStateManager.getSlotId(), gameStateManager.getTimer().toString(), gameStateManager.getEmeraldCount(), gameStateManager.getCurrentLevel(), gameStateManager.getDeathCount()));
                        System.exit(0);
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
        wordResume.paint(g);
        wordRetry.paint(g);
        wordSelect.paint(g);
        wordMenu.paint(g);
        wordSaveQuit.paint(g);
        cursor.paint(g);
    }
}
