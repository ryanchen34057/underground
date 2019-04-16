package graphics;

import util.ResourceManager;
import java.awt.image.BufferedImage;


public class SpriteSheet {
    private BufferedImage sheet;

    public SpriteSheet(String path) {
        sheet = ResourceManager.getInstance().getImage(path);
    }

    public BufferedImage getSprite(int x, int y, int width, int height) {
        return sheet.getSubimage(x*width-width, y*height-height, width, height);
    }
}
