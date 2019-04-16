package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class ResourceManager {
    private ArrayList<BufferedImage> bufferedImages;
    private ArrayList<String> paths;
    private static ResourceManager resourceManager;

    private ResourceManager() {
        bufferedImages = new ArrayList<>();
        paths = new ArrayList<>();
        resourceManager = null;
    }

    public static ResourceManager getInstance() {
        if(resourceManager == null) {
            resourceManager = new ResourceManager();
        }
        return resourceManager;
    }

    public BufferedImage getImage(String path) {
        int index = findContains(path);
        if(index == -1) {
            return addImage(path);
        }
        return bufferedImages.get(index);
    }

    public BufferedImage addImage(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedImages.add(img);
        return img;
    }

    public int findContains(String path) {
        for(int i=0;i<paths.size();i++) {
            if(paths.get(i).equals(path)) {
                return i;
            }
        }
        return -1;
    }
}
