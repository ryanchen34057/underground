package gameStates;

import UI.Game;
import UI.Window;
import audio.SoundEffectPlayer;
import fonts.Words;
import input.Input;
import map.Background;
import java.awt.*;
import java.util.ArrayList;

public class AudioOptionState extends GameState {
    private ArrayList<Words> words;
    private int selectedHor;
    private int selectedVer;
    private selectionObject.Cursor leftCursor;
    private selectionObject.Cursor rightCursor;

    public AudioOptionState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        background = new Background("/res/Cave1.png",1);
        words = new ArrayList<>();
        words.add(new Words("Audio", (int)(60*Game.widthRatio), Window.scaledGameWidth /2, (int)(Window.scaledGameHeight/2.5)));
        words.add(new Words("Volume: ", (int)(30*Game.widthRatio), (int)(500*Game.widthRatio), (int)(500*Game.heightRatio)));
        words.add(new Words("Save Changes", (int)(30*Game.widthRatio), (int)(650*Game.widthRatio), (int)(700*Game.heightRatio)));
        rightCursor = new selectionObject.Cursor((int)(32*Game.widthRatio), 1);
        leftCursor = new selectionObject.Cursor((int)(32*Game.widthRatio), -1);
        selectedHor = 0;
        selectedVer = 0;
    }

    @Override
    public void handleKeyInput() {
        if (!locked) {
            if (!Input.keys.get(7).down && !Input.keys.get(0).down && !Input.keys.get(1).down && !Input.keys.get(2).down && !Input.keys.get(3).down) {//放開
                locked = false;
            }
        }
    }

    @Override
    public void update() {
        handleKeyInput();
        leftCursor.setPos(words.get(1).getWordX() + words.get(1).getWidth()/2, words.get(1).getWordY() - (int)(words.get(1).getHeight()*1.6));
        rightCursor.setPos((int)(leftCursor.getX() * 1.2), leftCursor.getY());
    }

    @Override
    public void paint(Graphics g) {
        background.paint(g);
        for (Words word : words) {
            word.paint(g);
        }
        leftCursor.paint(g);
        rightCursor.paint(g);
    }
}
