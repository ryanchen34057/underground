package util;

import effects.Effect;
import effects.ParticleSystem;
import tiles.*;
import prize.Prize;
import UI.Game;
import character.Entity;
import character.Id;
import character.Player;
import prize.Coin;
import tiles.trap.DownwardSpike;
import tiles.trap.LeftwardSpike;
import tiles.trap.RightwardSpike;
import tiles.trap.UpwardSpike;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Handler {
    private static LinkedList<Entity> entities;
    private static LinkedList<Tile> tiles;
    private static LinkedList<Prize> prizes;
    private static LinkedList<Effect> effects;
    private static LinkedList<ParticleSystem> particles;

    public Handler() {
        entities = new LinkedList<>();
        tiles = new LinkedList<>();
        prizes = new LinkedList<>();
        effects = new LinkedList<>();
        particles = new LinkedList<>();
    }

    //getters
    public LinkedList<Entity> getEntities() {
        return entities;
    }
    public LinkedList<Tile> getTiles() {
        return tiles;
    }
    public LinkedList<Prize> getPrizes() { return prizes; }
    public LinkedList<Effect> getEffects() { return effects; }
    public static LinkedList<ParticleSystem> getParticleSystems() { return particles; }

    public static void addObject(Entity en) {
        entities.add(en);
    }
    public static void addObject(Tile en) {
        tiles.add(en);
    }
    public static void addObject(Prize p) {
        prizes.add(p);
    }
    public static void addObject(Effect e) { effects.add(e); }
    public static void addObject(ParticleSystem p) { particles.add(p); }


    public void paint(Graphics g) {
        for(int i=0;i<entities.size();i++) {
            entities.get(i).paint(g);
        }
        for(int i=0;i<tiles.size();i++) {
            tiles.get(i).paint(g);
        }

        for(int i=0;i<prizes.size();i++) {
            prizes.get(i).paint(g);
        }
        for(int i=0;i<effects.size();i++) {
            effects.get(i).paint(g);
        }
        for(int i=0;i<particles.size();i++) {
            particles.get(i).paint(g);
        }

    }

    public void update() {
        for(int i=0;i<entities.size();i++) {
            entities.get(i).update();
        }
        for(int i=0;i<prizes.size();i++) {
            prizes.get(i).update();
        }
        for(int i=0;i<effects.size();i++) {
            effects.get(i).update();
        }
        for(int i=0;i<particles.size();i++) {
            particles.get(i).update();
        }
    }

    public void createLevel(BufferedImage level) {
        int width = level.getWidth();
        int height = level.getHeight();

        for(int y=0;y<height;y++) {
            for(int x=0;x<width;x++) {
                int pixel = level.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;


                //Player
                if(red == 255 && green == 0 && blue == 255) {
                    addObject(new Player(x*64, y*64, Game.playerWidth, Game.playerHeight,Id.player));
                }

                //Wall
                if(red == 0 && green == 0 && blue == 0) {
                    addObject(new Wall(x*64, y*64, Game.TILE_SIZE, Game.TILE_SIZE, false, Id.wall));
                }

                //Breakable Wall
                if(red == 0 && green == 0 && blue == 150) {
                    addObject(new Wall(x*64, y*64, Game.TILE_SIZE, Game.TILE_SIZE, true, Id.wall));
                }

                //Spike
                if(red == 0 && green == 255 && blue == 0) {
                    addObject(new UpwardSpike(x*64, y*64, Game.TILE_SIZE, Game.TILE_SIZE, false, Id.upwardSpike));
                }
                if(red == 0 && green == 100 && blue == 0) {
                    addObject(new DownwardSpike(x*64, y*64, Game.TILE_SIZE, Game.TILE_SIZE, false, Id.downwardSpike));
                }
                if(red == 0 && green == 150 && blue == 0) {
                    addObject(new LeftwardSpike(x*64, y*64, Game.TILE_SIZE, Game.TILE_SIZE, false, Id.leftwardSpike));
                }
                if(red == 0 && green == 200 && blue == 0) {
                    addObject(new RightwardSpike(x*64, y*64, Game.TILE_SIZE, Game.TILE_SIZE, false, Id.rightwardSpike));
                }

                //Coin
                if(red == 255 && green == 0 && blue == 0) {
                    addObject(new Coin(x*64, y*64, Game.TILE_SIZE, Game.TILE_SIZE, 10, Id.coin));
                }
            }
        }
    }

    public static Player getPlayer() { return (Player) entities.get(0); }
    public static void effectRemove() { effects.clear(); }
}
