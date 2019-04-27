package gameStates;

import record.Timer;
import java.awt.*;
import java.util.Stack;

public class GameStateManager {
    private Stack<GameState> gameStates;
    private GameState currentGameState;
    private Timer timer;
    private GameState menu;


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
        if(timer == null) {
            timer = new Timer();
        }
    }

    public void paint(Graphics g) {
        if(timer != null) {
            timer.paint(g);
        }
        if(currentGameState != null){
            currentGameState.paint(g);
        }
    }

    public void update() {
        currentGameState = gameStates.peek();
        currentGameState.update();
        if(timer != null) {
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
    
}
