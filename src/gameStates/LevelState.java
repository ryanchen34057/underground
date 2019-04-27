package gameStates;

import UI.Game;
import effects.DeathParticle;
import effects.Effect;
import effects.LandingEffect;
import effects.ParticleSystem;
import enums.Direction;
import gameObject.tiles.Decor;
import gameObject.tiles.Tile;
import enums.Id;
import gameObject.character.Player;
import gameObject.tiles.movable.FallingRock;
import gameObject.tiles.portal.Portal;
import gameObject.tiles.prize.Coin;
import gameObject.tiles.trap.Hole;
import gameObject.tiles.trap.Spike;
import gameObject.tiles.wall.IceWall;
import gameObject.tiles.wall.VanishingRock;
import gameObject.tiles.wall.Wall;
import graphics.SpriteManager;
import input.Input;
import map.Background;
import record.Timer;
import states.PlayerState;
import util.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public abstract class LevelState extends GameState {
    public static final int DEATH_DELAY_TIME = 20;
    protected Player player;
    protected LinkedList<Tile> tiles;
    protected  LinkedList<Effect> effects;
    protected  LinkedList<ParticleSystem> particles;

    // Coordinate of blue portal(where player spawns)
    protected  Dimension bluePortalCor;

    protected LinkedList<FallingRock> fallingRocks;
    protected static Camera cam;

    public LevelState(GameStateManager gameStateManager) {
        super(gameStateManager);
        init();
    }

    public abstract LevelState getInstance();

    @Override
    public void handleKeyInput() {
        if(player != null) {
            player.handleKeyInput();
        }
        //ESC - pause
        if(Input.keys.get(8).down) {
            gameStateManager.setGameState(new PauseState(gameStateManager));
        }
    }

    @Override
    public void paint(Graphics g) {
        background.paint(g);
        g.translate(cam.getX(), cam.getY());
        paintAllGameObject(g);
        g.translate(-cam.getX(), -cam.getY());
    }

    public void paintAllGameObject(Graphics g) {
        player.paint(g);
        Tile t;
        for(int i=0;i<tiles.size();i++) {
            t = tiles.get(i);
            if(inTheScreen(t)) {
                t.paint(g);
            }
        }
        for(int i=0;i<effects.size();i++) {
            effects.get(i).paint(g);
        }
        for(int i=0;i<particles.size();i++) {
            particles.get(i).paint(g);
        }
    }

    public void updateAllGameObject() {
        player.update();
        Tile t;
        player.setOnTheGround(false);
        for(int i=0;i<tiles.size();i++) {
            t = tiles.get(i);
            if(inTheScreen(t)) {
                t.update();
                if(t instanceof FallingRock) {
                    if (Math.abs(player.getX() - t.getX()) < 100 && !((FallingRock) t).isFallen() && t.getY() <= player.getY()) {
                        ((FallingRock) t).setShaking(true);
                    }
                    if (((FallingRock) t).getCurrentEffect() instanceof LandingEffect) {
                        effects.add(LandingEffect.getInstance(t));
                        cam.setShaking(true, 10, 5);
                    }
                    ((FallingRock) t).setCurrentEffect(null);
                }
                // ********* Player collision detection **********
                if (t.getBounds() != null && player.inTheScreen(t)) {
                    player.handleCollision(t, player.checkCollisionBounds(t, Tile::getBounds));
                }
                // ***********************************************

                // ********* FallingRock collision detection **********
                for(FallingRock fr: fallingRocks) {
                    if (t.getBounds() != null && t instanceof Wall && fr.collidesWith(t, Tile::getBounds)) {
                        fr.handleCollision(t, null);
                    }
                }
                // ****************************************************
            }
            if(t.isDead()) {
                tiles.remove(t);
            }
        }
        Effect e;
        for(int i=0;i<effects.size();i++) {
            e = effects.get(i);
            e.update();
            if(e.isDead()) {
                effects.remove(e);
            }
        }
        ParticleSystem p;
        for(int i=0;i<particles.size();i++) {
            p =  particles.get(i);
            p.update();
            if(p.isDead()) {
                particles.remove(p);
            }
        }
    }

    @Override
    public String toString() {
        return "LevelState";
    }

    private boolean inTheScreen(Tile t) {
        return (t.getX() >= player.getX() - (Game.WIDTH * Game.SCALE)) && (t.getX() < player.getX() + (Game.WIDTH * Game.SCALE))
                && (t.getY() >= player.getY() - (Game.HEIGHT * Game.SCALE)) && (t.getY() < player.getY() + (Game.HEIGHT * Game.SCALE));
    }

    public abstract void createLevel(BufferedImage level);

}
