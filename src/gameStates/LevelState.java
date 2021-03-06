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
import gameObject.tiles.prize.Diamond;
import gameObject.tiles.prize.Emerald;
import gameObject.tiles.trap.Hole;
import gameObject.tiles.trap.Spike;
import gameObject.tiles.trap.Spring;
import gameObject.tiles.wall.IceWall;
import gameObject.tiles.wall.VanishingRock;
import gameObject.tiles.wall.Wall;
import graphics.SpriteManager;
import input.Input;
import states.PlayerState;
import util.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class LevelState extends GameState {
    private static final int DEATH_DELAY_TIME = 20;
    private static final float FADEIN_TIME = 1.8f;
    protected Player player;
    protected ArrayList<Tile> tiles;
    protected  LinkedList<Effect> effects;
    protected  LinkedList<ParticleSystem> particles;

    // Coordinate of blue portal(where player spawns)
    protected  Dimension bluePortalCor;

    // SerialNum of the emerald;
    protected int emeraldSerial;

    protected ArrayList<FallingRock> fallingRocks;
    protected static Camera cam;
    protected float alpha;
    protected int mapWidth;
    protected int mapHeight;

    public LevelState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    public abstract LevelState getInstance();

    public void levelObjectInit() {
        tiles = new ArrayList<>();
        effects = new LinkedList<>();
        particles = new LinkedList<>();
        fallingRocks = new ArrayList<>();
        cam = new Camera();
        emeraldSerial = 0;
        alpha = 0.0f;
    }

    @Override
    public void handleKeyInput() {
        if(player != null) {
            player.handleKeyInput();
        }
        if(!locked) {
            //ESC - pause
            if(Input.keys.get(8).down) {
                gameStateManager.setGameState(new PauseState(gameStateManager));
                locked = true;
            }

            //Debug mode G + B
            if(Input.keys.get(9).down && Input.keys.get(10).down) {
                Game.debugMode = !(Game.debugMode);
                locked = true;
            }
        }
        if(!Input.keys.get(8).down &&!Input.keys.get(9).down && !Input.keys.get(10).down){//放開
            locked = false;
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if(alpha <= 0.99f) {
            fadeIn(g2);
        }
        else {
            background.paint(g2);
            g2.translate(cam.getX(), cam.getY());
            paintAllGameObject(g2);
            g2.translate(-cam.getX(), -cam.getY());
        }
    }

    protected void fadeIn(Graphics2D g2) {
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
        Iterator<Tile> tileIterator = tiles.iterator();
        while(tileIterator.hasNext()) {
            t = tileIterator.next();
            if(inTheScreen(t)) {
                t.paint(g);
            }
        }
        Effect e;
        Iterator<Effect> effectIterator = effects.iterator();
        while(effectIterator.hasNext()) {
            e = effectIterator.next();
            e.paint(g);
        }
        ParticleSystem p;
        Iterator<ParticleSystem> particleSystemIterator = particles.iterator();
        while(particleSystemIterator.hasNext()) {
            p = particleSystemIterator.next();
            p.paint(g);
        }
    }

    public void updateAllGameObject() {
        if(alpha <= 0.97f) alpha += 0.99 / (FADEIN_TIME*Game.UPDATES);

        // Paint effect
        if(player.getCurrentEffect() != null && effects.size() == 0) {
            effects.add(player.getCurrentEffect());
            player.setCurrentEffect(null);
        }


        //Update player
        player.update();

        Tile t;
        player.setOnTheGround(false);
        player.setOnTheWall(false);
        Direction direction;
        Iterator<Tile> tileIterator = tiles.iterator();
        while(tileIterator.hasNext()) {
            t = tileIterator.next();
            if(inTheScreen(t)) {
                t.update();
                if (t instanceof FallingRock) {
                    if (Math.abs(player.getY() - t.getY()) < (700 * Game.heightRatio) && Math.abs(player.getX() - t.getX()) < 150 * Game.widthRatio && !((FallingRock) t).isFallen() && t.getY() <= player.getY()) {
                        ((FallingRock) t).setShaking(true);
                    }
                    if (((FallingRock) t).getCurrentEffect() instanceof LandingEffect) {
                        effects.add(LandingEffect.getInstance(t));
                        cam.setShaking(true, 20, 5);
                    }
                    ((FallingRock) t).setCurrentEffect(null);
                }
                // ********* Player collision detection **********
                if (t.getBounds() != null && player.inTheScreen(t)) {
                    player.handleCollision(t, player.checkCollisionVertical(t, Tile::getBounds));
                    direction = player.checkCollisionHorizontal(t, Tile::getBounds);
                    player.handleCollision(t, direction);
                }
                // ***********************************************
            }

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
            if(t.isDead()) {
                if(t instanceof Emerald) {
                    gameStateManager.incrementEmeraldCount();
                }
                tileIterator.remove();
            }
        }

        Effect e;
        Iterator<Effect> effectIterator = effects.iterator();
        while(effectIterator.hasNext()) {
            e = effectIterator.next();
            e.update();
            if(e.isDead()) {
                effectIterator.remove();
            }
        }
        ParticleSystem p;
        Iterator<ParticleSystem> particleSystemIterator = particles.iterator();
        while(particleSystemIterator.hasNext()) {
            p = particleSystemIterator.next();
            p.update();
            if(p.isDead()) {
                particleSystemIterator.remove();
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
                alpha = 0;
                gameStateManager.incrementDeathCount();
                gameStateManager.setLevelState(getInstance());
            }
        }
        // ************************************

        // Check if on the ice
        if(player.isOnTheIce() && player.getCurrentState() != PlayerState.standing) {
            player.setCurrentState(PlayerState.iceSkating);
        }

        //Check if on the ground
        if(!player.isInTheAir() && !player.isOnTheGround()) {
            player.setCurrentState(PlayerState.falling);
        }

        //Update camera
        cam.update(player, mapWidth, mapHeight);

    }

    @Override
    public String toString() {
        return "LevelState";
    }

    protected boolean inTheScreen(Tile t) {
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
                    tiles.add(new Hole(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.hole));
                }
                // Bigger block
                else if (red == 50 && green == 50 && blue == 19) {
                    tiles.add(new Wall(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio) * 5, (int)(64*Game.widthRatio) * 5, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                } else if (red == 100 && green == 50 && blue == 19) {
                    tiles.add(new Wall(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                } else if (red == 100 && green == 0 && blue <= 20) {
                    if (blue == 5 || blue == 4) {
                        tiles.add(new Wall(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.halfWidthWall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    }
                    else if (blue == 17 || blue == 18 || blue == 20) {
                        tiles.add(new Wall(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.halfHeightWall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    }
                    else {
                        tiles.add(new Wall(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    }

                } else if (red == 100 && green == 100 && blue <= 29) {
                    tiles.add(new Decor(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.decor, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                } else if (red == 255 && green == 255 && blue == 0) {
                    tiles.add(new VanishingRock(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.vanishingRock));
                } else if (red == 0 && green == 30 && blue == 255) {
                    tiles.add(new IceWall(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.icewall1, SpriteManager.level1Sprites.get(green - 1).getBufferedImage()));
                } else if (red == 0 && green == 255 && blue == 31) {
                    tiles.add(new Spike(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.upwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.UP));
                } else if (red == 0 && green == 255 && blue == 32) {
                    tiles.add(new Spike(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.downwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.DOWN));
                } else if (red == 0 && green == 255 && blue == 33) {
                    tiles.add(new Spike(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.leftwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.LEFT));
                } else if (red == 0 && green == 255 && blue == 34) {
                    tiles.add(new Spike(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.rightwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.RIGHT));
                } else if (red == 255 && green == 100 && blue == 35) {
                    FallingRock fr = new FallingRock(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)((int)(64*Game.widthRatio) * 2.5), (int)((int)(64*Game.widthRatio) * 2.5), Id.fallingRock, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage());
                    tiles.add(fr);
                    fallingRocks.add(fr);
                } else if (red == 100 && green == 0 && blue >= 36) {
                    if (blue == 42 || blue == 43) {
                        tiles.add(new Wall(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(22*Game.widthRatio), Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    } else {
                        tiles.add(new Wall(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    }

                } else if (red == 255 && green == 0 && blue == 0) {
                    Emerald emerald;
                    if(gameStateManager.getEmerald(getLevel(), emeraldSerial) == null) {
                        emerald = new Emerald(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), 1000, Id.emerald, emeraldSerial);
                        gameStateManager.addEmerald(getLevel(), emeraldSerial, emerald);
                    }
                    else {
                        emerald = gameStateManager.getEmerald(getLevel(), emeraldSerial);
                        emerald.setSize( (int)(64*Game.widthRatio), (int)(64*Game.widthRatio));
                        if(emerald.isEaten()) {
                            emerald = null;
                        }
                    }
                    emeraldSerial++;
                    if(emerald != null) {
                        tiles.add(emerald);
                    }
                }
                else if (red == 255 && green == 150 && blue == 255) {
                    tiles.add( new Diamond(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(80 * Game.widthRatio), (int)(80 * Game.widthRatio), 0, Id.diamond));
                }
                else if(red == 255 && green == 150 && blue == 150) {
                    tiles.add(new Spring(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.spring, Direction.UP));
                }
                else if(red == 255 && green == 160 && blue == 160) {
                    tiles.add(new Spring(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.spring, Direction.DOWN));
                }
                else if(red == 255 && green == 170 && blue == 170) {
                    tiles.add(new Spring(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.spring, Direction.LEFT));
                }
                else if(red == 255 && green == 180 && blue == 180) {
                    tiles.add(new Spring(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), Id.spring, Direction.RIGHT));
                }

                //Portal
//              //Blue
                else if (red == 0 && green == 0 && blue == 255 || red == 0 && green == 1 && blue == 255) {
                    Direction direction = (green == 1) ? Direction.LEFT : Direction.RIGHT;
                    tiles.add(new Portal(x * (int)(Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(200*Game.widthRatio), (int)(200*Game.widthRatio), Id.bluePortal, Color.BLUE, direction));
                    bluePortalCor = new Dimension(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio));
                }
                //Purple
                else if (red == 255 && green == 0 && blue == 255 || red == 255 && green == 1 && blue == 255) {
                    Direction direction = (green == 1) ? Direction.LEFT : Direction.RIGHT;
                    tiles.add(new Portal(x * (int)(64*Game.widthRatio), y * (int)(64*Game.widthRatio), (int)(200*Game.widthRatio), (int)(200*Game.widthRatio), Id.purplePortal, Color.MAGENTA, direction));
                }
            }
        }
        emeraldSerial = 0;
    }

    public abstract int getLevel();
}
