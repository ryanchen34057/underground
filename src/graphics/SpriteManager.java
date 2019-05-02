package graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteManager {
    //images
    //Sprite sheet
    public static SpriteSheet spriteSheet;

    //UI.Game object
    // Level1
    public static ArrayList<Sprite> level1Sprites;

    public static Sprite emerald;
    public static Sprite skull;
    public static Sprite vanishingRock;

    //Menu
    public static BufferedImage menu;

    //level
    public static BufferedImage level1;
    public static BufferedImage level2;
    public static BufferedImage level3;
    public static BufferedImage level4;

    // Option
    public static BufferedImage option;

    //selectionObject
    public static BufferedImage cursor;
    
    //pause
    public static BufferedImage pause; 

    public SpriteManager() {
        //Sprite object
        spriteSheet = new SpriteSheet("/res/spriteSheet.png");
        emerald = new Sprite(spriteSheet, 1, 9, 32, 32);
        skull = new Sprite(spriteSheet, 9, 9, 32, 32);
        vanishingRock = new Sprite(spriteSheet, 1,6,32,32);

        //BufferedImage object
        level1 = ResourceManager.getInstance().getImage("/res/level1.png");
        level2 = ResourceManager.getInstance().getImage("/res/level2.png");
        level3 = ResourceManager.getInstance().getImage("/res/level3.png");
        level4 = ResourceManager.getInstance().getImage("/res/level4.png");
        cursor = ResourceManager.getInstance().getImage("/res/CursorA.png");
        menu = ResourceManager.getInstance().getImage("/res/Cave1.png");
        option = ResourceManager.getInstance().getImage("/res/Cave1.png");
        pause = ResourceManager.getInstance().getImage("/res/Cave1.png");    
    }

    public static void levelInit() {
        level1Sprites = new ArrayList<>();
        for(int i=1;i<=30;i++) {
            level1Sprites.add(new Sprite(spriteSheet, i, 1, 32, 32));
        }
        for(int i=1;i<=4;i++) {
            level1Sprites.add( new Sprite(spriteSheet, i, 5, 32, 32));
        }
        for(int i=1;i<=14;i++) {
            level1Sprites.add(new Sprite(spriteSheet, i, 2, 32, 32));
        }
    }
}
