package gameStates;

import record.Timer;

import java.awt.*;
import java.util.Stack;

public class GameStateManager {
    private Stack<GameState> gameStates;
    private GameState currentGameState;
    private GameState previousGameState;
    private Timer timer;



    public GameStateManager() {
        gameStates = new Stack<>();
        setGameState(new MenuState(this));
        currentGameState = gameStates.peek();//創建時指定場景
        if(!(currentGameState instanceof MenuState)) {
            timer = new Timer();
        }
    }

    public void setGameState(GameState gameState) {
        gameStates.push(gameState);
        currentGameState = gameStates.peek();
    }

    public void setLevelState(GameState levelState) {
        gameStates.push(levelState);
        currentGameState = gameStates.pop();
    }

    public void paint(Graphics g) {
        currentGameState.paint(g);
        if(timer != null) {
            timer.paint(g);
        }
    }

    public void update() {
        currentGameState.update();
        if(timer != null) {
            timer.update();
        }
    }
    
    public void handleKeyInput(){
        currentGameState.handleKeyInput();
        
    }
}
