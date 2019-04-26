package gameStates;

import java.awt.*;
import java.util.Stack;

public class GameStateManager {
    private Stack<GameState> gameStates;
    private GameState currentGameState;
//    private GameState previousGameState;
    public GameState menu = new MenuState(this);



    public GameStateManager() {
        gameStates = new Stack<>();
        gameStates.push(menu);
    }

    public void setGameState(GameState gameState) {
        gameStates.push(gameState);
        currentGameState = gameStates.peek();
    }

    public void paint(Graphics g) {
        if(currentGameState != null){
            currentGameState.paint(g);
        }
    }

    public void update() {
        currentGameState = gameStates.peek();
        currentGameState.update();
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
    
}
