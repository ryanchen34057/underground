package gameStates;

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
    private Stack<GameState> gameStates;
    private GameState currentGameState;
    private GameState menu;
    private Timer timer;
    private Words emeraldCountWord;
    private Words deathCountWord;
    private int emeraldCount;
    private int deathCount;

    public GameStateManager() {
        menu = new MenuState(this);
        gameStates = new Stack<>();
        emeraldCount = 0;
        deathCount = 0;
        emeraldCountWord = new Words("X " + emeraldCount, 30, 1200, 50);
        deathCountWord = new Words("X " + deathCount, 30, 1200, 100);
        setGameState(menu);
    }

    public void setGameState(GameState gameState) {
        gameStates.push(gameState);
        currentGameState = gameStates.peek();
    }

    public void setLevelState(GameState levelState) {
        gameStates.push(levelState);

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
            g.drawImage(SpriteManager.emerald.getBufferedImage(), 1100, 10, 32, 32, null);
            g.drawImage(SpriteManager.skull.getBufferedImage(), 1100, 61, 32, 32, null);
        }
    }

    public void update() {
        currentGameState = gameStates.peek();
        currentGameState.update();
        if(timer == null & currentGameState instanceof LevelState) { timer = new Timer(); }
        if(timer != null && currentGameState instanceof LevelState) {
            timer.update();
            emeraldCountWord.update("X " + emeraldCount);
            deathCountWord.update("X " + deathCount);
        }
    }
    
    public void handleKeyInput(){
        if(currentGameState != null) {
          currentGameState.handleKeyInput();   
        }
           
    }
    public void back(){
        gameStates.pop();
    }
    
    public void toMenu(){
        if(gameStates.size() > 1){
            gameStates.pop(); 
        }
    }

    public Timer getTimer() {
        return timer;
    }

    public void saveAndWriteRecord(Record record) {
        try {
            new File("./record").mkdir();
            FileOutputStream fileOut =
                    new FileOutputStream("./record/" + record.getId() + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(record);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void incrementDeathCount() {
        deathCount++;
    }

    public void incrementEmeraldCount() {
        emeraldCount++;
    }
}
