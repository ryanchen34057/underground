package gameStates.level1;

import gameObject.character.Player;
import gameStates.GameState;
import gameStates.GameStateManager;
import graphics.SpriteManager;
import map.Background;

import java.awt.*;

public class Level1CState extends GameState {
    public Level1CState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    public void init() {
        SpriteManager.level1Init();
        handler.createLevel1(SpriteManager.level1B);
        background = new Background("/res/background2.jpg", 1.0f);

    }
    @Override
    public void handleKeyInput() {

    }

    @Override
    public void update() {
        background.setPos(cam.getX(), cam.getY());
        handler.update();
        Player player = null;
        for(int i=0;i<handler.getEntities().size();i++) {
            if (handler.getEntities().get(i) instanceof Player) {
                player = (Player) handler.getEntities().get(i);
                cam.update(player);
                if((player.isGoaled())) {
//                    gameStateManager.setLevelState(new Level1AState(gameStateManager));
                }
            }
        }
        if(player == null) {
            deathDelay++;
            if(deathDelay >= DEATH_DELAY_TIME) {
                deathDelay = 0;
                gameStateManager.setLevelState(new Level1BState(gameStateManager));
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        background.paint(g);
        g.translate(cam.getX(), cam.getY());
        handler.paint(g);
        g.translate(-cam.getX(), -cam.getY());
    }
}
