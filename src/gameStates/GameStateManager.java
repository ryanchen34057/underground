package gameStates;

import java.awt.*;
import java.util.Stack;

public class GameStateManager {
    private Stack<GameState> gameStates;
    private GameState currentGameState;
    private GameState previousGameState;
    public GameState menu = new MenuState(this);



    public GameStateManager() {
        gameStates = new Stack<>();
        gameStates.push(menu);
        currentGameState = gameStates.peek();//創建時指定場景
        
    }

    public void setGameState(GameState gameState) {
        gameStates.push(gameState);
        currentGameState = gameStates.peek();
    }

    public void paint(Graphics g) {
        currentGameState.paint(g);
    }

    public void update() {
        currentGameState.update();
    }
    
    public void handleKeyInput(){
        currentGameState.handleKeyInput();
        
    }
}
