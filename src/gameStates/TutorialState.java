package gameStates;

import UI.Game;
import UI.Window;
import audio.MusicPlayer;
import effects.Effect;
import enums.Direction;
import enums.Id;
import fonts.Words;
import gameObject.character.Player;
import gameObject.tiles.Tile;
import gameObject.tiles.portal.Portal;
import gameObject.tiles.prize.Diamond;
import gameStates.level.LastLevelState;
import graphics.SpriteManager;
import map.Background;
import states.PlayerState;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class TutorialState extends LevelState {
    private float alpha;
    private ArrayList<Words> words;

    public TutorialState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public LevelState getInstance() {
        return new TutorialState(gameStateManager);
    }

    @Override
    public void init() {
        SpriteManager.levelInit();
        levelObjectInit();
        createLevel(SpriteManager.tutorial);
        alpha = 0.3f;
        background = new Background("/res/lavaBackground2.png", 1.0f);
        words = new ArrayList<>();
        words.add(new Words("Portal to last level", (int)(30* Game.widthRatio), (int)(background.getWidth()*0.85), (int)(100*Game.heightRatio)));
        words.add(new Words("Eat the diamond to reset your dash!", (int)(25*Game.widthRatio), (int)(640*Game.widthRatio), (int)(350*Game.heightRatio)));
        player = new Player(Player.WIDTH, Player.HEIGHT, Id.player);
        player.setPosition((int)bluePortalCor.getWidth(), (int)bluePortalCor.getHeight());
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
            MusicPlayer.isOn = false;
            MusicPlayer.changeSong(1);
            MusicPlayer.isOn = true;
            gameStateManager.setLevelState(new LastLevelState(gameStateManager));
        }
    }

    @Override
    public void paint(Graphics g) {
        background.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
        g2.setComposite(ac);
        g.setColor(Color.BLACK);
        g.fillRect((int)background.getX(), (int)background.getY(), UI.Window.scaledGameWidth, Window.scaledGameHeight);
        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1);
        g2.setComposite(ac);
        for(Words word: words) {
            word.paint(g);
        }
        // Paint arrow
        g2.drawImage(SpriteManager.arrow.getBufferedImage(), words.get(1).getWordX(), words.get(1).getWordY()+(int)(20*Game.heightRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), null);
        // Paint the player
        player.paint(g);

        Iterator<Tile> tileIterator = tiles.iterator();
        Tile t;
        while(tileIterator.hasNext()) {
            t = tileIterator.next();
            if(t instanceof Diamond || t instanceof Portal) {
                t.paint(g);
            }
        }

        for(int i=0;i<effects.size();i++) {
            effects.get(i).paint(g);
        }


    }

    @Override
    public int getLevel() {
        return -1;
    }
}
