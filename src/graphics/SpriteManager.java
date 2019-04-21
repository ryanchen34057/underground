package graphics;

import java.awt.image.BufferedImage;

public class SpriteManager {
    //images
    //Sprite sheet
    public static SpriteSheet spriteSheet;

    //UI.Game object
    public static Sprite cavernWall1;
    public static Sprite cavernWall2;
    public static Sprite cavernWall3;
    public static Sprite cavernWall4;
    public static Sprite cavernWall5;
    public static Sprite cavernWall6;
    public static Sprite cavernWall7;
    public static Sprite cavernWall8;
    public static Sprite iceWall1;
    public static Sprite iceWall2;
    public static Sprite wall2Breakable;
    public static Sprite upwardSpike;
    public static Sprite downwardSpike;
    public static Sprite leftwardSpike;
    public static Sprite rightwardSpike;
    public static Sprite coin;

    //level1
    public static BufferedImage level1;

    public SpriteManager() {
        //Sprite object
        spriteSheet = new SpriteSheet("/res/spriteSheet.png");
        cavernWall1 = new Sprite(spriteSheet, 1, 1, 32, 32);
        cavernWall2 = new Sprite(spriteSheet, 2, 1, 32, 32);
        cavernWall3 = new Sprite(spriteSheet, 3, 1, 32, 32);
        cavernWall4 = new Sprite(spriteSheet, 4, 1, 32, 32);
        cavernWall5 = new Sprite(spriteSheet, 5, 1, 32, 32);
        cavernWall6 = new Sprite(spriteSheet, 6, 1, 32, 32);
        cavernWall7 = new Sprite(spriteSheet, 7, 1, 32, 32);
        cavernWall8 = new Sprite(spriteSheet, 7, 1, 32, 32);
        iceWall1 = new Sprite(spriteSheet, 1, 3, 32, 32);
        iceWall2 = new Sprite(spriteSheet, 2, 3, 32, 32);
        wall2Breakable = new Sprite(spriteSheet, 3, 1, 32, 32);
        upwardSpike = new Sprite(spriteSheet, 1, 2, 32, 32);
        downwardSpike = new Sprite(spriteSheet, 2, 2,32, 32);
        leftwardSpike = new Sprite(spriteSheet, 3, 2, 32, 32);
        rightwardSpike = new Sprite(spriteSheet, 4, 2, 32, 32);
        coin = new Sprite(spriteSheet, 1, 9, 32, 32);

        //BufferedImage object
        level1 = ResourceManager.getInstance().getImage("/res/level1.png");
    }
}
