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

    public static Sprite coin;
    public static Sprite vanishingRock;

    //Menu
    public static BufferedImage menu;

    //level1
    public static BufferedImage level1A;
    public static BufferedImage level1B;
    public static BufferedImage level1C;

    // Option
    public static BufferedImage option;

    //cursor
    public static BufferedImage cursor;

    public SpriteManager() {
        //Sprite object
        spriteSheet = new SpriteSheet("/res/spriteSheet.png");
        coin = new Sprite(spriteSheet, 1, 9, 32, 32);
        vanishingRock = new Sprite(spriteSheet, 1,6,32,32);

        //BufferedImage object
        level1A = ResourceManager.getInstance().getImage("/res/level1A.png");
        level1B = ResourceManager.getInstance().getImage("/res/level1B.png");
        level1C = ResourceManager.getInstance().getImage("/res/level1C.png");

        cursor = ResourceManager.getInstance().getImage("/res/CursorA.png");
        menu = ResourceManager.getInstance().getImage("/res/MenuTemp.png");
        option = ResourceManager.getInstance().getImage("/res/MenuTemp.png");
    }

    public static void level1Init() {
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
