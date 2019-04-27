package gameStates;

import record.Timer;
import java.awt.*;
import java.util.Stack;

public class GameStateManager {
    private Stack<GameState> gameStates;
    private GameState currentGameState;
    private GameState menu;
    private Timer timer;


    public GameStateManager() {
        menu = new MenuState(this);
        gameStates = new Stack<>();
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
        }
    }

    public void update() {
        currentGameState = gameStates.peek();
        currentGameState.update();
        if(timer == null & currentGameState instanceof LevelState) { timer = new Timer(); }
        if(timer != null && currentGameState instanceof LevelState) {
            timer.update();
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
}
