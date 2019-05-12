
package gameStates;

import UI.Game;
import UI.Window;
import audio.SoundEffectPlayer;
import selectionObject.Cursor;
import fonts.Words;
import input.Input;
import java.awt.Graphics;
import map.Background;


public class OptionState extends GameState{
    private Words[] words ;
    private Words wordTitle;
    private Words wordVideo;
    private Words wordAudio;
    private Words wordBack;
    private Cursor cursor;
    
    public OptionState(GameStateManager gameStateManager) {
       super(gameStateManager);
    }
    
    public void init() {
        background = new Background("/res/Cave1.png", Window.scaledGameWidth, Window.scaledGameHeight);
        wordTitle = new Words("Option", (int)(60*Game.widthRatio), Window.scaledGameWidth/2, (int)(Window.scaledGameHeight/2.5));
        wordVideo = new  Words("Video", (int)(40*Game.widthRatio), Window.scaledGameWidth/2,(int) (550 * Game.heightRatio));
        wordAudio = new Words("Audio", (int)(40*Game.widthRatio), Window.scaledGameWidth/2, (int) (620 * Game.heightRatio));
        wordBack = new Words("Back", (int)(40*Game.widthRatio), Window.scaledGameWidth/2, (int) (690 * Game.heightRatio));
        words = new Words[3];
        words[0] = wordVideo;
        words[1] = wordAudio;
        words[2] = wordBack;
        cursor = new Cursor((int)(32*Game.widthRatio), 1);
    }
    
    @Override
    public void handleKeyInput() {
        if(!locked){
            if(Input.keys.get(7).down){//Enter
                SoundEffectPlayer.playSoundEffect("Enter");
                 switch(cursor.getPointer()){
                    case 0:
                        gameStateManager.setGameState(new VideoOptionState(gameStateManager));
                        break;
                    case 1:
                        gameStateManager.setGameState(new AudioOptionState(gameStateManager));
                        break;
                    case 2:
                        gameStateManager.back();
                        locked = true;
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
        cursor.paint(g);
        wordTitle.paint(g);
        wordVideo.paint(g);
        wordAudio.paint(g);
        wordBack.paint(g);
    }
    
}
