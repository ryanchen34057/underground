
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
    private long pauseTime;
    
    public PauseState(GameStateManager gameStateManager) {
        super(gameStateManager);
        this.gameStateManager = gameStateManager;
        init(); 
    }
    
    public void init() {
        background = new Background("/res/Cave1.png",1);
        wordTitle = new Words("Pause", (int)(Game.WIDTH*Game.SCALE*0.0469), Game.WIDTH*Game.SCALE/2, (int)(Game.HEIGHT*Game.SCALE/3));
        wordResume = new Words("Resume", (int)(Game.WIDTH*Game.SCALE*0.03125), Game.WIDTH*Game.SCALE/2, (int)(Game.HEIGHT*Game.SCALE/2));
        wordRetry = new Words("Retry", (int)(Game.WIDTH*Game.SCALE*0.03125), Game.WIDTH*Game.SCALE/2, (int)(Game.HEIGHT*Game.SCALE/1.75));
        wordSelect = new Words("Select Level", (int)(Game.WIDTH*Game.SCALE*0.03125), Game.WIDTH*Game.SCALE/2, (int)(Game.HEIGHT*Game.SCALE/1.57));
        wordMenu = new Words("Back to Menu", (int)(Game.WIDTH*Game.SCALE*0.03125), Game.WIDTH*Game.SCALE/2, (int)(Game.HEIGHT*Game.SCALE/1.43));
        wordSaveQuit = new Words("Save & Quit", (int)(Game.WIDTH*Game.SCALE*0.03125), Game.WIDTH*Game.SCALE/2, (int)(Game.HEIGHT*Game.SCALE/1.31));
        words = new Words[5];
        words[0] = wordResume;
        words[1] = wordRetry;
        words[2] = wordSelect;
        words[3] = wordMenu;
        words[4] = wordSaveQuit;
        cursor = new Cursor(0,0,(int)(Game.WIDTH*Game.SCALE*0.025));
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
                        break;
                    case 3:
                        gameStateManager.saveAndWriteRecord(new Record(gameStateManager.getSlotId(), gameStateManager.getTimer().getUsedTimeRecord(), gameStateManager.getTimer().toString(), gameStateManager.getEmeraldCount(), gameStateManager.getCurrentLevel(), gameStateManager.getDeathCount()));
                        gameStateManager.toMenu();
                        locked = true;
                        break;
                    case 4:
                        gameStateManager.saveAndWriteRecord(new Record(gameStateManager.getSlotId(), gameStateManager.getTimer().getUsedTimeRecord(), gameStateManager.getTimer().toString(), gameStateManager.getEmeraldCount(), gameStateManager.getCurrentLevel(), gameStateManager.getDeathCount()));
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
