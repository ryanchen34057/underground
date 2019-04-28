package gameStates.level1;


import effects.DeathParticle;
import enums.Direction;
import enums.Id;
import gameObject.character.Player;
import gameObject.tiles.Decor;
import gameObject.tiles.movable.FallingRock;
import gameObject.tiles.portal.Portal;
import gameObject.tiles.prize.Emerald;
import gameObject.tiles.trap.Hole;
import gameObject.tiles.trap.Spike;
import gameObject.tiles.wall.IceWall;
import gameObject.tiles.wall.VanishingRock;
import gameObject.tiles.wall.Wall;
import gameStates.GameStateManager;
import gameStates.LevelState;
import graphics.SpriteManager;
import map.Background;
import states.PlayerState;
import util.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Level1CState extends LevelState {

    public Level1CState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public LevelState getInstance() {
        return new Level1CState(gameStateManager);
    }

    @Override
    public void init() {
        tiles = new LinkedList<>();
        effects = new LinkedList<>();
        particles = new LinkedList<>();
        fallingRocks = new LinkedList<>();
        //*******************
        background = new Background("/res/background2.jpg", 1.0f);
        SpriteManager.level1Init();
        createLevel(SpriteManager.level1C);
        //********************
        player = new Player(Player.WIDTH, Player.HEIGHT, Id.player);
        player.setPosition((int)bluePortalCor.getWidth(), (int)bluePortalCor.getHeight());
        cam = new Camera();
    }

    @Override
    public void update() {
        // handle player's keyInput
        handleKeyInput();

        // Set position of the background
        background.setPos(cam.getX(), cam.getY());


        // Paint effect
        if(player.getCurrentEffect() != null && effects.size() == 0) {
            effects.add(player.getCurrentEffect());
            player.setCurrentEffect(null);
        }

        // Update all game object
        updateAllGameObject();
        cam.update(player);


        // Check if on the ice
        if(player.isOnTheIce() && player.getCurrentState() != PlayerState.standing) {
            player.setCurrentState(PlayerState.iceSkating);
        }

        //Check if on the ground
        if(!player.isInTheAir() && !player.isOnTheGround()) {
            player.setCurrentState(PlayerState.falling);
        }

        if((player.isGoaled())) {
            gameStateManager.setLevelState(new Level1CState(gameStateManager));
        }

        // ********* Player death ************
        if(player.isDead()) {
            for(int j=0;j<8;j++) {
                particles.add(DeathParticle.getInstance(player,j));
            }
            deathDelay++;
            if(deathDelay >= DEATH_DELAY_TIME) {
                deathDelay = 0;
                gameStateManager.setLevelState(getInstance());
            }
        }
        // ************************************
    }

    @Override
    public void createLevel(BufferedImage level) {
        int width = level.getWidth();
        int height = level.getHeight();

        for(int y=0;y<height;y++) {
            for (int x = 0; x < width; x++) {
                int pixel = level.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (red == 99 && green == 99 && blue == 99) {
                    tiles.add(new Hole(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.hole));
                }
                // Bigger block
                else if (red == 50 && green == 50 && blue == 19) {
                    tiles.add(new Wall(x * 64, y * 64, Wall.TILE_SIZE * 5, Wall.TILE_SIZE * 5, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                } else if (red == 100 && green == 50 && blue == 19) {
                    tiles.add(new Wall(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                } else if (red == 100 && green == 0 && blue <= 20) {
                    if (blue == 5 || blue == 4) {
                        tiles.add(new Wall(x * 64, y * 64, Wall.TILE_SIZE / 2, Wall.TILE_SIZE, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    }
                    if (blue == 17) {
                        tiles.add(new Wall(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE / 2, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    } else {
                        tiles.add(new Wall(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    }

                } else if (red == 100 && green == 100 && blue <= 29) {
                    tiles.add(new Decor(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.decor, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                } else if (red == 255 && green == 255 && blue == 0) {
                    tiles.add(new VanishingRock(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.vanishingRock));
                } else if (red == 0 && green == 30 && blue == 255) {
                    tiles.add(new IceWall(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.icewall1, SpriteManager.level1Sprites.get(green - 1).getBufferedImage()));
                } else if (red == 0 && green == 255 && blue == 31) {
                    tiles.add(new Spike(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.upwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.UP));
                } else if (red == 0 && green == 255 && blue == 32) {
                    tiles.add(new Spike(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.downwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.DOWN));
                } else if (red == 0 && green == 255 && blue == 33) {
                    tiles.add(new Spike(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.leftwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.LEFT));
                } else if (red == 0 && green == 255 && blue == 34) {
                    tiles.add(new Spike(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.rightwardSpike, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage(), Direction.RIGHT));
                } else if (red == 255 && green == 100 && blue == 35) {
                    FallingRock fr = new FallingRock(x * 64, y * 64, Wall.TILE_SIZE * 3, Wall.TILE_SIZE * 3, Id.fallingRock, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage());
                    tiles.add(fr);
                    fallingRocks.add(fr);
                } else if (red == 100 && green == 0 && blue >= 36) {
                    if (blue == 42 || blue == 43) {
                        tiles.add(new Wall(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE / 2 - 10, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    } else {
                        tiles.add(new Wall(x * 64, y * 64, Wall.TILE_SIZE, Wall.TILE_SIZE, Id.wall, SpriteManager.level1Sprites.get(blue - 1).getBufferedImage()));
                    }

                } else if (red == 255 && green == 0 && blue == 0) {
                    tiles.add(new Emerald(x * 64, y * 64, Emerald.PRIZE_SIZE, Emerald.PRIZE_SIZE, 1000, Id.coin));
                }


                //Portal
//              //Blue
                else if (red == 0 && green == 0 && blue == 255 || red == 0 && green == 1 && blue == 255) {
                    Direction direction = (green == 1) ? Direction.LEFT : Direction.RIGHT;
                    tiles.add(new Portal(x * 64, y * 64, Portal.PORTAL_SIZE, Portal.PORTAL_SIZE, Id.bluePortal, Color.BLUE, direction));
                    bluePortalCor = new Dimension(x * 64, y * 64);
                }
                //Purple
                else if (red == 255 && green == 0 && blue == 255 || red == 255 && green == 1 && blue == 255) {
                    Direction direction = (green == 1) ? Direction.LEFT : Direction.RIGHT;
                    tiles.add(new Portal(x * 64, y * 64, Portal.PORTAL_SIZE, Portal.PORTAL_SIZE, Id.purplePortal, Color.MAGENTA, direction));
                }
            }
        }
    }
}
