package gameStates.level;

import UI.Game;
import audio.MusicPlayer;
import enums.Id;
import gameObject.character.Player;
import gameObject.tiles.Tile;
import gameObject.tiles.trap.Lava;
import gameStates.EndState;
import gameStates.GameStateManager;
import gameStates.LevelState;
import graphics.SpriteManager;
import map.Background;
import record.Record;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LastLevelState extends LevelState {
    private ArrayList<Lava> lavaLinkedList;
    private int speedDelay;
    private Rectangle rectangle;
    private static final int DELAY = Game.UPDATES-100;
    private static final Color LAVA_COLOR = new Color(173, 64, 34);
    private float currentSpeed;
    private static final int MAX_SPEED = -30;

    public LastLevelState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        lavaLinkedList = new ArrayList<>();
        SpriteManager.levelInit();
        levelObjectInit();
        createLevel(SpriteManager.lastLevel);
        background = new Background("/res/lavaBackground2.png", 1.0f);
        player = new Player((int)(96*Game.widthRatio), (int)(96*Game.widthRatio), Id.player);
        player.setPosition((int)bluePortalCor.getWidth(), (int)bluePortalCor.getHeight());
        speedDelay = 0;
        rectangle = new Rectangle(0, 0, Lava.LAVA_WIDTH*mapWidth, 0);
        currentSpeed = -10;
    }

    @Override
    public void update() {
        if(alpha <= 0.97f) alpha += 0.99 / (1.3f* Game.UPDATES);
        // handle player's keyInput
        handleKeyInput();

        // Set position of the background
        background.setPos(cam.getX(), cam.getY());

        // Update all game object
        updateAllGameObject();

        for(Lava lava: lavaLinkedList) {
            player.handleCollision(lava, player.checkCollisionVertical(lava, Tile::getBounds));
            if(speedDelay++ >= DELAY) {
                speedDelay = 0;
                lava.setVelY(currentSpeed * Game.heightRatio);
                if(currentSpeed >= MAX_SPEED) {
                    currentSpeed -= 0.03f;
                }

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

        if((player.isGoaled())) {
            MusicPlayer.isOn = false;
            MusicPlayer.changeSong(2);
            MusicPlayer.isOn = true;
            gameStateManager.setGameState(new EndState(gameStateManager));
            //過關存檔
            gameStateManager.saveEndGameRecord(new Record(gameStateManager.getSlotId(), gameStateManager.getPlayerName(), gameStateManager.getTimer().getUsedTimeRecord(),
                    gameStateManager.getTimer().toString(), gameStateManager.getEmeraldCount(), gameStateManager.getCurrentLevel(), gameStateManager.getLevelReached(), gameStateManager.getDeathCount()));
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        fadeIn(g2);
        g2.translate(cam.getX(), cam.getY());
        paintAllGameObject(g2);
        for(Lava lava: lavaLinkedList) {
            if(inTheScreen(lava)) {
                lava.paint(g2);
            }
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
