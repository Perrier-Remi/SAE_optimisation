import solution.Histogramme;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(new File("src/main/resources/images_etudiants/copie.png"));
        System.out.println("Le fichier a bien été lu");

        Histogramme histogramme = new Histogramme(image);
        Map res = histogramme.calculerHistogramme();
        Color[] color = histogramme.getColors(res, 5);
        for (int i = 0; i < 5; i++) {
            System.out.println(color[i]);
        }

    }
}
