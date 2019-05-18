package gameStates.level;

import UI.Game;
import enums.Id;
import gameObject.character.Player;
import gameStates.GameStateManager;
import gameStates.LevelState;
import graphics.SpriteManager;
import map.Background;

public class Level1State extends LevelState {
    
    public Level1State(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        SpriteManager.levelInit();
        levelObjectInit();
        createLevel(SpriteManager.level1);
        background = new Background("/res/background2.jpg", 1.0f);
        player = new Player((int)(96*Game.widthRatio), (int)(96*Game.widthRatio), Id.player);
        player.setPosition((int)bluePortalCor.getWidth(), (int)bluePortalCor.getHeight());
    }

    @Override
    public void update() {
        // handle player's keyInput
        handleKeyInput();

        // Set position of the background
        background.setPos(cam.getX(), cam.getY());

        // Update all game object
        updateAllGameObject();

        if((player.isGoaled())) {
            gameStateManager.setLevelState(new Level2State(gameStateManager));
            if(gameStateManager.getLevelReached() == 1) {
                gameStateManager.incrementLevelReached();
            }
        }
    }

    @Override
    public LevelState getInstance() {
        return new Level1State(gameStateManager);
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
