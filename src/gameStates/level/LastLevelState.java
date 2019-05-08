package gameStates.level;

import UI.Game;
import UI.Window;
import enums.Id;
import gameObject.character.Player;
import gameObject.tiles.Tile;
import gameObject.tiles.trap.Lava;
import gameStates.GameStateManager;
import gameStates.LevelState;
import graphics.SpriteManager;
import map.Background;
import states.PlayerState;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class LastLevelState extends LevelState {
    private LinkedList<Lava> lavaLinkedList;
    private static final int UP_SPEED = Game.UPDATES/3;
    private int lavaUP;
    private int lavaCurrentSpeed;
    private int speedDelay;
    private Rectangle rectangle;
    private static final Color LAVA_COLOR = new Color(173, 64, 34);
    private static final int MAX_SPEED = -18;

    public LastLevelState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        lavaLinkedList = new LinkedList<>();
        SpriteManager.levelInit();
        levelObjectInit();
        createLevel(SpriteManager.lastLevel);
        background = new Background("/res/lavaBackground.png", 1.0f);
        player = new Player(Player.WIDTH, Player.HEIGHT, Id.player);
        player.setPosition((int)bluePortalCor.getWidth(), (int)bluePortalCor.getHeight());
        lavaUP = 0;
        lavaCurrentSpeed = -8;
        speedDelay = 0;
        rectangle = new Rectangle(0, 0, Lava.LAVA_WIDTH*mapWidth, 0);
    }

    @Override
    public void update() {
        if(alpha <= 0.97f) alpha += 0.99 / (1.3f* Game.UPDATES);
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
        if(speedDelay++ >= Game.UPDATES*2 && lavaCurrentSpeed >= MAX_SPEED) {
            speedDelay = 0;
            lavaCurrentSpeed--;
        }
        for(Lava lava: lavaLinkedList) {
            player.handleCollision(lava, player.checkCollisionBounds(lava, Tile::getBounds));
            if(lavaUP++ >= UP_SPEED) {
                lavaUP = 0;
                lava.setVelY(lavaCurrentSpeed * Game.heightRatio);
            }
            else {
                lava.setVelY(0);
            }
            lava.update();
        }
        //Update lava rectangle
        Lava lava = lavaLinkedList.get(0);
        rectangle.y = lava.getY() + (int)(lava.getHeight() / 1.2);
        rectangle.height = Lava.LAVA_HEIGHT * mapHeight - (lava.getY() + lava.getHeight());

        cam.update(player, mapWidth, mapHeight);

        // Check if on the ice
        if(player.isOnTheIce() && player.getCurrentState() != PlayerState.standing) {
            player.setCurrentState(PlayerState.iceSkating);
        }

        //Check if on the ground
        if(!player.isInTheAir() && !player.isOnTheGround()) {
            player.setCurrentState(PlayerState.falling);
        }

        if((player.isGoaled())) {
            gameStateManager.setLevelState(new Level2State(gameStateManager));
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
//        if(alpha <= 0.99f) {
//            fadeIn(g2);
//        }
//        else {
        background.paint(g2);
        g2.translate(cam.getX(), cam.getY());
        paintAllGameObject(g2);
        for(Lava lava: lavaLinkedList) {
            lava.paint(g2);
        }
        g2.setColor(LAVA_COLOR);
        g2.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g2.translate(-cam.getX(), -cam.getY());
    }

    @Override
    public LevelState getInstance() {
        return new LastLevelState(gameStateManager);
    }

    @Override
    public int getLevel() {
        return 5;
    }

    @Override
    public void createLevel(BufferedImage level) {
        super.createLevel(level);
        for (int x = 0; x < mapWidth; x++) {
            lavaLinkedList.add(new Lava(x * Lava.LAVA_WIDTH, (mapHeight-8) * Lava.LAVA_HEIGHT, Lava.LAVA_WIDTH, Lava.LAVA_HEIGHT, Id.lava));
        }
    }
}