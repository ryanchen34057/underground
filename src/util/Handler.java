//package util;
//
//import UI.Game;
//import effects.DeathParticle;
//import effects.Effect;
//import effects.ParticleSystem;
//import enums.Direction;
//import gameObject.tiles.movable.FallingRock;
//import gameObject.tiles.portal.Portal;
//import gameObject.tiles.prize.Coin;
//import gameObject.tiles.wall.IceWall;
//import gameObject.tiles.wall.VanishingRock;
//import gameObject.tiles.wall.Wall;
//import graphics.SpriteManager;
//import gameObject.tiles.*;
//import gameObject.character.Entity;
//import enums.Id;
//import gameObject.character.Player;
//import gameObject.tiles.trap.*;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.util.LinkedList;
//
//public class Handler {
//    public  LinkedList<Entity> entities;
//    public  LinkedList<Tile> tiles;
//    public  LinkedList<Effect> effects;
//    public  LinkedList<ParticleSystem> particles;
//    public  Dimension bluePortalCor;
//    private Player player;
//
//
//    public Handler() {
//        entities = new LinkedList<>();
//        tiles = new LinkedList<>();
//        effects = new LinkedList<>();
//        particles = new LinkedList<>();
//    }
//
//    public void addObject(Entity en) {
//        entities.add(en);
//    }
//    public void addObject(Tile en) {
//        tiles.add(en);
//    }
//    public void addObject(Effect e) {
//        if(!effects.contains(e)) {
//            effects.add(e);
//        }
//    }
//    public static void addEffect(Effect e) {
//        if(!effects.contains(e)) {
//            effects.add(e);
//        }
//    }
//    public void addObject(ParticleSystem p) { particles.add(p); }
//
//
//    public void paint(Graphics g) {
//        Tile t;
//        for(int i=0;i<tiles.size();i++) {
//            t = tiles.get(i);
//            if(inTheScreen(t)) {
//                tiles.get(i).paint(g);
//            }
//
//        }
//        for(int i=0;i<effects.size();i++) {
//            effects.get(i).paint(g);
//        }
//        for(int i=0;i<particles.size();i++) {
//            particles.get(i).paint(g);
//        }
//        for(int i=0;i<entities.size();i++) {
//            entities.get(i).paint(g);
//        }
//    }
//
//    public void update() {
//        for(int i=0;i<entities.size();i++) {
//            player = (Player) entities.get(i);
//            player.setInTheGame(true);
//            player.update();
//            if(player.getCurrentEffect() != null && effects.size() == 0) {
//                addObject(player.getCurrentEffect());
//            }
//            if(player.isDead()) {
//                for(int j=0;j<8;j++) {
//                    addObject(DeathParticle.getInstance(player,j));
//                }
//                entities.remove(player);
//            }
//        }
//        Effect e;
//        for(int i=0;i<effects.size();i++) {
//            e = effects.get(i);
//            e.update();
//            if(e.isDead()) {
//                effects.remove(e);
//            }
//        }
//        ParticleSystem p;
//        for(int i=0;i<particles.size();i++) {
//            p =  particles.get(i);
//            p.update();
//            if(p.isDead()) {
//                particles.remove(p);
//            }
//        }
//        Tile t;
//        for(int i=0;i<tiles.size();i++) {
//            t = tiles.get(i);
//            if(t instanceof FallingRock && Math.abs(player.getX() - t.getX()) < 100
//                    && !((FallingRock) t).isFallen() && t.getY() <= player.getY()) {
//                ((FallingRock) t).setShaking(true);
//            }
//            if(t instanceof VanishingRock && t.isDead()) {
//                tiles.remove(t);
//            }
//           t.update();
//            if(t.isDead()) {
//                tiles.remove(i);
//            }
//        }
//    }
//
//    public void createLevel1(BufferedImage level) {
//        int width = level.getWidth();
//        int height = level.getHeight();
//
//        for(int y=0;y<height;y++) {
//            for (int x = 0; x < width; x++) {
//                int pixel = level.getRGB(x, y);
//                int red = (pixel >> 16) & 0xff;
//                int green = (pixel >> 8) & 0xff;
//                int blue = (pixel) & 0xff;
//
//                if (red == 99 && green == 99 && blue == 99) {
//                    addObject(new Hole(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.hole));
//                }
//                // Bigger block
//                else if (red == 50 && green == 50 && blue == 19) {
//                    addObject(new Wall(x * 64, y * 64, Wall.TILE_SIZE * 5, Wall.TILE_SIZE * 5, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
//                } else if (red == 100 && green == 50 && blue == 19) {
//                    addObject(new Wall(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
//                } else if (red == 100 && green == 0 && blue <= 20) {
//                    if (blue == 5 || blue == 4) {
//                        addObject(new Wall(x * 64, y * 64, Wall.TILE_SIZE / 2, Wall.TILE_SIZE, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
//                    }
//                    if (blue == 17) {
//                        addObject(new Wall(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE / 2, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
//                    } else {
//                        addObject(new Wall(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
//                    }
//
//                } else if (red == 100 && green == 100 && blue <= 29) {
//                    addObject(new Decor(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.decor, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
//                } else if (red == 255 && green == 255 && blue == 0) {
//                    addObject(new VanishingRock(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.vanishingRock));
//                } else if (red == 0 && green == 30 && blue == 255) {
//                    addObject(new IceWall(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.icewall1, SpriteManager.level1Sprites.get(green - 1).getBufferedImage()));
//                } else if (red == 0 && green == 255 && blue == 31) {
//                    addObject(new Spike(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.upwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.UP));
//                } else if (red == 0 && green == 255 && blue == 32) {
//                    addObject(new Spike(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.downwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.DOWN));
//                } else if (red == 0 && green == 255 && blue == 33) {
//                    addObject(new Spike(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.leftwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.LEFT));
//                } else if (red == 0 && green == 255 && blue == 34) {
//                    addObject(new Spike(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.rightwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.RIGHT));
//                } else if (red == 255 && green == 100 && blue == 35) {
//                    addObject(new FallingRock(x * 64, y * 64, Wall.TILE_SIZE * 3, Wall.TILE_SIZE * 3, Id.fallingRock, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
//                } else if (red == 100 && green == 0 && blue >= 36) {
//                    if (blue == 42 || blue == 43) {
//                        addObject(new Wall(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE / 2 - 10, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
//                    } else {
//                        addObject(new Wall(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
//                    }
//
//                } else if (red == 255 && green == 0 && blue == 0) {
//                    addObject(new Coin(x * 64, y * 64, Coin.PRIZE_SIZE, Coin.PRIZE_SIZE, 1000, Id.coin));
//                }
//
//
//                //Portal
////              //Blue
//                else if (red == 0 && green == 0 && blue == 255 || red == 0 && green == 1 && blue == 255) {
//                    Direction direction = (green == 1) ? Direction.LEFT : Direction.RIGHT;
//                    addObject(new Portal(x * 64, y * 64, Portal.PORTAL_SIZE, Portal.PORTAL_SIZE, Id.bluePortal, Color.BLUE, direction));
//                    player = new Player(x * 64 + 35, y * 64 + 35, Player.WIDTH, Player.HEIGHT, Id.player);
//                    addObject(player);
//                    bluePortalCor = new Dimension(x * 64, y * 64);
//                }
//                //Purple
//                else if (red == 255 && green == 0 && blue == 255 || red == 255 && green == 1 && blue == 255) {
//                    Direction direction = (green == 1) ? Direction.LEFT : Direction.RIGHT;
//                    addObject(new Portal(x * 64, y * 64, Portal.PORTAL_SIZE, Portal.PORTAL_SIZE, Id.purplePortal, Color.MAGENTA, direction));
//                }
//            }
//        }
//    }
//
//    public LinkedList<Entity> getEntities() {
//        return entities;
//    }
//
//    public LinkedList<Tile> getTiles() {
//        return tiles;
//    }
//
//    public LinkedList<Effect> getEffects() {
//        return effects;
//    }
//
//    private boolean inTheScreen(Tile t) {
//        return (t.getX() >= player.getX() - (Game.WIDTH * Game.SCALE)) && (t.getX() < player.getX() + (Game.WIDTH * Game.SCALE))
//                && (t.getY() >= player.getY() - (Game.HEIGHT * Game.SCALE)) && (t.getY() < player.getY() + (Game.HEIGHT * Game.SCALE));
//    }
//}
