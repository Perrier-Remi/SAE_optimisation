import solution.Distance;
import solution.Histogramme;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(new File("src/main/resources/images_etudiants/copie.png"));
        System.out.println("Le fichier a bien été lu");

        Histogramme histogramme = new Histogramme(image);
        HashMap res = histogramme.calculerHistogramme();
        Color[] color = histogramme.getColors(res, 50);

        int largeur = image.getWidth();
        int hauteur = image.getHeight();

        BufferedImage imageCopie = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_3BYTE_BGR);

        Distance distance = new Distance();

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                int rgb = image.getRGB(i, j);

                int indexDistanceMin = 0;
                int[] distances = new int[color.length];
                for (int k = 0; k < color.length; k++) {
                    distances[k] = distance.distanceCouleur(color[k], new Color(rgb));
                    if (distances[k] < distances[indexDistanceMin]) {
                        indexDistanceMin = k;
                    }
                }

                imageCopie.setRGB(i, j, color[indexDistanceMin].getRGB());
            }
        }

        boolean success = ImageIO.write(imageCopie, "png", new File("src/main/resources/questions/solution_etudiant/histogramme.png"));
        if (success) {
            System.out.println("Le fichier a bien été écrit");
        } else {
            System.out.println("Pas de format d'écriture approprié trouvé");
        }
    }
}
