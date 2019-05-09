package gameStates;

import UI.Game;
import UI.Window;
import audio.AudioFile;
import audio.MusicPlayer;
import audio.SoundEffectPlayer;
import fonts.Words;
import input.Input;
import map.Background;
import java.awt.*;
import java.util.ArrayList;

public class AudioOptionState extends GameState {
    private ArrayList<Words> words;
    private int selectedHorBackground;
    private int selectedHorSoundEffect;
    private int selectedVer;
    private selectionObject.Cursor leftCursor;
    private selectionObject.Cursor rightCursor;
    private float[] volumeList;
    private Words[] backgroundSwitch;
    private Words[] soundEffectSwitch;
    private float currentVolume;
    private static final float MAX_VOLUME = 0;
    private static final float MIN_VOLUME = -50;
    private static final float DEFAULT_VOLUME = -10;
    private Words volumeWords;

    public AudioOptionState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        background = new Background("/res/Cave1.png",1);
        words = new ArrayList<>();
        words.add(new Words("Audio", (int)(60*Game.widthRatio), Window.scaledGameWidth /2, (int)(Window.scaledGameHeight/2.5)));
        words.add(new Words("Background Music ", (int)(30*Game.widthRatio), (int)(500*Game.widthRatio), (int)(500*Game.heightRatio)));
        words.add(new Words("Sound Effect ", (int)(30*Game.widthRatio), (int)(500*Game.widthRatio), (int)(600*Game.heightRatio)));
        words.add(new Words("Volume ", (int)(30*Game.widthRatio), (int)(500*Game.widthRatio), (int)(700*Game.heightRatio)));
        words.add(new Words("Save Changes", (int)(30*Game.widthRatio), (int)(650*Game.widthRatio), (int)(800*Game.heightRatio)));
        backgroundSwitch = new Words[2];
        soundEffectSwitch = new Words[2];
        backgroundSwitch[0] = new Words("On", (int)(30*Game.widthRatio), (int)(words.get(1).getWordX()*1.5), words.get(1).getWordY());
        backgroundSwitch[1] = new Words("Off", (int)(30*Game.widthRatio), (int)(words.get(1).getWordX()*1.5), words.get(1).getWordY());
        soundEffectSwitch[0] = new Words("On", (int)(30*Game.widthRatio), (int)(words.get(1).getWordX()*1.5), words.get(2).getWordY());
        soundEffectSwitch[1] = new Words("Off", (int)(30*Game.widthRatio), (int)(words.get(1).getWordX()*1.5), words.get(2).getWordY());
        volumeWords = new Words(Integer.toString((int)((50.0f+currentVolume)/0.5f)), (int)(30*Game.widthRatio), (int)(words.get(1).getWordX()*1.5), words.get(3).getWordY());
        rightCursor = new selectionObject.Cursor((int)(32*Game.widthRatio), 1);
        leftCursor = new selectionObject.Cursor((int)(32*Game.widthRatio), -1);
        words.add(new Words("+", (int)(30*Game.widthRatio), rightCursor.getX(), rightCursor.getY()));
        words.add(new Words("-", (int)(30*Game.widthRatio), leftCursor.getX(), leftCursor.getY()));
        selectedVer = 0;
        selectedHorBackground = 0;
        selectedHorSoundEffect = 0;
        volumeList = new float[101];
        for(int i=0;i<101;i++) {
            volumeList[i] = -50.0f + i * 0.5f;
        }
        currentVolume = DEFAULT_VOLUME;
    }

    @Override
    public void handleKeyInput() {
        if (!locked) {
            if(Input.keys.get(7).down && selectedVer == 1){//Enter
                gameStateManager.back();
                if(selectedHorSoundEffect == 0) {
                    SoundEffectPlayer.isON = true;
                }
                else {
                    SoundEffectPlayer.isON = false;
                }
                SoundEffectPlayer.playSoundEffect("Enter");
                locked = true;
            }
            if(Input.keys.get(0).down){//上
                if(selectedVer > 0) {
                    selectedVer--;
                }
                locked = true;
                SoundEffectPlayer.playSoundEffect("Cursor");
            }
            if(Input.keys.get(1).down){//下
                if(selectedVer < 3) {
                    selectedVer++;
                }
                locked = true;
                SoundEffectPlayer.playSoundEffect("Cursor");
            }
            if(Input.keys.get(2).down){//左
                if(selectedVer == 0) {
                    selectedHorBackground = (Math.abs(selectedHorBackground - 1)) % 2;
                    locked = true;
                }
                else if(selectedVer == 1) {
                    selectedHorSoundEffect = (Math.abs(selectedHorSoundEffect - 1)) % 2;
                    locked = true;
                }
                else if(selectedVer == 2) {
                    if(currentVolume > MIN_VOLUME) {
                        currentVolume--;
                    }
                }
                SoundEffectPlayer.playSoundEffect("Cursor");
            }
            if(Input.keys.get(3).down){//右
                if(selectedVer == 0) {
                    selectedHorBackground = (selectedHorBackground + 1) % 2;
                    locked = true;
                }
                else if(selectedVer == 1) {
                    selectedHorSoundEffect = (selectedHorSoundEffect + 1) % 2;
                    locked = true;
                }
                else if(selectedVer == 2) {
                    if(currentVolume < MAX_VOLUME) {
                        currentVolume++;
                    }
                }
                SoundEffectPlayer.playSoundEffect("Cursor");

            }
        }
        if(!Input.keys.get(7).down && !Input.keys.get(0).down &&!Input.keys.get(1).down && !Input.keys.get(2).down &&!Input.keys.get(3).down){//放開
            locked = false;
        }
    }

    @Override
    public void update() {
        handleKeyInput();
        if(selectedVer == 3) {
            leftCursor.setPos(words.get(4).getWordX() - (int)(words.get(4).getWidth()/1.5), words.get(4).getWordY() - (int)(words.get(4).getHeight()*1.6));
            rightCursor.setPos(words.get(4).getWordX() + words.get(4).getWidth()/2, words.get(4).getWordY() - (int)(words.get(4).getHeight()*1.6));
        }
        else {
            leftCursor.setPos(words.get(1).getWordX() + words.get(1).getWidth()/2, words.get(selectedVer + 1).getWordY() - (int)(words.get(selectedVer + 1).getHeight()*1.6));
            rightCursor.setPos((int)(leftCursor.getX() * 1.2), leftCursor.getY());
        }

        volumeWords.setWord(Integer.toString((int)((50.0f+currentVolume)/0.5f)));

        // Switch music on and off
        MusicPlayer.isOn = selectedHorBackground == 0;
        SoundEffectPlayer.isON = selectedHorSoundEffect == 0;
        if(AudioFile.volume != currentVolume) {
            AudioFile.volume = currentVolume;
        }
    }

    @Override
    public void paint(Graphics g) {
        background.paint(g);
        for (Words word : words) {
            word.paint(g);
        }
        backgroundSwitch[selectedHorBackground].paint(g);
        soundEffectSwitch[selectedHorSoundEffect].paint(g);
        leftCursor.paint(g);
        rightCursor.paint(g);
        volumeWords.paint(g);
    }
}
