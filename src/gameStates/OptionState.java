
package gameStates;

import UI.Game;
import audio.SoundEffectPlayer;
import selectionObject.Cursor;
import fonts.Words;
import input.Input;
import java.awt.Graphics;
import map.Background;


public class OptionState extends GameState{
    private GameStateManager gameStateManager;
    private Words[] words ;
    private Words wordTitle;
    private Words wordVideo;
    private Words wordAudio;
    private Words wordBack;
    private Cursor cursor;
    
    public OptionState(GameStateManager gameStateManager) {
       super(gameStateManager);
       this.gameStateManager = gameStateManager;
       init(); 
    }
    
    public void init() {
        background = new Background("/res/Cave1.png", Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
        wordTitle = new Words("Option", (int)(Game.WIDTH*Game.SCALE*0.0469), Game.WIDTH*Game.SCALE/2, (int)(Game.HEIGHT*Game.SCALE/2.5));
        wordVideo = new  Words("Video", (int)(Game.WIDTH*Game.SCALE*0.03125), Game.WIDTH*Game.SCALE/2,(int)(Game.HEIGHT*Game.SCALE/1.7));
        wordAudio = new Words("Audio", (int)(Game.WIDTH*Game.SCALE*0.03125), Game.WIDTH*Game.SCALE/2, (int)(Game.HEIGHT*Game.SCALE/1.51));
        wordBack = new Words("Back", (int)(Game.WIDTH*Game.SCALE*0.03125), Game.WIDTH*Game.SCALE/2, (int)(Game.HEIGHT*Game.SCALE/1.37));
        words = new Words[3];
        words[0] = wordVideo;
        words[1] = wordAudio;
        words[2] = wordBack;
        cursor = new Cursor(0,0,(int)(Game.WIDTH*Game.SCALE*0.025));
    }
    
    @Override
    public void handleKeyInput() {
        if(!locked){
            if(Input.keys.get(7).down){//Enter
                SoundEffectPlayer.playSoundEffect("Enter");
                 switch(cursor.getPointer()){
                    case 0:
                        break;
                    case 1:
                        
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
