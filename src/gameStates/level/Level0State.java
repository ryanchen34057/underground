
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
    int x1,y1;
    int x2,y2;
    int x3,y3;
    private ArrayList<Words> words;
 
    
    
    public Level0State(GameStateManager gameStateManager) {
        super(gameStateManager);
        words = new ArrayList<>();
        words.add(new Words("< > = Move", 20, (int)(Game.WIDTH*Game.SCALE*0.6), (int)(Game.HEIGHT*Game.SCALE*0.70)));
        words.add(new Words("Esc = Pause", 20, (int)(Game.WIDTH*Game.SCALE*0.6), (int)(Game.HEIGHT*Game.SCALE*0.74)));
        words.add(new Words("C = Jump", 20, (int)(Game.WIDTH*Game.SCALE*1.80), (int)(Game.HEIGHT*Game.SCALE*0.70)));
        words.add(new Words("C + -> = Climb", 20, (int)(Game.WIDTH*Game.SCALE*1.80), (int)(Game.HEIGHT*Game.SCALE*0.74)));
        words.add(new Words("X = Dash", 20, (int)(Game.WIDTH*Game.SCALE*3.2), (int)(Game.HEIGHT*Game.SCALE*0.70)));
        words.add(new Words("C+X = AirDash", 20, (int)(Game.WIDTH*Game.SCALE*3.2), (int)(Game.HEIGHT*Game.SCALE*0.74)));
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
        
        for(Words n: words){
                n.paint(g);
        }
        
        //  
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
