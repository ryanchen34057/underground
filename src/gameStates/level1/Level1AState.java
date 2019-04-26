package gameStates.level1;

import gameObject.tiles.Tile;
import enums.Id;
import gameObject.character.Player;
import gameStates.GameState;
import gameStates.GameStateManager;
import graphics.SpriteManager;
import map.Background;
import states.PlayerState;
import util.LevelHandler;

import java.awt.*;

public class Level1AState extends GameState {
    private LevelHandler levelHandler;
    private Player player;
    public Level1AState(GameStateManager gameStateManager, LevelHandler levelHandler) {
        super(gameStateManager);
        this.levelHandler = levelHandler;
        init();
    }

    public void init() {
        SpriteManager.level1Init();
        levelHandler.createLevel(SpriteManager.level1A);
        player = new Player(levelHandler.getBluePortalCor().width, levelHandler.getBluePortalCor().height, Player.WIDTH, Player.HEIGHT, Id.player);
        background = new Background("/res/background2.jpg", 1.0f);
    }


    @Override
    public void handleKeyInput() {
        player.handleKeyInput();
    }

    @Override
    public void update() {
        // Set position of the background
        background.setPos(cam.getX(), cam.getY());

        // Update all game object
        player.update();
        cam.update(player);
        levelHandler.update();

        player.setOnTheGround(false);
        // Collision detection
        Tile t;
        for(int i=0;i<levelHandler.getTiles().size();i++) {
            t = levelHandler.getTiles().get(i);
            if(!player.inTheScreen(t)) { continue; }
            if(t.getBounds() != null) {
                player.handleCollision(t, player.checkCollisionBounds(t, Tile::getBounds));
            }
        }
        // Check if on the ice
        if(player.isOnTheIce() && player.getCurrentState() != PlayerState.standing) {
            player.setCurrentState(PlayerState.iceSkating);
        }

        //Check if on the ground
        if(!player.isInTheAir() && !player.isOnTheGround()) {
            player.setCurrentState(PlayerState.falling);
        }

        if((player.isGoaled())) {
//            gameStateManager.setLevelState(new Level1BState(gameStateManager));
        }

        if(player == null) {
            deathDelay++;
            if(deathDelay >= DEATH_DELAY_TIME) {
                deathDelay = 0;
                gameStateManager.setLevelState(new Level1AState(gameStateManager, new LevelHandler(cam)));
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        background.paint(g);
        player.paint(g);
        g.translate(cam.getX(), cam.getY());
        levelHandler.paint(g);
        g.translate(-cam.getX(), -cam.getY());
    }

    @Override
    public String toString() {
        return "Level1AState";
    }
}
