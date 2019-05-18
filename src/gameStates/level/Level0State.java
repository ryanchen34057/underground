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
            words.add(new Words("    = Move", 20, (int) (768 * Game.widthRatio), (int) (750 * Game.heightRatio)));
            words.add(new Words("   = Pause", 20, (int) (768 * Game.widthRatio), (int) (795 * Game.heightRatio)));
            words.add(new Words("  = Jump", 20, (int) (2304 * Game.widthRatio), (int) (750 * Game.heightRatio)));
            words.add(new Words("   +   = Bounce", 20, (int) (2304 * Game.widthRatio), (int) (795 * Game.heightRatio)));
            words.add(new Words("  = Dash", 20, (int) (4096 * Game.widthRatio), (int) (750 * Game.heightRatio)));
            words.add(new Words("  +   = AirDash", 20, (int) (4096 * Game.widthRatio), (int) (790 * Game.heightRatio)));
            words.add(new Words("  +   +   = Vertical AirDash", 20, (int) (5300 * Game.widthRatio), (int) (850 * Game.heightRatio)));
      }

      @Override
      public void init() {
            SpriteManager.levelInit();
            levelObjectInit();
            createLevel(SpriteManager.level0);
            background = new Background("/res/background2.jpg", 1.0f);
            player = new Player((int)(96*Game.widthRatio), (int)(96*Game.widthRatio), Id.player);
            player.setPosition((int) bluePortalCor.getWidth(), (int) bluePortalCor.getHeight());
            //Signboard 
            signImage = SpriteManager.signboard.getBufferedImage();
      }

      @Override
      public void paint(Graphics g) {
            background.paint(g);
            g.translate(cam.getX(), cam.getY());
            //Signboard
            g.drawImage(SpriteManager.signboard.getBufferedImage(), words.get(0).getWordX() - (int) (144 * Game.widthRatio), words.get(0).getWordY() - (int) (80 * Game.heightRatio), (int) (288 * Game.widthRatio), (int) (192 * Game.heightRatio), null);
            g.drawImage(SpriteManager.signboard.getBufferedImage(), words.get(2).getWordX() - (int) (144 * Game.widthRatio), words.get(0).getWordY() - (int) (80 * Game.heightRatio), (int) (288 * Game.widthRatio), (int) (192 * Game.heightRatio), null);
            g.drawImage(SpriteManager.signboard.getBufferedImage(), words.get(4).getWordX() - (int) (144 * Game.widthRatio), words.get(0).getWordY() - (int) (80 * Game.heightRatio), (int) (288 * Game.widthRatio), (int) (192 * Game.heightRatio), null);
            g.drawImage(SpriteManager.signboard.getBufferedImage(), (int)(words.get(6).getWordX()*0.95), words.get(0).getWordY() - (int) (80 * Game.heightRatio) + (int)(64*Game.widthRatio), (int) (500 * Game.widthRatio), (int) (192 * Game.heightRatio), null);
            //Words
            for (Words n : words) {
                  n.paint(g);
            }
            //keys
            g.drawImage(SpriteManager.left.getBufferedImage(), (int) (words.get(0).getWordX()*0.9), words.get(0).getWordY()-(int)(32 * Game.widthRatio), (int)(32 * Game.widthRatio) , (int)(32 * Game.widthRatio ), null);
            g.drawImage(SpriteManager.right.getBufferedImage(), (int) (words.get(0).getWordX()*0.9) + (int)(32 * Game.widthRatio), words.get(0).getWordY()-(int)(32 * Game.widthRatio), (int)(32 * Game.widthRatio ), (int)(32 * Game.widthRatio ), null);
            g.drawImage(SpriteManager.esc.getBufferedImage(), (int) (words.get(1).getWordX()*0.92), words.get(1).getWordY()-(int)(32 * Game.widthRatio), (int)(32 * Game.widthRatio), (int)(32 * Game.widthRatio), null);

            g.drawImage(SpriteManager.c.getBufferedImage(), (int) (words.get(2).getWordX()*0.97), words.get(2).getWordY()-(int)(32 * Game.widthRatio), (int)(45 * Game.widthRatio), (int)(45 * Game.widthRatio ), null);
            g.drawImage(SpriteManager.right.getBufferedImage(), (int) (words.get(3).getWordX()*0.96), words.get(3).getWordY()-(int)(27 * Game.widthRatio), (int)(32 * Game.widthRatio), (int)(32 * Game.widthRatio ), null);
            g.drawImage(SpriteManager.c.getBufferedImage(), (int) (words.get(3).getWordX()*0.98), words.get(3).getWordY()-(int)(32 * Game.widthRatio), (int)(45 * Game.widthRatio), (int)(45 * Game.widthRatio ), null);

            g.drawImage(SpriteManager.x.getBufferedImage(), (int) (words.get(4).getWordX()*0.985), words.get(4).getWordY()-(int)(32 * Game.widthRatio), (int)(45 * Game.widthRatio), (int)(45 * Game.widthRatio ), null);
            g.drawImage(SpriteManager.c.getBufferedImage(), (int) (words.get(5).getWordX()*0.974), words.get(5).getWordY()-(int)(32 * Game.widthRatio), (int)(45 * Game.widthRatio), (int)(45 * Game.widthRatio ), null);
            g.drawImage(SpriteManager.x.getBufferedImage(), (int) (words.get(5).getWordX()*0.985), words.get(5).getWordY()-(int)(32 * Game.widthRatio), (int)(45 * Game.widthRatio), (int)(45 * Game.widthRatio ), null);

            g.drawImage(SpriteManager.c.getBufferedImage(), (int) (words.get(6).getWordX()*0.965), words.get(6).getWordY()-(int)(32 * Game.widthRatio), (int)(45 * Game.widthRatio ), (int)(45 * Game.widthRatio ), null);
            g.drawImage(SpriteManager.up.getBufferedImage(), (int) (words.get(6).getWordX()*0.975), words.get(6).getWordY()-(int)(46 * Game.widthRatio), (int)(32 * Game.widthRatio ), (int)(32 * Game.widthRatio ), null);
            g.drawImage(SpriteManager.right.getBufferedImage(), (int) (words.get(6).getWordX()*0.975), words.get(6).getWordY()-(int)(12 * Game.widthRatio), (int)(32 * Game.widthRatio ), (int)(32 * Game.widthRatio ), null);
            g.drawImage(SpriteManager.x.getBufferedImage(), (int) (words.get(6).getWordX()*0.985), words.get(6).getWordY()-(int)(32 * Game.widthRatio), (int)(45 * Game.widthRatio ), (int)(45 * Game.widthRatio ), null);


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
            if (player.getCurrentEffect() != null && effects.size() == 0) {
                  effects.add(player.getCurrentEffect());
                  player.setCurrentEffect(null);
            }

            // Update all game object
            updateAllGameObject();
            cam.update(player, mapWidth, mapHeight);

            // Check if on the ice
            if (player.isOnTheIce() && player.getCurrentState() != PlayerState.standing) {
                  player.setCurrentState(PlayerState.iceSkating);
            }

            //Check if on the ground
            if (!player.isInTheAir() && !player.isOnTheGround()) {
                  player.setCurrentState(PlayerState.falling);
            }

            if ((player.isGoaled())) {
                  gameStateManager.setLevelState(new Level1State(gameStateManager));
                  if(gameStateManager.getLevelReached() == 0) {
                        gameStateManager.incrementLevelReached();
                  }
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
