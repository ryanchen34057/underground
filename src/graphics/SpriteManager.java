 package graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteManager {
    //images
    //Sprite sheet
    public static SpriteSheet spriteSheet;
    public static SpriteSheet lavaSheet;

    //UI.Game object
    // Level1
    public static ArrayList<Sprite> level1Sprites;

    public static Sprite emerald;
    public static Sprite skull;
    public static Sprite vanishingRock;
    public static Sprite signboard;
    public static Sprite c;
    public static Sprite x;
    public static Sprite esc;
    public static Sprite left;
    public static Sprite right;
    public static Sprite up;
    public static Sprite down;
    

    //Menu
    private static BufferedImage menu;

    //level
    public static BufferedImage level0;
    public static BufferedImage level1;
    public static BufferedImage level2;
    public static BufferedImage level3;
    public static BufferedImage level4;
    public static BufferedImage lastLevel;
    public static BufferedImage tutorial;

    // Option
    public static BufferedImage option;

    //selectionObject
    public static Sprite rightCursor;
    public static Sprite leftCursor;
    
    //pause
    public static BufferedImage pause;

    //Enter key
    public static Sprite enterKey;

    //X key
    public static Sprite xKey;

    //Arror
    public static Sprite arrow;

    //Empty diamond
    public static Sprite emptyDiamond;

    //Empty vanishing rock
    public static Sprite emptyVanishingRock;

    //Lock
    public static Sprite lock;


    public SpriteManager() {
        //Sprite object
        spriteSheet = new SpriteSheet("/res/spriteSheet.png");
        lavaSheet = new SpriteSheet("/res/lava.png");
        emerald = new Sprite(spriteSheet, 1, 9, 64, 64);
        skull = new Sprite(spriteSheet, 9, 9, 64, 64);
        vanishingRock = new Sprite(spriteSheet, 1,6,64,64);
        enterKey = new Sprite(spriteSheet, 10,9, 64, 64);
        xKey = new Sprite(spriteSheet, 11,9, 64, 64);
        rightCursor = new Sprite(spriteSheet, 12, 9, 64, 64);
        leftCursor = new Sprite(spriteSheet, 13, 9, 64, 64);
        arrow = new Sprite(spriteSheet, 10, 10, 64, 64);
        emptyDiamond = new Sprite(spriteSheet, 9, 7, 64, 64 );
        emptyVanishingRock = new Sprite(spriteSheet, 2, 6, 64, 64);
        signboard = new Sprite(spriteSheet,9,8,64,64);
        c = new Sprite(spriteSheet,14,9,64,64);
        x = new Sprite(spriteSheet,11,9,64,64);
        esc = new Sprite(spriteSheet,15,9,64,64);
        left = new Sprite(spriteSheet,16,9,64,64);
        right = new Sprite(spriteSheet,17,9,64,64);
        up = new Sprite(spriteSheet,18,9,64,64);
        down = new Sprite(spriteSheet,19,9,64,64);
        lock = new Sprite(spriteSheet, 11, 10, 64, 64);


        //BufferedImage object
        level0 = ResourceManager.getInstance().getImage("/res/level0.png");
        level1 = ResourceManager.getInstance().getImage("/res/level1.png");
        level2 = ResourceManager.getInstance().getImage("/res/level2.png");
        level3 = ResourceManager.getInstance().getImage("/res/level3.png");
        level4 = ResourceManager.getInstance().getImage("/res/level4.png");
        lastLevel = ResourceManager.getInstance().getImage("/res/lastLevel.png");
        tutorial = ResourceManager.getInstance().getImage("/res/tutorial.png");
        menu = ResourceManager.getInstance().getImage("/res/Cave1.png");
        option = ResourceManager.getInstance().getImage("/res/Cave1.png");
        pause = ResourceManager.getInstance().getImage("/res/Cave1.png");  
       
        
    }

    public static void levelInit() {
        level1Sprites = new ArrayList<>();
        for(int i=1;i<=30;i++) {
            level1Sprites.add(new Sprite(spriteSheet, i, 1, 64, 64));
        }
        for(int i=1;i<=4;i++) {
            level1Sprites.add( new Sprite(spriteSheet, i, 5, 64, 64));
        }
        for(int i=1;i<=14;i++) {
            level1Sprites.add(new Sprite(spriteSheet, i, 2, 64, 64));
        }
    }
}
