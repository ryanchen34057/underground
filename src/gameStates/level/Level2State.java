package gameStates.level;


import effects.DeathParticle;
import enums.Id;
import gameObject.character.Player;
import gameStates.GameStateManager;
import gameStates.LevelState;
import graphics.SpriteManager;
import map.Background;
import states.PlayerState;

public class Level2State extends LevelState {

    public Level2State(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        SpriteManager.levelInit();
        levelObjectInit();
        createLevel(SpriteManager.level2);
        background = new Background("/res/background2.jpg", 1.0f);
        player = new Player(Player.WIDTH, Player.HEIGHT, Id.player);
        player.setPosition((int)bluePortalCor.getWidth(), (int)bluePortalCor.getHeight());
    }

    @Override
    public LevelState getInstance() {
        return new Level2State(gameStateManager);
    }

    @Override
    public int getLevel() {
        return 2;
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
            gameStateManager.setLevelState(new Level3State(gameStateManager));
            if(gameStateManager.getLevelreached() == 2) {
                gameStateManager.incrementLevelReached();
            }
        }
    }
}
