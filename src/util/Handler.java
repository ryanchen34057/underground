package util;

import UI.Game;
import effects.DeathParticle;
import effects.Effect;
import effects.ParticleSystem;
import gameObject.tiles.movable.FallingRock;
import gameObject.tiles.portal.PurplePortal;
import gameObject.tiles.prize.Coin;
import gameObject.tiles.wall.IceWall;
import gameObject.tiles.wall.Wall;
import graphics.SpriteManager;
import input.Input;
import gameObject.tiles.*;
import gameObject.character.Entity;
import enums.Id;
import gameObject.character.Player;
import gameObject.tiles.portal.BluePortal;
import gameObject.tiles.trap.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Handler {
    public static LinkedList<Entity> entities;
    public static LinkedList<Tile> tiles;
    public static LinkedList<Effect> effects;
    public static LinkedList<ParticleSystem> particles;
    public static Dimension bluePortalCor;
    private Player player;


    public Handler() {
        entities = new LinkedList<>();
        tiles = new LinkedList<>();
        effects = new LinkedList<>();
        particles = new LinkedList<>();
    }

    public void addObject(Entity en) {
        entities.add(en);
    }
    public void addObject(Tile en) {
        tiles.add(en);
    }
    public void addObject(Effect e) {
        if(!effects.contains(e)) {
            effects.add(e);
        }
    }
    public static void addEffect(Effect e) {
        if(!effects.contains(e)) {
            effects.add(e);
        }
    }
    public void addObject(ParticleSystem p) { particles.add(p); }


    public void paint(Graphics g) {
        Tile t;
        for(int i=0;i<tiles.size();i++) {
            t = tiles.get(i);
            if(inTheScreen(t)) {
                tiles.get(i).paint(g);
            }

        }
        for(int i=0;i<effects.size();i++) {
            effects.get(i).paint(g);
        }
        for(int i=0;i<particles.size();i++) {
            particles.get(i).paint(g);
        }
        for(int i=0;i<entities.size();i++) {
            entities.get(i).paint(g);
        }
    }

    public void update() {
        for(int i=0;i<entities.size();i++) {
            player = (Player) entities.get(i);
            player.setInTheGame(true);
            player.update();
            if(player.getCurrentEffect() != null && effects.size() == 0) {
                addObject(player.getCurrentEffect());
            }
            if(player.isDead()) {
                for(int j=0;j<8;j++) {
                    addObject(DeathParticle.getInstance(player,j));
                }
                entities.remove(player);
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
        Tile t;
        for(int i=0;i<tiles.size();i++) {
            t = tiles.get(i);
            if(t instanceof FallingRock && Math.abs(player.getX() - t.getX()) < 100 && !((FallingRock) t).isFallen()) {
                ((FallingRock) t).setShaking(true);
            }
           t.update();
            if(t.isDead()) {
                tiles.remove(i);
            }
        }

        // Respawn player
        if(entities.size() == 0 && Input.keys.get(6).down) {
            addObject(new Player((int)(bluePortalCor.getWidth()+35), (int)(bluePortalCor.getHeight()+35), Player.WIDTH, Player.HEIGHT, Id.player));
        }
    }
    
   

    public void createLevel1(BufferedImage level) {
        int width = level.getWidth();
        int height = level.getHeight();

        for(int y=0;y<height;y++) {
            for(int x=0;x<width;x++) {
                int pixel = level.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if(red == 99 && green == 99 && blue == 99) {
                    addObject(new Hole(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.hole));
                }
                else if(red == 50 && green == 50 && blue == 19) {
                    addObject(new Wall(x*64, y*64, Wall.TILE_SIZE*5, Wall.TILE_SIZE*5, false, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                }
                else if(red == 100 && green == 50 && blue == 19) {
                    addObject(new Wall(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                }
                else if(red == 100 && green == 0 && blue <= 20) {
                    if(blue == 5 || blue == 4) {
                        addObject(new Wall(x*64, y*64, Wall.TILE_SIZE/2, Wall.TILE_SIZE, false, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    }
                    else {
                        addObject(new Wall(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    }

                }

                else if(red == 100 && green == 100 && blue <= 29) {
                    addObject(new Decor(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.decor, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                }

                else if(red == 0 && green == 30 && blue == 255) {
                    addObject(new IceWall(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.icewall1, SpriteManager.level1Sprites.get(green - 1).getBufferedImage()));
                }
                else if(red == 0 && green == 255 && blue == 31) {
                    addObject(new UpwardSpike(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.upwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                }
                else if(red == 0 && green == 255 && blue == 32) {
                    addObject(new DownwardSpike(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.downwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                }
                else if(red == 0 && green == 255 && blue == 33) {
                    addObject(new LeftwardSpike(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.leftwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                }
                else if(red == 0 && green == 255 && blue == 34) {
                    addObject(new DownwardSpike(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.rightwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                }
                else if(red == 100 && green == 0 && blue == 35) {
                    addObject(new FallingRock(x*64, y*64, Wall.TILE_SIZE * 3, Wall.TILE_SIZE * 3, false, Id.fallingRock, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                }
                else if(red == 100 && green == 0 && blue >= 36) {
                    if(blue == 42 || blue == 43) {
                        addObject(new Wall(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE/2, false, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    }
                    else {
                        addObject(new Wall(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    }

                }
                else if(red == 255 && green == 0 && blue == 0) {
                    addObject(new Coin(x*64, y*64, Coin.PRIZE_SIZE, Coin.PRIZE_SIZE, false,1000, Id.coin));
                }


                //Portal
//              //Blue
                else if(red == 0 && green == 0 && blue == 255) {
                    addObject(new BluePortal(x*64, y*64, BluePortal.PORTAL_SIZE, BluePortal.PORTAL_SIZE, false, Id.bluePortal));
                    player = new Player(x*64+35, y*64+35, Player.WIDTH, Player.HEIGHT, Id.player);
                    addObject(player);
                    bluePortalCor = new Dimension(x*64, y*64);
                }
                //Purple
                else if(red == 255 && green == 0 && blue == 255) {
                    addObject(new PurplePortal(x*64, y*64, BluePortal.PORTAL_SIZE, BluePortal.PORTAL_SIZE, false, Id.purplePortal));
                }
            }
        }
    }

    public LinkedList<Entity> getEntities() {
        return entities;
    }

    public LinkedList<Tile> getTiles() {
        return tiles;
    }

    public LinkedList<Effect> getEffects() {
        return effects;
    }

    public LinkedList<ParticleSystem> getParticles() {
        return particles;
    }

    private boolean inTheScreen(Tile t) {
        return (t.getX() >= player.getX() - (Game.WIDTH * Game.SCALE)) && (t.getX() < player.getX() + (Game.WIDTH * Game.SCALE))
                && (t.getY() >= player.getY() - (Game.HEIGHT * Game.SCALE)) && (t.getY() < player.getY() + (Game.HEIGHT * Game.SCALE));
    }
}
