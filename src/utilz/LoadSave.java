package utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadSave {

    private static final Logger logger = Logger.getLogger(LoadSave.class.getName());

    public static BufferedImage loadImage(String resourceName){
        BufferedImage img = null;
        try{
            InputStream inputStream = LoadSave.class.getResourceAsStream("/" + resourceName);

            img = ImageIO.read(inputStream);
            inputStream.close();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while loading image \"" + resourceName + "\"");
        }

        return img;
    }
}
