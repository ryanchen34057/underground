package gameStates;

import java.awt.*;
import java.util.ArrayList;

public class GameStateManager {
    private ArrayList<GameState> gameStates;
    private static GameState currentGameState;


    public GameStateManager() {
        gameStates = new ArrayList<>();
        currentGameState = GameStates.level1;
    }

    public static GameState getCurrentGameState() { return currentGameState; }

    public void setGameStates(GameState gameState) {
        currentGameState = gameState;
    }

    public void paint(Graphics g) {
        currentGameState.paint(g);
    }

    public void update() {

        currentGameState.update();
    }
}
