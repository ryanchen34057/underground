package gameStates.level1;

import enums.Id;
import fonts.Words;
import gameObject.character.Entity;
import gameStates.GameState;
import graphics.SpriteManager;
import map.Background;

import java.awt.*;

public class Level1BState extends GameState {

    public Level1BState() {
        super();
        init();
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
        g.translate(-cam.getX(), -cam.getY());
    }
}
