
package gameStates.level;

import UI.Game;
import enums.Id;
import gameObject.character.Player;
import gameStates.GameStateManager;
import gameStates.LevelState;
import gameStates.TutorialState;
import graphics.SpriteManager;
import map.Background;

public class Level4State extends LevelState {
    public Level4State(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public LevelState getInstance() {
        return new Level4State(gameStateManager);
    }

    @Override
    public int getLevel() {
        return 4;
    }

    @Override
    public void init() {
        SpriteManager.levelInit();
        levelObjectInit();
        createLevel(SpriteManager.level4);
        background = new Background("/res/background2.jpg", 1.0f);
        player = new Player((int)(96* Game.widthRatio), (int)(96*Game.widthRatio), Id.player);
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
            gameStateManager.setLevelState(new TutorialState(gameStateManager));
            if(gameStateManager.getLevelReached() == 4) {
                gameStateManager.incrementLevelReached();
            }
        }
    }
}
