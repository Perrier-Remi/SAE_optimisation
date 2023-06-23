package solution.aleatoire;

import solution.Distance;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CalculImage {
    public static BufferedImage calculerImage(BufferedImage image, Color[] couleurs){
        int largeur = image.getWidth();
        int hauteur = image.getHeight();
        BufferedImage imageCopie = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                int rgb = image.getRGB(i, j);

                int indexDistanceMin = 0;
                int[] distances = new int[couleurs.length];
                for (int k = 0; k < couleurs.length; k++) {
                    distances[k] = Distance.distanceCouleur(couleurs[k], new Color(rgb));
                    if (distances[k] < distances[indexDistanceMin]) {
                        indexDistanceMin = k;
                    }
                }

                imageCopie.setRGB(i, j, couleurs[indexDistanceMin].getRGB());
            }
        }
        return imageCopie;
    }
}
