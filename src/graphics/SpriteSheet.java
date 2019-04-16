package graphics;

import util.ResourceManager;
import java.awt.image.BufferedImage;


public class SpriteSheet {
    private BufferedImage sheet;

    public SpriteSheet(String path) {
        sheet = ResourceManager.getInstance().getImage(path);
    }

    public BufferedImage getSprite(int x, int y) {
        return sheet.getSubimage(x*32-32, y*32-32, 32, 32);
    }
}
