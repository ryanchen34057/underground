package gameStates;

import enums.Id;
import gameObject.character.Entity;
import graphics.SpriteManager;
import map.Background;

import java.awt.*;

public class Level1AState extends GameState {

    public Level1AState() {
        super();
        init();

    }

    public void init() {
        handler.createLevel1(SpriteManager.level1);
        background = new Background("/res/back-walls.png", 1);
    }


    @Override
    public void handleKeyInput() {

    }

    @Override
    public void update() {
        background.setPos(cam.getX(), cam.getY());
        handler.update();
        for(int i=0;i<handler.getEntities().size();i++) {
            Entity e = handler.getEntities().get(i);
            if (e.getId() == Id.player) {
                cam.update(e);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        background.paint(g);
        g.translate(cam.getX(), cam.getY());
        handler.paint(g);
    }
}
