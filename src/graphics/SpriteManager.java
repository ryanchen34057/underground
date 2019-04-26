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

    //level1
    public static BufferedImage level1;
    public static BufferedImage menu;
    public static BufferedImage option;
    
    //cursor
    public static BufferedImage cursor;

    public SpriteManager() {
        //Sprite object
        spriteSheet = new SpriteSheet("/res/spriteSheet.png");
        coin = new Sprite(spriteSheet, 1, 9, 32, 32);

        //BufferedImage object
        cursor = ResourceManager.getInstance().getImage("/res/CursorA.png");
        level1 = ResourceManager.getInstance().getImage("/res/level1.png");
        menu = ResourceManager.getInstance().getImage("/res/MenuTemp.png");
        option = ResourceManager.getInstance().getImage("/res/MenuTemp.png");
        cursor = ResourceManager.getInstance().getImage("/res/pause3.png");        
    }

    public static void level1Init() {
        level1Sprites = new ArrayList<>();
        for(int i=1;i<=30;i++) {
            level1Sprites.add(new Sprite(spriteSheet, i, 1, 32, 32));
        }
        for(int i=1;i<=4;i++) {
            level1Sprites.add( new Sprite(spriteSheet, i, 5, 32, 32));
        }
        for(int i=1;i<=13;i++) {
            level1Sprites.add(new Sprite(spriteSheet, i, 2, 32, 32));
        }
    }
}
