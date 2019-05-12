package gameStates.level;

import UI.Game;
import effects.DeathParticle;
import enums.Id;
import fonts.Words;
import gameObject.character.Player;
import gameStates.GameStateManager;
import gameStates.LevelState;
import graphics.Sprite;
import graphics.SpriteManager;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import map.Background;
import states.PlayerState;

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
        player = new Player(Player.WIDTH, Player.HEIGHT, Id.player);
        player.setPosition((int)bluePortalCor.getWidth(), (int)bluePortalCor.getHeight());
    }
    @Override
    public void paint(Graphics g) {
        background.paint(g);
        g.translate(cam.getX(), cam.getY());
        paintAllGameObject(g);      
        g.translate(-cam.getX(), -cam.getY());
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
        cam.update(player, mapWidth, mapHeight);

        // Check if on the ice
        if(player.isOnTheIce() && player.getCurrentState() != PlayerState.standing) {
            player.setCurrentState(PlayerState.iceSkating);
        }

        //Check if on the ground
        if(!player.isInTheAir() && !player.isOnTheGround()) {
            player.setCurrentState(PlayerState.falling);
        }

        if((player.isGoaled())) {
            gameStateManager.setLevelState(new Level2State(gameStateManager));
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
