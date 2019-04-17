package graphics;

import util.ResourceManager;

import java.awt.image.BufferedImage;

public class SpriteManager {
    //images
    //Sprite sheet
    public static SpriteSheet spriteSheet;
    public static SpriteSheet deathSpriteSheet;
    public static SpriteSheet dashSpriteSheet;

    //UI.Game object
    public static Sprite wall;
    public static Sprite upwardSpike;
    public static Sprite downwardSpike;
    public static Sprite leftwardSpike;
    public static Sprite rightwardSpike;
    public static Sprite coin;

    //currentLevel
    public static BufferedImage currentLevel;

    public SpriteManager() {
        //Sprite object
        spriteSheet = new SpriteSheet("/res/spriteSheet.png");
        dashSpriteSheet = new SpriteSheet("/res/Dash.png");
        wall = new Sprite(spriteSheet, 1, 1, 32, 32);
        upwardSpike = new Sprite(spriteSheet, 1, 2, 32, 32);
        downwardSpike = new Sprite(spriteSheet, 2, 2,32, 32);
        leftwardSpike = new Sprite(spriteSheet, 3, 2, 32, 32);
        rightwardSpike = new Sprite(spriteSheet, 4, 2, 32, 32);
        coin = new Sprite(spriteSheet, 1, 9, 32, 32);

        //BufferedImage object
        currentLevel = ResourceManager.getInstance().getImage("/res/movementTest.png");
    }
}
