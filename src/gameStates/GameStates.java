package gameStates;

import gameStates.level.Level1State;
import gameStates.level.Level2State;
import gameStates.level.Level3State;
import gameStates.level.Level4State;

import java.util.ArrayList;

public class GameStates {
    private ArrayList<LevelState> levelStates;
    private GameStateManager gameStateManager;

//
//    public GameStates(GameStateManager gameStateManager) {
//        this.gameStateManager = gameStateManager;
//        this.levelStates = new ArrayList<>();
//        levelStates.add(new Level1State(gameStateManager));
//        levelStates.add(new Level2State(gameStateManager));
//        levelStates.add(new Level3State(gameStateManager));
//        levelStates.add(new Level4State(gameStateManager));
//    }
//
//    public ArrayList<LevelState> getLevelStates() { return levelStates;}
}
