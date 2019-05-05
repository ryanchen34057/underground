package gameStates;

import UI.Game;
import UI.Window;
import effects.DeathParticle;
import effects.Effect;
import effects.LandingEffect;
import effects.ParticleSystem;
import enums.Direction;
import enums.Id;
import gameObject.tiles.Decor;
import gameObject.tiles.Tile;
import gameObject.character.Player;
import gameObject.tiles.movable.FallingRock;
import gameObject.tiles.portal.Portal;
import gameObject.tiles.prize.Emerald;
import gameObject.tiles.trap.Hole;
import gameObject.tiles.trap.Spike;
import gameObject.tiles.trap.Spring;
import gameObject.tiles.wall.IceWall;
import gameObject.tiles.wall.VanishingRock;
import gameObject.tiles.wall.Wall;
import graphics.SpriteManager;
import input.Input;
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

    // SerialNum of the emerald;
    protected int emeraldSerial;

    protected LinkedList<FallingRock> fallingRocks;
    protected static Camera cam;
    private float alpha;
    protected int mapWidth;
    protected int mapHeight;

    public LevelState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    public abstract LevelState getInstance();

    public void levelObjectInit() {
        tiles = new LinkedList<>();
        effects = new LinkedList<>();
        particles = new LinkedList<>();
        fallingRocks = new LinkedList<>();
        cam = new Camera();
        emeraldSerial = 0;
        alpha = 0;
    }

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
        Graphics2D g2 = (Graphics2D) g;
        if(alpha <= 0.99) {
            fadeIn(g2);
        }
        else {
            background.paint(g2);
            g2.translate(cam.getX(), cam.getY());
            paintAllGameObject(g2);
            g2.translate(-cam.getX(), -cam.getY());
        }


    }

    private void fadeIn(Graphics2D g2) {
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
        g2.setComposite(ac);
        background.paint(g2);
        g2.translate(cam.getX(), cam.getY());
        paintAllGameObject(g2);
        g2.translate(-cam.getX(), -cam.getY());
        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1);
        g2.setComposite(ac);
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
        if(alpha <= 0.97) alpha += 0.03;
        //Update player
        player.update();

        Tile t;
        player.setOnTheGround(false);
        for(int i=0;i<tiles.size();i++) {
            t = tiles.get(i);
            if(inTheScreen(t)) {
                t.update();
                if(t instanceof FallingRock) {
                    if (Math.abs(player.getY() - t.getY()) < 1000 * Game.heightRatio && Math.abs(player.getX() - t.getX()) <  150 * Game.widthRatio && !((FallingRock) t).isFallen() && t.getY() <= player.getY()) {
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
                    if (t.getBounds() != null && !(t instanceof FallingRock) && fr.collidesWith(t, Tile::getBounds)) {
                        if(t instanceof VanishingRock) {
                            if(!((VanishingRock) t).isStepOn()) {
                                fr.handleCollision(t, null);
                            }
                        }
                        else {
                            fr.handleCollision(t, null);
                        }
                    }
                }
                // ****************************************************
            }
            if(t.isDead()) {
                if(t instanceof Emerald) {
                    gameStateManager.incrementEmeraldCount();
                }
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
        // ********* Player death ************
        if(player.isDead()) {
            for(int j=0;j<8;j++) {
                particles.add(DeathParticle.getInstance(player,j));
            }
            deathDelay++;
            if(deathDelay >= DEATH_DELAY_TIME) {
                deathDelay = 0;
                gameStateManager.incrementDeathCount();
                gameStateManager.setLevelState(getInstance());
            }
        }
        // ************************************
    }

    @Override
    public String toString() {
        return "LevelState";
    }

    private boolean inTheScreen(Tile t) {
        return (t.getX() >= player.getX() - (Window.scaledGameWidth)) && (t.getX() < player.getX() + (Window.scaledGameWidth))
                && (t.getY() >= player.getY() - (Window.scaledGameHeight)) && (t.getY() < player.getY() + (Window.scaledGameHeight));
    }

    public void createLevel(BufferedImage level) {
        int width = level.getWidth();
        int height = level.getHeight();
        mapWidth = width;
        mapHeight = height;

        for(int y=0;y<height;y++) {
            for (int x = 0; x < width; x++) {
                int pixel = level.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (red == 99 && green == 99 && blue == 99) {
                    tiles.add(new Hole(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.hole));
                }
                // Bigger block
                else if (red == 50 && green == 50 && blue == 19) {
                    tiles.add(new Wall(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE * 5, Wall.TILE_SIZE * 5, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                } else if (red == 100 && green == 50 && blue == 19) {
                    tiles.add(new Wall(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                } else if (red == 100 && green == 0 && blue <= 20) {
                    if (blue == 5 || blue == 4) {
                        System.out.println(blue);
                        tiles.add(new Wall(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.halfWidthWall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    }
                    else if (blue == 17 || blue == 18 || blue == 20) {
                        tiles.add(new Wall(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.halfHeightWall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    }
                    else {
                        tiles.add(new Wall(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    }

                } else if (red == 100 && green == 100 && blue <= 29) {
                    tiles.add(new Decor(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.decor, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                } else if (red == 255 && green == 255 && blue == 0) {
                    tiles.add(new VanishingRock(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.vanishingRock));
                } else if (red == 0 && green == 30 && blue == 255) {
                    tiles.add(new IceWall(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.icewall1, SpriteManager.level1Sprites.get(green - 1).getBufferedImage()));
                } else if (red == 0 && green == 255 && blue == 31) {
                    tiles.add(new Spike(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.upwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.UP));
                } else if (red == 0 && green == 255 && blue == 32) {
                    tiles.add(new Spike(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.downwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.DOWN));
                } else if (red == 0 && green == 255 && blue == 33) {
                    tiles.add(new Spike(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.leftwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.LEFT));
                } else if (red == 0 && green == 255 && blue == 34) {
                    tiles.add(new Spike(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.rightwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.RIGHT));
                } else if (red == 255 && green == 100 && blue == 35) {
                    FallingRock fr = new FallingRock(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE * 3, Wall.TILE_SIZE * 3, Id.fallingRock, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage());
                    tiles.add(fr);
                    fallingRocks.add(fr);
                } else if (red == 100 && green == 0 && blue >= 36) {
                    if (blue == 42 || blue == 43) {
                        tiles.add(new Wall(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, (int)(22*Game.widthRatio), Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    } else {
                        tiles.add(new Wall(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    }

                } else if (red == 255 && green == 0 && blue == 0) {
                    Emerald emerald;
                    if(gameStateManager.getEmerald(getLevel(), emeraldSerial) == null) {
                        emerald = new Emerald(x * Emerald.PRIZE_SIZE, y * Emerald.PRIZE_SIZE, Emerald.PRIZE_SIZE, Emerald.PRIZE_SIZE, 1000, Id.emerald, emeraldSerial);
                        gameStateManager.addEmerald(getLevel(), emeraldSerial, emerald);
                    }
                    else {
                        emerald = gameStateManager.getEmerald(getLevel(), emeraldSerial);
                        if(emerald.isEaten()) {
                            emerald = null;
                        }
                    }
                    emeraldSerial++;
                    if(emerald != null) {
                        tiles.add(emerald);
                    }
                }
                else if(red == 255 && green == 150 && blue == 150) {
                    tiles.add(new Spring(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.spring, Direction.UP));
                }
                else if(red == 255 && green == 160 && blue == 160) {
                    tiles.add(new Spring(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.spring, Direction.DOWN));
                }
                else if(red == 255 && green == 170 && blue == 170) {
                    tiles.add(new Spring(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.spring, Direction.LEFT));
                }
                else if(red == 255 && green == 180 && blue == 180) {
                    tiles.add(new Spring(x * Wall.TILE_SIZE, y * Wall.TILE_SIZE, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.spring, Direction.RIGHT));
                }

                //Portal
//              //Blue
                else if (red == 0 && green == 0 && blue == 255 || red == 0 && green == 1 && blue == 255) {
                    Direction direction = (green == 1) ? Direction.LEFT : Direction.RIGHT;
                    tiles.add(new Portal(x *  Wall.TILE_SIZE, y *  Wall.TILE_SIZE, Portal.PORTAL_SIZE, Portal.PORTAL_SIZE, Id.bluePortal, Color.BLUE, direction));
                    bluePortalCor = new Dimension(x *  Wall.TILE_SIZE, y * Wall.TILE_SIZE);
                }
                //Purple
                else if (red == 255 && green == 0 && blue == 255 || red == 255 && green == 1 && blue == 255) {
                    Direction direction = (green == 1) ? Direction.LEFT : Direction.RIGHT;
                    tiles.add(new Portal(x *  Wall.TILE_SIZE, y *  Wall.TILE_SIZE, Portal.PORTAL_SIZE, Portal.PORTAL_SIZE, Id.purplePortal, Color.MAGENTA, direction));
                }
            }
        }
        emeraldSerial = 0;
    }

    public abstract int getLevel();
}
