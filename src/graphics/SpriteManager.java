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
    public static Sprite bluePortal;

    //level1
    public static BufferedImage level1;

    public SpriteManager() {
        //Sprite object
        spriteSheet = new SpriteSheet("/res/spriteSheet.png");
        coin = new Sprite(spriteSheet, 1, 9, 32, 32);

        //BufferedImage object
        level1 = ResourceManager.getInstance().getImage("/res/level1.png");
    }

    public static void level1Init() {
        level1Sprites = new ArrayList<>();
        for(int i=1;i<=26;i++) {
            level1Sprites.add(new Sprite(spriteSheet, i, 1, 32, 32));
        }
        for(int i=1;i<=4;i++) {
            level1Sprites.add( new Sprite(spriteSheet, i, 5, 32, 32));
        }
    }
}
