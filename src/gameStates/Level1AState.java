package gameStates;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import enums.Id;
import fonts.Words;
import gameObject.character.Entity;
import graphics.SpriteManager;
import map.Background;

import java.awt.*;

public class Level1AState extends GameState {
    private Words timer;

    public Level1AState() {
        super();
        init();
    }

    public void init() {
        SpriteManager.level1Init();
        handler.createLevel1(SpriteManager.level1);
        background = new Background("/res/background2.jpg", 1.0f);
        timer = new Words("00:00:00", 30, 100, 50);
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
        timer.paint(g);
    }
}
