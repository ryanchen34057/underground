package util;

import UI.Game;
import effects.DeathParticle;
import effects.Effect;
import effects.ParticleSystem;
import gameObject.tiles.wall.IceWall;
import gameObject.tiles.wall.Wall;
import graphics.Sprite;
import graphics.SpriteManager;
import input.Input;
import gameObject.tiles.*;
import gameObject.tiles.movable.Torch;
import gameObject.character.Entity;
import enums.Id;
import gameObject.character.Player;
import gameObject.tiles.prize.Coin;
import gameObject.tiles.portal.BluePortal;
import gameObject.tiles.portal.PurplePortal;
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
                Game.cam.setShaking(true, 20, 5);
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
        for(int i=0;i<tiles.size();i++) {
            if(tiles.get(i).getId() == Id.bluePortal || tiles.get(i).getId() == Id.torch || tiles.get(i).getId() == Id.coin) {
                tiles.get(i).update();
            }
            if(tiles.get(i).isDead()) {
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

                if(red == 100 && green == 0 && blue <= 20) {
                    addObject(new Wall(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                }

                if(red == 100 && green == 0 && blue <= 29) {
                    addObject(new Decor(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                }
                if(red == 100 && green == 0 && blue == 30) {
                    addObject(new IceWall(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                }
                if(red == 100 && green == 0 && blue == 31) {
                    addObject(new UpwardSpike(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.upwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                }
                if(red == 100 && green == 0 && blue == 32) {
                    addObject(new DownwardSpike(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.downwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                }
                if(red == 100 && green == 0 && blue == 33) {
                    addObject(new LeftwardSpike(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.leftwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                }
                if(red == 100 && green == 0 && blue == 34) {
                    addObject(new DownwardSpike(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.rightwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                }


                //Portal
//                //Blue
                if(red == 0 && green == 0 && blue == 255) {
                    addObject(new BluePortal(x*64, y*64, BluePortal.PORTAL_SIZE, BluePortal.PORTAL_SIZE, false, Id.bluePortal));
                    player = new Player(x*64+35, y*64+35, Player.WIDTH, Player.HEIGHT, Id.player);
                    addObject(player);
                    bluePortalCor = new Dimension(x*64, y*64);
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
        return (t.getX() >= player.getX() - (Game.WIDTH * Game.SCALE) / 1.5) && (t.getX() < player.getX() + (Game.WIDTH * Game.SCALE))
                && (t.getY() >= player.getY() - (Game.HEIGHT * Game.SCALE) / 1.5) && (t.getY() < player.getY() + (Game.HEIGHT * Game.SCALE) / 1.5);
    }
}
