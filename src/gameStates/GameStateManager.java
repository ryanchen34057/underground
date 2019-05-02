package gameStates;

import UI.Window;
import audio.MusicPlayer;
import fonts.Words;
import graphics.SpriteManager;
import record.Timer;
import record.Record;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Stack;

public class GameStateManager {
    private GameStates gameStates;
    private Stack<GameState> gameStateStack;
    private GameState currentGameState;
    private GameState menu;
    private Timer timer;
    private Words emeraldCountWord;
    private Words deathCountWord;
    private Words levelWord;
    private int emeraldCount;
    private int deathCount;
    private int slotId;
    private int currentLevel;


    public GameStateManager() {
        gameStates = new GameStates(this);
        menu = new MenuState(this);
        gameStateStack = new Stack<>();
        emeraldCount = 0;
        deathCount = 0;
        emeraldCountWord = new Words("X " + emeraldCount, 30, 1200, 50);
        deathCountWord = new Words("X " + deathCount, 30, 1200, 100);
        setGameState(new MenuState(this));
    }

    //Getters
    public int getEmeraldCount() {
        return emeraldCount;
    }
    public int getDeathCount() {
        return deathCount;
    }
    public int getSlotId() {
        return slotId;
    }
    public int getCurrentLevel() {
        return currentLevel;
    }

    //Setters
    public void setSlotId(int id) {
        slotId = id;
    }

    public void setGameState(GameState gameState) {
        gameStateStack.push(gameState);
        currentGameState = gameStateStack.peek();
    }

    public void setLevelState(LevelState levelState) {
        gameStateStack.push(levelState);
        currentLevel = levelState.getLevel();
        levelWord = new Words("Level: " + currentLevel, 30, 85, 40);
    }

    public void paint(Graphics g) {
        if(currentGameState != null){
            currentGameState.paint(g);
        }
        if(timer != null && currentGameState instanceof LevelState) {
            timer.paint(g);
            // Display emerald count
            emeraldCountWord.paint(g);
            deathCountWord.paint(g);
            levelWord.paint(g);
            g.drawImage(SpriteManager.emerald.getBufferedImage(), 1100, 10, 32, 32, null);
            g.drawImage(SpriteManager.skull.getBufferedImage(), 1100, 61, 32, 32, null);
        }
    }

    public void update() {
        currentGameState = gameStateStack.peek();
        currentGameState.update();
        if(currentGameState instanceof MenuState) {
            if(Window.musicPlayer.size() == 0) {
                Window.musicPlayer.add("BedTime-Story");
            }
        }
        if(timer == null & currentGameState instanceof LevelState) { timer = new Timer(); }

        if(timer != null && currentGameState instanceof LevelState) {
            timer.pauseEnd();
            timer.update();
            emeraldCountWord.update("X " + emeraldCount);
            deathCountWord.update("X " + deathCount);
        }else if(timer != null){
            timer.timerPause();
        }
    }
    
    public void handleKeyInput(){
        if(currentGameState != null) {
          currentGameState.handleKeyInput();   
        }
           
    }
    public void back(){
        gameStateStack.pop();
    }
    
    public void toMenu(){
        if(gameStateStack.size() > 1){
            gameStateStack = new Stack<>();
            gameStateStack.push(new MenuState(this));
        }
    }

    public Timer getTimer() {
        return timer;
    }

    public void saveAndWriteRecord(Record record) {
        try {
            new File("./record").mkdir();
            FileOutputStream fileOut =
                    new FileOutputStream("./record/" + "record" + record.getId() + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(record);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void loadRecord(Record record) {
        slotId = record.getId();
        currentLevel = record.getLevel();
        emeraldCount = record.getEmeraldCount();
        deathCount = record.getDeathCount();
        setLevelState(gameStates.getLevelStates().get(record.getLevel() - 1));
    }

    public void incrementDeathCount() {
        deathCount++;
    }

    public void incrementEmeraldCount() {
        emeraldCount++;
    }
}
