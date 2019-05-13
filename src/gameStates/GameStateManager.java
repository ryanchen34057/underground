package gameStates;

import UI.Game;
import audio.MusicPlayer;
import fonts.Words;
import gameObject.tiles.prize.Emerald;
import gameStates.level.*;
import graphics.SpriteManager;
import static graphics.SpriteManager.c;
import record.Timer;
import record.Record;
import java.awt.*;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class GameStateManager {
    private String playerName;
    private Stack<GameState> gameStateStack;
    private GameState currentGameState;
    private GameState menu;
    private Timer timer;
    private static HashMap<Integer, Emerald[]> emeraldMap;
    private Words nameWords;
    private Words emeraldCountWord;
    private Words deathCountWord;
    private Words levelWord;
    private Words infinityModeWord;
    private int emeraldCount;
    private int deathCount;
    private int slotId;
    private int currentLevel;
    public static final int LEVEL_COUNT = 5;
    ArrayList<Record> records;


    public GameStateManager() {
        playerName = null;
        emeraldMap = new HashMap<>();
        for(int i=0;i<LEVEL_COUNT;i++) {
            emeraldMap.put(i+1, new Emerald[5]);
        }
        menu = new MenuState(this);
        gameStateStack = new Stack<>();
        emeraldCount = 0;
        deathCount = 0;
        emeraldCountWord = new Words("X " + emeraldCount, (int) (30 * Game.widthRatio), (int)(1200 * Game.widthRatio), (int)(50 * Game.heightRatio));
        deathCountWord = new Words("X " + deathCount, (int)(30 * Game.widthRatio), (int)(1200 * Game.widthRatio), (int)(100 * Game.heightRatio));
        infinityModeWord = new Words("Infinity Mode", (int)(30 * Game.widthRatio), (int)(140 * Game.widthRatio), (int)(120 * Game.heightRatio));
        setGameState(new MenuState(this));
        //setLevelState(new LastLevelState(this));
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
    public String getPlayerName() {
        return playerName;
    }

    //Setters
    public void setSlotId(int id) {
        slotId = id;
    }
    public void setDeathCount(int deathCount) {
        this.deathCount = deathCount;
    }
    public void setEmeraldCount(int emeraldCount) {
        this.emeraldCount = emeraldCount;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setGameState(GameState gameState) {
        gameStateStack.push(gameState);
        currentGameState = gameStateStack.peek();
    }

    public void setLevelState(LevelState levelState) {
        gameStateStack.push(levelState);
        currentLevel = levelState.getLevel();
        String msg;
        int x;
        if(currentLevel == LEVEL_COUNT) {
            msg = "Level: Lava Hell";
            x = 160;
        }
        else {
            msg = "Level: " + currentLevel;
            x = 85;
        }
        levelWord = new Words(msg, (int)(30 * Game.widthRatio), (int)(x * Game.widthRatio), (int)(40 * Game.heightRatio));
    }

    public void resetEmerald() {
        emeraldMap = new HashMap<>();
        for(int i=0;i<LEVEL_COUNT;i++) {
            emeraldMap.put(i+1, new Emerald[5]);
        }
    }

    public void updateGameState(GameState gameState) {
        gameStateStack.pop();
        gameStateStack.push(gameState);
        currentGameState = gameStateStack.peek();
    }

    public void paint(Graphics g) {
        if(currentGameState != null){
            currentGameState.paint(g);
        }
        if(timer != null && currentGameState instanceof LevelState && !(currentGameState instanceof TutorialState)) {
            timer.paint(g);
            // Display emerald count
            emeraldCountWord.paint(g);
            deathCountWord.paint(g);
            levelWord.paint(g);
            g.drawImage(SpriteManager.emerald.getBufferedImage(), (int)(1100 * Game.widthRatio), (int)(10 * Game.heightRatio), (int)(32 * Game.widthRatio), (int)(32 * Game.widthRatio), null);
            g.drawImage(SpriteManager.skull.getBufferedImage(), (int)(1100 * Game.widthRatio), (int)(60 * Game.heightRatio), (int)(32 * Game.widthRatio), (int)(32 * Game.widthRatio), null);
            if(Game.infinityMode) {
                infinityModeWord.paint(g);
            }
        }
    }

    public void update() {
        currentGameState = gameStateStack.peek();
        currentGameState.update();
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

    public LevelState getNewLevel(int currentLevel) {
        switch (currentLevel) {
            case 1:
                return new Level1State(this);
            case 2:
                return new Level2State(this);
            case 3:
                return new Level3State(this);
            case 4:
                return new Level4State(this);
            case 5:
                return new LastLevelState(this);
        }
        return null;
    }
    
    public void toMenu(){
        emeraldCountWord = new Words("X " + emeraldCount, (int) (30 * Game.widthRatio), (int)(1200 * Game.widthRatio), (int)(50 * Game.heightRatio));
        deathCountWord = new Words("X " + deathCount, (int)(30 * Game.widthRatio), (int)(1200 * Game.widthRatio), (int)(100 * Game.heightRatio));
        infinityModeWord = new Words("Infinity Mode", (int)(30 * Game.widthRatio), (int)(140 * Game.widthRatio), (int)(120 * Game.heightRatio));
        if(gameStateStack.size() > 1){
            gameStateStack = new Stack<>();
            gameStateStack.push(new MenuState(this));
        }
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void saveAndWriteRecord(Record record) {//遊戲紀錄
            try {
                  new File("./record").mkdir();
                  FileOutputStream fileOut
                          = new FileOutputStream("./record/" + "record" + record.getId() + ".ser");
                  ObjectOutputStream out = new ObjectOutputStream(fileOut);
                  out.writeObject(record);
                  out.close();
                  fileOut.close();
            } catch (IOException i) {
                  i.printStackTrace();
            }
      }

      public ArrayList readEndGameRecord(){//破關記錄
            records = new ArrayList<>();
            try {
                  //讀檔
                  FileInputStream fileIn = new FileInputStream("./record/" + "Leaderboard.ser");
                  ObjectInputStream in = new ObjectInputStream(fileIn);
                  records = (ArrayList)in.readObject();
                  fileIn.close();
                  in.close();
                  return records;
            }catch(IOException | ClassNotFoundException e){
                   e.printStackTrace();
            }finally{
                  return records;
            }
      }

      public void saveEndGameRecord(Record record) {//寫入破關記錄
            try {
                  ArrayList<Record> endGame;
                  if(readEndGameRecord() != null){
                        endGame = readEndGameRecord();
                  }else{
                        endGame = new ArrayList<>();
                  }
                  //寫檔
                  FileOutputStream fileOut = new FileOutputStream("./record/" + "Leaderboard.ser");
                  ObjectOutputStream out = new ObjectOutputStream(fileOut); 
                  endGame.add(record);
                  //
                  out.writeObject(endGame);
                  out.close();
                  fileOut.close();          
            } catch (IOException i) {
                  i.printStackTrace();
            }
      }

    public void loadRecord(Record record) {
        slotId = record.getId();
        playerName = record.getName();
        currentLevel = record.getLevel();
        emeraldCount = record.getEmeraldCount();
        deathCount = record.getDeathCount();
        timer = new Timer(record.getTime());
        switch (record.getLevel()) {
            case 0:
                setLevelState(new Level0State(this));
            case 1:
                setLevelState(new Level1State(this));
                break;
            case 2:
                setLevelState(new Level2State(this));
                break;
            case 3:
                setLevelState(new Level3State(this));
                break;
            case 4:
                setLevelState(new Level4State(this));
                break;
            case 5:
                setLevelState(new LastLevelState(this));
                break;
        }
    }

    public void addEmerald(int level, int serialId, Emerald emerald) {
        emeraldMap.get(level)[serialId] = emerald;
    }

    public Emerald getEmerald(int level, int serialId) {
        return emeraldMap.get(level)[serialId];
    }

    public void incrementDeathCount() {
        deathCount++;
    }

    public void incrementEmeraldCount() {
        emeraldCount++;
    }
}
