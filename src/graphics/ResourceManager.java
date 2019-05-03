package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class ResourceManager {
//    private ArrayList<BufferedImage> bufferedImages;
//    private ArrayList<String> paths;
    private HashMap<String, BufferedImage> bufferedImageMap;
    private static ResourceManager resourceManager;


    private ResourceManager() {
        bufferedImageMap = new HashMap<>();
        resourceManager = null;
    }

    public static ResourceManager getInstance() {
        if(resourceManager == null) {
            resourceManager = new ResourceManager();
        }
        return resourceManager;
    }

    public BufferedImage getImage(String path) {
        if(!bufferedImageMap.containsKey(path)) {
            addImage(path);
        }
        return bufferedImageMap.get(path);
    }

    public BufferedImage addImage(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedImageMap.put(path, img);
        return img;
    }
}
