
package gameStates;

import UI.Game;
import UI.Window;
import audio.MusicPlayer;
import selectionObject.Cursor;
import fonts.Words;
import input.Input;
import java.awt.Graphics;

import map.Background;
import record.Record;


public class PauseState extends GameState{
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
    }
    
    public void init() {
        background = new Background("/res/Cave1.png",1);
        wordTitle = new Words("Pause", (int)(60*Game.widthRatio), Window.scaledGameWidth /2, (int)(Window.scaledGameHeight/3));
        wordResume = new Words("Resume", (int)(30*Game.widthRatio), Window.scaledGameWidth/2, (int)(Window.scaledGameHeight/2));
        wordRetry = new Words("Retry", (int)(30*Game.widthRatio), Window.scaledGameWidth/2, (int)(Window.scaledGameHeight/1.75));
        wordSelect = new Words("Select Level", (int)(30*Game.widthRatio), Window.scaledGameWidth/2, (int)(Window.scaledGameHeight/1.57));
        wordMenu = new Words("Back to Menu", (int)(30*Game.widthRatio), Window.scaledGameWidth/2, (int)(Window.scaledGameHeight/1.43));
        wordSaveQuit = new Words("Save & Quit", (int)(30*Game.widthRatio), Window.scaledGameWidth/2, (int)(Window.scaledGameHeight/1.31));
        words = new Words[5];
        words[0] = wordResume;
        words[1] = wordRetry;
        words[2] = wordSelect;
        words[3] = wordMenu;
        words[4] = wordSaveQuit;
        cursor = new Cursor((int)(32 * Game.widthRatio), 1);
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
                        // Return from PauseState
                        gameStateManager.back();
                        // Return from level
                        gameStateManager.back();
                        // Set a new evel
                        gameStateManager.setLevelState(gameStateManager.getNewLevel(gameStateManager.getCurrentLevel()));
                        locked = true;
                        break;
                    case 2:
                        if(gameStateManager.getCurrentLevel() > 0) {
                            gameStateManager.setGameState(new LevelSelectionState(gameStateManager));
                        }
                        locked = true;
                        break;
                    case 3:
                        gameStateManager.saveAndWriteRecord(new Record(gameStateManager.getSlotId(), gameStateManager.getPlayerName(), gameStateManager.getTimer().getUsedTimeRecord(), gameStateManager.getTimer().toString(), gameStateManager.getEmeraldCount(), gameStateManager.getCurrentLevel(), gameStateManager.getLevelReached(), gameStateManager.getDeathCount()));
                        gameStateManager.toMenu();
                        MusicPlayer.isOn = false;
                        MusicPlayer.changeSong(0);
                        MusicPlayer.isOn = true;
                        locked = true;
                        break;
                    case 4:
                        gameStateManager.saveAndWriteRecord(new Record(gameStateManager.getSlotId(), gameStateManager.getPlayerName(), gameStateManager.getTimer().getUsedTimeRecord(), gameStateManager.getTimer().toString(), gameStateManager.getEmeraldCount(), gameStateManager.getCurrentLevel(), gameStateManager.getLevelReached(), gameStateManager.getDeathCount()));
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
        if (Input.isAllReleased()){//放開
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
