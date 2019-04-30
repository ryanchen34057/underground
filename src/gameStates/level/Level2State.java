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


        // Paint effect
        if(player.getCurrentEffect() != null && effects.size() == 0) {
            effects.add(player.getCurrentEffect());
            player.setCurrentEffect(null);
        }

        // Update all game object
        updateAllGameObject();
        cam.update(player);


        // Check if on the ice
        if(player.isOnTheIce() && player.getCurrentState() != PlayerState.standing) {
            player.setCurrentState(PlayerState.iceSkating);
        }

        //Check if on the ground
        if(!player.isInTheAir() && !player.isOnTheGround()) {
            player.setCurrentState(PlayerState.falling);
        }

        if((player.isGoaled())) {
            gameStateManager.setLevelState(new Level3State(gameStateManager));
        }

        // ********* Player death ************
        if(player.isDead()) {
            for(int j=0;j<8;j++) {
                particles.add(DeathParticle.getInstance(player,j));
            }
            deathDelay++;
            if(deathDelay >= DEATH_DELAY_TIME) {
                deathDelay = 0;
                gameStateManager.incrementDeathCount();
                gameStateManager.setLevelState(getInstance());
            }
        }
        // ************************************
    }
}
