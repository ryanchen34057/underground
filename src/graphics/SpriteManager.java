package graphics;

import util.ResourceManager;

import java.awt.image.BufferedImage;

public class SpriteManager {
    //images
    //Sprite sheet
    public static SpriteSheet spriteSheet;


    //UI.Game object
    public static Sprite wall1;
    public static Sprite wall2Breakable;
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
        wall1 = new Sprite(spriteSheet, 2, 1, 32, 32);
        wall2Breakable = new Sprite(spriteSheet, 3, 1, 32, 32);
        upwardSpike = new Sprite(spriteSheet, 1, 2, 32, 32);
        downwardSpike = new Sprite(spriteSheet, 2, 2,32, 32);
        leftwardSpike = new Sprite(spriteSheet, 3, 2, 32, 32);
        rightwardSpike = new Sprite(spriteSheet, 4, 2, 32, 32);
        coin = new Sprite(spriteSheet, 1, 9, 32, 32);

        //BufferedImage object
        currentLevel = ResourceManager.getInstance().getImage("/res/movementTest.png");
    }
}
