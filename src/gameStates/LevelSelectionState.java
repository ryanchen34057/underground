package gameStates;

import UI.Game;
import UI.Window;
import audio.MusicPlayer;
import audio.SoundEffectPlayer;
import fonts.Words;
import gameObject.character.Player;
import gameStates.level.LastLevelState;
import graphics.FrameManager;
import graphics.SpriteManager;
import input.Input;
import map.Background;
import states.PlayerState;

import java.awt.*;
import java.util.ArrayList;

public class LevelSelectionState extends GameState {
    private ArrayList<Words> words;
    private ArrayList<Words> levelWords;
    private int selected;
    private int frame;
    private int frameDelay;
    private int playerX;
    private int lockX;
    private static final int MOVE_INCREMENT = 202;

    public LevelSelectionState(GameStateManager gameStateManager) {
        super(gameStateManager);
        selected = gameStateManager.getCurrentLevel() - 1;
    }

    @Override
    public void init() {
        background = new Background("/res/levelSelection.png", UI.Window.scaledGameWidth, UI.Window.scaledGameHeight);
        words = new ArrayList<>();
        levelWords = new ArrayList<>();
        words.add(new Words("Level Selection", (int) (50 * Game.widthRatio), (int) (UI.Window.scaledGameWidth * 0.25), (int) (UI.Window.scaledGameHeight * 0.14)));
        words.add(new Words("Load", (int) (Window.scaledGameWidth* 0.02), (int)(Window.scaledGameWidth/1.12),(int)(Window.scaledGameHeight/1.04)));
        levelWords.add(new Words("Level 1", (int) (40 * Game.widthRatio), (int)(Window.scaledGameWidth/1.98),(int)(Window.scaledGameHeight*0.77)));
        levelWords.add(new Words("Level 2", (int) (40 * Game.widthRatio), (int)(Window.scaledGameWidth/1.98),(int)(Window.scaledGameHeight*0.77)));
        levelWords.add(new Words("Level 3", (int) (40 * Game.widthRatio), (int)(Window.scaledGameWidth/1.98),(int)(Window.scaledGameHeight*0.77)));
        levelWords.add(new Words("Level 4", (int) (40 * Game.widthRatio), (int)(Window.scaledGameWidth/1.98),(int)(Window.scaledGameHeight*0.77)));
        levelWords.add(new Words("Lava Hell", (int) (40 * Game.widthRatio), (int)(Window.scaledGameWidth/1.98),(int)(Window.scaledGameHeight*0.77)));
        frame = 0;
        frameDelay = 0;
        playerX = (int) (220 * Game.widthRatio);
        lockX = (int) (295 * Game.widthRatio);
    }

    @Override
    public void handleKeyInput() {
        if (!locked) {
            if (Input.keys.get(7).down) {//Enter
                //Stop current song if it's last level
                if(gameStateManager.getNewLevel(selected + 1) instanceof LastLevelState) {
                    MusicPlayer.isOn = false;
                    MusicPlayer.changeSong(1);
                    MusicPlayer.isOn = true;
                }
                gameStateManager.setLevelState(gameStateManager.getNewLevel(selected + 1));
                SoundEffectPlayer.playSoundEffect("Enter");
                locked = true;
            }
            if (Input.keys.get(2).down) {//左
                if (selected > 0) {
                    selected--;
                }
                SoundEffectPlayer.playSoundEffect("Cursor");
                locked = true;
            }
            if (Input.keys.get(3).down) {//右
                if (selected < 4 && (selected + 1) < gameStateManager.getCurrentLevel()) {
                    selected++;
                }
                SoundEffectPlayer.playSoundEffect("Cursor");
                locked = true;
            }

        }
        if (!Input.keys.get(0).down && !Input.keys.get(1).down && !Input.keys.get(2).down && !Input.keys.get(3).down && !Input.keys.get(7).down) {//放開
            locked = false;
        }
    }

    @Override
    public void update() {
        handleKeyInput();
        frameDelay++;
        if (frameDelay >= 10 / Game.UpdatesRatio) {
            frame++;
            if (frame >= FrameManager.playerMoveFrame.length / 2) {
                frame = 0;
            }
            frameDelay = 0;
        }
    }

    @Override
    public void paint(Graphics g) {
        background.paint(g);
        // Paint Load game word
        for (Words word : words) {
            word.paint(g);
        }

        //Paint levelWords
        levelWords.get(selected).paint(g);

        //Paint player moving
        g.drawImage(FrameManager.getPlayerMoveFrame(PlayerState.running)[frame].getBufferedImage(), playerX + MOVE_INCREMENT * selected, (int) (320 * Game.heightRatio), Player.WIDTH, Player.HEIGHT, null);
        //Paint enter Key
        g.drawImage(SpriteManager.enterKey.getBufferedImage(), (int) (Window.scaledGameWidth / 1.25), (int) (Window.scaledGameHeight / 1.12), (int) (Window.scaledGameWidth * 0.05), (int) (Window.scaledGameWidth * 0.05), null);
        //Paint lock
        for(int i=gameStateManager.getCurrentLevel();i<GameStateManager.LEVEL_COUNT;i++) {
            g.drawImage(SpriteManager.lock.getBufferedImage(), lockX + MOVE_INCREMENT * i, (int) (340 * Game.heightRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), null);
        }

    }
}
