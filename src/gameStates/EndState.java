package gameStates;

import UI.Game;
import UI.Window;
import fonts.Words;
import graphics.SpriteManager;
import input.Input;
import map.Background;

import java.awt.*;
import java.util.ArrayList;

public class EndState extends GameState {
    private float alpha;
    private static final float FADEIN_TIME = 2.5f;
    private ArrayList<Words> words;

    public EndState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        words = new ArrayList<>();
        background = new Background("/res/out.png", UI.Window.scaledGameWidth, Window.scaledGameHeight);
        words.add(new Words("Back To Menu", (int) (Window.scaledGameWidth* 0.02), (int)(Window.scaledGameWidth/1.1),(int)(Window.scaledGameHeight/1.04)));
        words.add(new Words("Time to go home....",  (int) (60*Game.widthRatio), (int)(500*Game.widthRatio), (int)(100*Game.heightRatio)));
        alpha = 0.0f;
    }

    private void fadeIn(Graphics2D g2) {
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
        g2.setComposite(ac);
        background.paint(g2);
        for(Words words: words) {
            words.paint(g2);
        }
        // Enter Key
        g2.drawImage(SpriteManager.enterKey.getBufferedImage(), (int)(Window.scaledGameWidth/1.3), (int)(Window.scaledGameHeight/1.12), (int)(Window.scaledGameWidth*0.05), (int)(Window.scaledGameWidth*0.05), null);
        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1);
        g2.setComposite(ac);
    }

    @Override
    public void handleKeyInput() {
        if(!locked) {
            if(Input.keys.get(7).down) {
                locked = true;
                gameStateManager.toMenu();
            }
        }
        if(!(Input.keys.get(7).down)) {
            locked = false;
        }
    }

    @Override
    public void update() {
        handleKeyInput();
        if(alpha <= 0.97f) alpha += 0.99 / (FADEIN_TIME* Game.UPDATES);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        fadeIn(g2);
    }
}
