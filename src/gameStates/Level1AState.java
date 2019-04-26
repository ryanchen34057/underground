package gameStates;

import record.Timer;
import enums.Id;
import gameObject.character.Entity;
import static gameStates.GameState.locked;
import graphics.SpriteManager;
import input.Input;
import map.Background;

import java.awt.*;

public class Level1AState extends GameState {
    private Timer timer;
    GameStateManager gameStateManager;
    
    public Level1AState(GameStateManager gameStateManager) {
        super();
        this.gameStateManager = gameStateManager;
        init();
    }

    public void init() {
        SpriteManager.level1Init();
        handler.createLevel1(SpriteManager.level1);
        background = new Background("/res/background2.jpg", 1.0f);
        timer = new Timer();
    }


    @Override
    public void handleKeyInput() {
        if(!locked){
            if(Input.keys.get(7).down){//Enter               
                gameStateManager.setGameState(new PuaseState(gameStateManager));
                locked = true;
            }
        }
        if(!Input.keys.get(7).down){//放開
            locked = false;
        }
    }

    @Override
    public void update() {
        System.out.println(cam.getX() + " " + cam.getY());
        System.out.println(handler.getEntities().size());
        background.setPos(cam.getX(), cam.getY());
        handler.update();
        for(int i=0;i<handler.getEntities().size();i++) {
            Entity e = handler.getEntities().get(i);
            if (e.getId() == Id.player) {
                cam.update(e);
            }
        }
        timer.update();
    }

    @Override
    public void paint(Graphics g) {
        background.paint(g);
        g.translate(cam.getX(), cam.getY());
        handler.paint(g);
        g.translate(-cam.getX(), -cam.getY());
        timer.paint(g);        
    }
}
