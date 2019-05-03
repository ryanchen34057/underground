package gameStates.level;

import gameStates.GameStateManager;
import gameStates.LevelState;

public class BossLevelState extends LevelState {
    public BossLevelState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public LevelState getInstance() {
        return null;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }
}
