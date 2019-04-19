package util;

import effects.Effect;
import effects.ParticleSystem;
import input.Input;
import tiles.*;
import tiles.movable.Torch;
import character.Entity;
import character.Id;
import character.Player;
import tiles.prize.Coin;
import tiles.portal.BluePortal;
import tiles.portal.PurplePortal;
import tiles.trap.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Handler {
    public static LinkedList<Entity> entities;
    public static LinkedList<Tile> tiles;
    public static LinkedList<Effect> effects;
    public static LinkedList<ParticleSystem> particles;
    private Dimension bluePortalCor;


    public Handler() {
        entities = new LinkedList<>();
        tiles = new LinkedList<>();
        effects = new LinkedList<>();
        particles = new LinkedList<>();
    }

    public static void addObject(Entity en) {
        entities.add(en);
    }
    public static void addObject(Tile en) {
        tiles.add(en);
    }
    public static void addObject(Effect e) {
        if(!effects.contains(e)) {
            effects.add(e);
        }
    }
    public static void addObject(ParticleSystem p) { particles.add(p); }


    public void paint(Graphics g) {
        for(int i=0;i<tiles.size();i++) {
            tiles.get(i).paint(g);
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
            entities.get(i).update();
        }
        for(int i=0;i<effects.size();i++) {
            effects.get(i).update();
        }
        for(int i=0;i<particles.size();i++) {
            particles.get(i).update();
        }
        for(int i=0;i<tiles.size();i++) {
            if(tiles.get(i).getId() == Id.bluePortal || tiles.get(i).getId() == Id.torch) {
                tiles.get(i).update();
            }
        }

        // Respawn player
        if(Handler.entities.size() == 0 && Input.keys.get(6).down) {
            Handler.addObject(new Player((int)(bluePortalCor.getWidth()+35), (int)(bluePortalCor.getHeight()+35), Player.width, Player.height, Id.player));
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


                //Wall
                if(red == 0 && green == 0 && blue == 0) {
                    addObject(new Wall(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, false, Id.wall));
                }

                //Breakable Wall
                if(red == 0 && green == 0 && blue == 150) {
                    addObject(new Wall(x*64, y*64, Wall.TILE_SIZE, Wall.TILE_SIZE, true, Id.breakableWall));
                }

                //Spike
                if(red == 0 && green == 255 && blue == 0) {
                    addObject(new UpwardSpike(x*64, y*64, Spike.TILE_SIZE, Spike.TILE_SIZE, false, Id.upwardSpike));
                }
                if(red == 0 && green == 100 && blue == 0) {
                    addObject(new DownwardSpike(x*64, y*64, Spike.TILE_SIZE, Spike.TILE_SIZE, false, Id.downwardSpike));
                }
                if(red == 0 && green == 150 && blue == 0) {
                    addObject(new LeftwardSpike(x*64, y*64, Spike.TILE_SIZE, Spike.TILE_SIZE, false, Id.leftwardSpike));
                }
                if(red == 0 && green == 200 && blue == 0) {
                    addObject(new RightwardSpike(x*64, y*64, Spike.TILE_SIZE, Spike.TILE_SIZE, false, Id.rightwardSpike));
                }

                //Coin
                if(red == 255 && green == 0 && blue == 0) {
                    addObject(new Coin(x*64, y*64, Coin.PRIZE_SIZE, Coin.PRIZE_SIZE,  false, 1000, Id.coin));
                }

                //Portal
                //Blue
                if(red == 0 && green == 0 && blue == 255) {
                    addObject(new BluePortal(x*64, y*64, BluePortal.PORTAL_SIZE, BluePortal.PORTAL_SIZE, false, Id.bluePortal));
                    addObject(new Player(x*64+35, y*64+35, Player.width, Player.height, Id.player));
                    bluePortalCor = new Dimension(x*64, y*64);
                }
                //Purple
                if(red == 100 && green == 0 && blue == 255) {
                    addObject(new PurplePortal(x*64, y*64, BluePortal.PORTAL_SIZE, BluePortal.PORTAL_SIZE, false, Id.bluePortal));
                }

                //Torch
                if(red == 200 && green == 100 && blue == 0) {
                    addObject(new Torch(x*64, y*64, Torch.TILE_SIZE, Torch.TILE_SIZE, false, Id.torch));
                }
            }
        }
    }

    public static Player getPlayer() { return (Player) entities.get(0); }
    public static void effectRemove() { effects.clear(); }
}
