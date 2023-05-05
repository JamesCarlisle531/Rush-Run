package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BufferedImages {

    public static BufferedImage backgroundImg;

    public static BufferedImage hiddenImg;
    public static BufferedImage foundImg;
    public static BufferedImage flagImg;
    public static BufferedImage mineImg;

    public static void loadImages() {
        backgroundImg = loadImage("./res/images/background.png");

        hiddenImg = loadImage("./res/images/Hidden.png");
        foundImg = loadImage("./res/images/Found.png");
        flagImg = loadImage("./res/images/Flag.png");
        mineImg = loadImage("./res/images/Mine.png");
    }

    private static BufferedImage loadImage(String path){
        File image = new File(path);
        try {
            BufferedImage bufferedImage = ImageIO.read(image);
            return bufferedImage;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
