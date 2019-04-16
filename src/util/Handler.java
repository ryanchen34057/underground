package util;

import prize.Prize;
import UI.Game;
import character.Entity;
import character.Id;
import character.Player;
import prize.Coin;
import level.Spike;
import level.Tile;
import level.Wall;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Handler {
    private static LinkedList<Entity> entities;
    private static LinkedList<Tile> tiles;
    private static LinkedList<Prize> prizes;

    public Handler() {
        entities = new LinkedList<>();
        tiles = new LinkedList<>();
        prizes = new LinkedList<>();
    }

    //getters
    public LinkedList<Entity> getEntities() {
        return entities;
    }
    public LinkedList<Tile> getTiles() {
        return tiles;
    }
    public LinkedList<Prize> getPrizes() { return prizes; }

    public void addObject(Entity en) {
        entities.add(en);
    }
    public void addObject(Tile en) {
        tiles.add(en);
    }
    public void addObject(Prize p) {
        prizes.add(p);
    }

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
    }

    public void update() {
        for(int i=0;i<entities.size();i++) {
            entities.get(i).update();
        }
        for(int i=0;i<prizes.size();i++) {
            prizes.get(i).update();
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

                if(red == 0 && green == 0 && blue == 0) {
                    addObject(new Wall(x*64, y*64, Game.TILE_SIZE, Game.TILE_SIZE, false, Id.wall));
                }
                if(red == 255 && green == 0 && blue == 255) {
                    addObject(new Player(x*64, y*64, Game.playerWidth, Game.playerHeight,Id.player));
                }
                if(red == 0 && green == 255 && blue == 0) {
                    addObject(new Spike(x*64, y*64, Game.TILE_SIZE, Game.TILE_SIZE, false, Id.spike));
                }
                if(red == 255 && green == 0 && blue == 0) {
                    addObject(new Coin(x*64, y*64, Game.TILE_SIZE, Game.TILE_SIZE, 10, Id.coin));
                }
            }
        }
    }

    public static Player getPlayer() { return (Player) entities.get(0); }
}
