
package gameStates.level;

import UI.Game;
import enums.Id;
import fonts.Words;
import gameObject.character.Player;
import gameStates.GameStateManager;
import gameStates.LevelState;
import graphics.SpriteManager;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import map.Background;
import states.PlayerState;

public class Level0State extends LevelState {
    //Signboard
    private BufferedImage signImage;
    private ArrayList<Words> words;
 
    
    
    public Level0State(GameStateManager gameStateManager) {
        super(gameStateManager);
        words = new ArrayList<>();
        words.add(new Words("    = Move", 20, (int)(Game.WIDTH*Game.SCALE*0.6), (int)(Game.HEIGHT*Game.SCALE*0.70)));
        words.add(new Words("   = Pause", 20, (int)(Game.WIDTH*Game.SCALE*0.6), (int)(Game.HEIGHT*Game.SCALE*0.74)));
        words.add(new Words("  = Jump", 20, (int)(Game.WIDTH*Game.SCALE*1.80), (int)(Game.HEIGHT*Game.SCALE*0.70)));
        words.add(new Words("   +   = Climb", 20, (int)(Game.WIDTH*Game.SCALE*1.80), (int)(Game.HEIGHT*Game.SCALE*0.74)));
        words.add(new Words("  = Dash", 20, (int)(Game.WIDTH*Game.SCALE*3.2), (int)(Game.HEIGHT*Game.SCALE*0.70)));
        words.add(new Words(" +  = AirDash", 20, (int)(Game.WIDTH*Game.SCALE*3.2), (int)(Game.HEIGHT*Game.SCALE*0.74)));
    }

    @Override
    public void init() {
        SpriteManager.levelInit();
        levelObjectInit();
        createLevel(SpriteManager.level0);
        background = new Background("/res/background2.jpg", 1.0f);
        player = new Player(Player.WIDTH, Player.HEIGHT, Id.player);
        player.setPosition((int)bluePortalCor.getWidth(), (int)bluePortalCor.getHeight());
      //Signboard 
        signImage = SpriteManager.signboard.getBufferedImage();    
    }
    @Override
    public void paint(Graphics g) {
        background.paint(g);
        g.translate(cam.getX(), cam.getY());
        //Signboard
        g.drawImage(SpriteManager.signboard.getBufferedImage(), words.get(0).getWordX()-(int)(Game.WIDTH*Game.SCALE*0.225/2), words.get(0).getWordY()-(int)(Game.HEIGHT*Game.SCALE*0.218/2.5), (int)(Game.WIDTH*Game.SCALE*0.225), (int)(Game.HEIGHT*Game.SCALE*0.218), null);
        g.drawImage(SpriteManager.signboard.getBufferedImage(), words.get(2).getWordX()-(int)(Game.WIDTH*Game.SCALE*0.225/2), words.get(0).getWordY()-(int)(Game.HEIGHT*Game.SCALE*0.218/2.5), (int)(Game.WIDTH*Game.SCALE*0.225), (int)(Game.HEIGHT*Game.SCALE*0.218), null);
        g.drawImage(SpriteManager.signboard.getBufferedImage(), words.get(4).getWordX()-(int)(Game.WIDTH*Game.SCALE*0.225/2), words.get(0).getWordY()-(int)(Game.HEIGHT*Game.SCALE*0.218/2.5), (int)(Game.WIDTH*Game.SCALE*0.225), (int)(Game.HEIGHT*Game.SCALE*0.218), null);
        //Words
        for(Words n: words){
            n.paint(g);
        }
        //keys
        g.drawImage(SpriteManager.left.getBufferedImage(), (int)(words.get(0).getWordX()-32*2.5), words.get(0).getWordY()-32, 32, 32, null);
        g.drawImage(SpriteManager.right.getBufferedImage(), (int)(words.get(0).getWordX()-32*1.5), words.get(0).getWordY()-32, 32, 32, null);
        g.drawImage(SpriteManager.esc.getBufferedImage(), (int)(words.get(1).getWordX()-32*2), words.get(1).getWordY()-32, 32, 32, null);
        
        g.drawImage(SpriteManager.c.getBufferedImage(), (int)(words.get(2).getWordX()-45*2), words.get(2).getWordY()-32-5, 45, 45, null);
        g.drawImage(SpriteManager.right.getBufferedImage(), (int)(words.get(3).getWordX()-32*3), words.get(3).getWordY()-32, 32, 32, null);
        g.drawImage(SpriteManager.c.getBufferedImage(), (int)(words.get(3).getWordX()-45*1), words.get(3).getWordY()-32-5, 45, 45, null);

        g.drawImage(SpriteManager.x.getBufferedImage(), (int)(words.get(4).getWordX()-45*2), words.get(4).getWordY()-32-5, 45, 45, null);
        g.drawImage(SpriteManager.c.getBufferedImage(), (int)(words.get(5).getWordX()-45*2.5), words.get(5).getWordY()-32-5, 45, 45, null);
        g.drawImage(SpriteManager.x.getBufferedImage(), (int)(words.get(5).getWordX()-45*1.5), words.get(5).getWordY()-32-5, 45, 45, null);

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
        cam.update(player);


        // Check if on the ice
        if(player.isOnTheIce() && player.getCurrentState() != PlayerState.standing) {
            player.setCurrentState(PlayerState.iceSkating);
        }

        //Check if on the ground
        if(!player.isInTheAir() && !player.isOnTheGround()) {
            player.setCurrentState(PlayerState.falling);
        }

        if((player.isGoaled())) {
            gameStateManager.setLevelState(new Level1State(gameStateManager));
        }
    }

    @Override
    public LevelState getInstance() {
        return new Level0State(gameStateManager);
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
