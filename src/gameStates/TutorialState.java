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

public class TutorialState extends LevelState {
    private float alpha;
    private ArrayList<Words> words;
    private Player player;

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
        //Handle player's keyInput
        player.handleKeyInput();

        //Update player
        player.update();

        // Paint effect
        if(player.getCurrentEffect() != null && effects.size() == 0) {
            effects.add(player.getCurrentEffect());
            player.setCurrentEffect(null);
        }

        Tile t;
        player.setOnTheGround(false);
        for(int i=0;i<tiles.size();i++) {
            t = tiles.get(i);
            t.update();
            // ********* Player collision detection **********
            if (t.getBounds() != null && player.inTheScreen(t)) {
                Direction direction = player.checkCollisionVertical(t, Tile::getBounds);
                player.handleCollision(t, direction);
            }
            if(t.isDead()) {
                tiles.remove(t);
            }
                // ***********************************************
        }

        //Check if on the ground
        if(!player.isInTheAir() && !player.isOnTheGround()) {
            player.setCurrentState(PlayerState.falling);
        }

        Effect e;
        for(int i=0;i<effects.size();i++) {
            e = effects.get(i);
            e.update();
            if(e.isDead()) {
                effects.remove(e);
            }
        }

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

        for(int i=0;i<tiles.size();i++) {
            if(tiles.get(i) instanceof Diamond || tiles.get(i) instanceof Portal) {
                tiles.get(i).paint(g);
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
