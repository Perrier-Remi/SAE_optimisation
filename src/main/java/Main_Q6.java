import solution.Distance;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Q6 {
    public static void main(String[] args) {
        try {
            String imageOriginale = "src/main/resources/images_etudiants/originale.jpg";

            String[] listeImage = new String[14];
            listeImage[0] = "src/main/resources/images_etudiants/coul_2.png";
            listeImage[1] = "src/main/resources/images_etudiants/coul_3.png";
            listeImage[2] = "src/main/resources/images_etudiants/coul_5.png";
            listeImage[3] = "src/main/resources/images_etudiants/coul_10.png";
            listeImage[4] = "src/main/resources/images_etudiants/coul_20.png";
            listeImage[5] = "src/main/resources/images_etudiants/copie.png";
            listeImage[6] = "src/main/resources/images_etudiants/copie_pixels.png";
            listeImage[7] = "src/main/resources/images_etudiants/copie_nb.png";
            listeImage[8] = "src/main/resources/images_etudiants/copie_rouge.png";
            listeImage[9] = "src/main/resources/images_etudiants/copie_vert_bleu.png";
            listeImage[10] = "src/main/resources/images_etudiants/copie_proche_YG.png";
            listeImage[11] = "src/main/resources/images_etudiants/copie_proche_YGW.png";
            listeImage[12] = "src/main/resources/images_etudiants/copie_proche_YGWO.png";
            listeImage[13] = "src/main/resources/images_etudiants/copie_proche_YGWOP.png";

            BufferedImage image = ImageIO.read(new File(imageOriginale));



            Distance distance = new Distance();
            for (int i = 0; i < listeImage.length; i++) {
                BufferedImage imageCopie = ImageIO.read(new File(listeImage[i]));
                long res = distance.distance(image, imageCopie);
                //formated ouput like this : "distance(originale.jpg, coul_2.png) = 1 270 154 677" with a space every 3 digits

                System.out.println("distance(" + imageOriginale.split("/")[4] + ", " + listeImage[i].split("/")[4] + ") = " + res);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
