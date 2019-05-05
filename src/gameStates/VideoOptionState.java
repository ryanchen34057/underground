package gameStates;

import UI.Game;
import UI.Window;
import audio.SoundEffectPlayer;
import fonts.Words;
import input.Input;
import map.Background;
import java.awt.*;
import java.util.ArrayList;

public class VideoOptionState extends GameState {
    private static final String[] WINDOW_TYPE_LIST = {"Windowed", "Full Screen"};
    private static final String[] SIZE_LIST = Window.getSizeList();
    private Words windowType;
    private Words sizeWords;
    private Words[] selection;
    private ArrayList<Dimension> sizes;
    private ArrayList<Words> words;
    private int selectedHorType;
    private int selectedHorSize;
    private int selectedVer;
    private selectionObject.Cursor leftCursor;
    private selectionObject.Cursor rightCursor;
    private boolean isFullScreen;

    public VideoOptionState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        background = new Background("/res/Cave1.png",1);
        sizes = new ArrayList<>();
        words = new ArrayList<>();
        int i = Window.screenWidth / Window.gameWidth;
        for(int j=1;j<=i;j++) {
            sizes.add(new Dimension(Window.gameWidth * j, Window.gameHeight * j));
        }
        words.add(new Words("Video", (int)(60*Game.widthRatio), Window.scaledGameWidth /2, (int)(Window.scaledGameHeight/2.5)));
        words.add(new Words("Window Type: ", (int)(30*Game.widthRatio), (int)(500*Game.widthRatio), (int)(500*Game.heightRatio)));
        words.add(new Words("Window Size: ", (int)(30*Game.widthRatio), (int)(500*Game.widthRatio), (int)(600*Game.heightRatio)));
        words.add(new Words("Save Changes", (int)(30*Game.widthRatio), (int)(650*Game.widthRatio), (int)(700*Game.heightRatio)));
        windowType = new Words("Windowed", (int)(30*Game.widthRatio), words.get(1).getWordX() + (int)(280*Game.widthRatio), words.get(1).getWordY());
        sizeWords = new Words(SIZE_LIST[0], (int)(30*Game.widthRatio), windowType.getWordX(), words.get(2).getWordY());
        rightCursor = new selectionObject.Cursor((int)(32*Game.widthRatio), 1);
        leftCursor = new selectionObject.Cursor((int)(32*Game.widthRatio), -1);
        selection = new Words[3];
        selection[0] = windowType;
        selection[1] = sizeWords;
        selection[2] = words.get(3);
        selectedHorType = 0;
        selectedHorSize = 0;
        selectedVer = 0;
    }

    @Override
    public void handleKeyInput() {
        if(!locked){
            if(Input.keys.get(7).down && selectedVer == 2){//Enter
                SoundEffectPlayer.playSoundEffect("Enter");
                if(isFullScreen) {
                    Window.makeFullScreen();
                    gameStateManager.toMenu();
                }
                locked = true;
            }
            if(Input.keys.get(0).down){//上
                if(isFullScreen) {
                    selectedVer = (selectedVer == 0) ? selection.length-1:0;
                }
                else {
                    selectedVer = (selectedVer + selection.length - 1) % selection.length;
                }
                locked = true;
                SoundEffectPlayer.playSoundEffect("Cursor");
            }
            else if(Input.keys.get(1).down){//下
                if(isFullScreen) {
                    selectedVer = (selectedVer == 0) ? selection.length-1:0;
                }
                else {
                    selectedVer = (selectedVer + 1) % selection.length;
                }
                locked = true;
                SoundEffectPlayer.playSoundEffect("Cursor");
            }
            else if(Input.keys.get(2).down){//左
                int length = (selectedVer == 0) ? WINDOW_TYPE_LIST.length:SIZE_LIST.length;
                if(selectedVer == 0) {
                    selectedHorType = (selectedHorType + length - 1) % length;
                }
                else {
                    selectedHorSize = (selectedHorSize + length - 1) % length;
                }
                locked = true;
                SoundEffectPlayer.playSoundEffect("Cursor");
            }
            else if(Input.keys.get(3).down){//右
                int length = (selectedVer == 0) ? WINDOW_TYPE_LIST.length:SIZE_LIST.length;
                if(selectedVer == 0) {
                    selectedHorType = (selectedHorType + 1) % length;
                }
                else {
                    selectedHorSize = (selectedHorSize + 1) % length;
                }
                locked = true;
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
        rightCursor.setPos(selection[selectedVer].getWordX() + (int)(140*Game.widthRatio), selection[selectedVer].getWordY() - (int)(40*Game.heightRatio));
        leftCursor.setPos(rightCursor.getX() - (int)(320*Game.widthRatio), rightCursor.getY());
        if(selectedVer == 0) {
            windowType.setWord(WINDOW_TYPE_LIST[selectedHorType]);
            if(selectedHorType == 1) {
                isFullScreen = true;
            }
            else {
                isFullScreen = false;
            }
        }
        else {
            sizeWords.setWord(SIZE_LIST[selectedHorSize]);
        }

    }

    @Override
    public void paint(Graphics g) {
        background.paint(g);
        for(int i=0;i<words.size();i++) {
            if(i == 2) {
                if(!isFullScreen) {
                    words.get(i).paint(g);
                }
            }
            else {
                words.get(i).paint(g);
            }

        }
        windowType.paint(g);
        if(!isFullScreen) {
            sizeWords.paint(g);
        }
        leftCursor.paint(g);
        rightCursor.paint(g);
    }
}
