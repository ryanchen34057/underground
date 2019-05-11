
package gameStates;

import UI.Game;
import UI.Window;
import audio.SoundEffectPlayer;
import selectionObject.Cursor;
import fonts.Words;
import input.Input;

import java.awt.*;

import map.Background;


public class MenuState extends GameState{
    private Words[] words ;
    private Cursor cursor;
    private Words wordTitle;


    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }
    
    public void init() {
        background = new Background("/res/Cave1.png", Window.scaledGameWidth, Window.scaledGameHeight);
        wordTitle = new Words("Underground", (int) (80 * Game.widthRatio), Window.scaledGameWidth / 2, (int) (350 * Game.heightRatio));
        Words wordStart = new Words("Start", (int) (40 * Game.widthRatio), Window.scaledGameWidth / 2, (int) (550 * Game.heightRatio));
        Words wordLeaderboard = new Words("Leaderboard", (int) (40 * Game.widthRatio), Window.scaledGameWidth / 2, (int) (620 * Game.heightRatio));
        Words wordOption = new Words("Option", (int) (40 * Game.widthRatio), Window.scaledGameWidth / 2, (int) (690 * Game.heightRatio));
        Words wordExit = new Words("Exit", (int) (40 * Game.widthRatio), Window.scaledGameWidth / 2, (int) (760 * Game.heightRatio));
        words = new Words[4];
        words[0] = wordStart;
        words[1] = wordLeaderboard;
        words[2] = wordOption;
        words[3] = wordExit;
        cursor = new Cursor((int)(32*Game.widthRatio), 1);
    }

    @Override
    public void handleKeyInput() {
        if(!locked){
            if(Input.keys.get(7).down){//Enter
                SoundEffectPlayer.playSoundEffect("Enter");
                switch(cursor.getPointer()){
                    case 0:
                        gameStateManager.setGameState(new StoryState(gameStateManager));
                        locked = true;
                        break;
                    case 1:
                        gameStateManager.setGameState(new LeaderboardState(gameStateManager));
                        locked = true;
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
                SoundEffectPlayer.playSoundEffect("Cursor");
                cursor.chagePointer(-1, words);
                locked = true;
            }
            if(Input.keys.get(1).down){//下
                SoundEffectPlayer.playSoundEffect("Cursor");
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
        for(Words words: words) {
            words.paint(g);
        }
        cursor.paint(g);
    }

   
}
