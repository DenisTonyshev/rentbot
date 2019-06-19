package telegrammrentalbot.rentbot.botAbilities;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class PictureResize {
    //Метод возвращает порезанное буферизированное изображение, которое потом можно куда-нибудь писать!
    @Value("${picture.size}")
    private int heigt;
    public BufferedImage resise(File pictureFile, String newFileNameWithLocation) throws NoSuchFieldException {
        BufferedImage image = null;
        try {
            image = ImageIO.read(pictureFile);

        } catch (IOException e) {
            e.printStackTrace();
            throw new NoSuchFieldException("can't read file");
        }
        return convertSise(image);
    }

    private BufferedImage convertSise(BufferedImage img) {
        int width = checkWidth(img.getHeight(), img.getWidth());
        Image tmp = img.getScaledInstance(width, heigt, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, heigt, BufferedImage.OPAQUE);//TYPE_INT_ARGB
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private int checkWidth(int height, int width) {
        double ratio = (double) height/(double) width;
        return (int)(heigt /ratio);
    }
}
