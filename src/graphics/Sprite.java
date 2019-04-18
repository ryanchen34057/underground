package graphics;

import java.awt.image.BufferedImage;

public class Sprite {
    private BufferedImage image;

    public Sprite(SpriteSheet sheet, int x, int y, int width, int height) {
        image = sheet.getSprite(x, y, width, height);
    }

    public BufferedImage getBufferedImage() {
        return image;
    }
}
