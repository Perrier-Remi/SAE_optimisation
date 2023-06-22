package solution;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Distance {
    public long distance(BufferedImage image1, BufferedImage image2) {
        long somme = 0;

        int largeur = image1.getWidth();
        int hauteur = image1.getHeight();

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                int rgb1 = image1.getRGB(i, j);
                int rgb2 = image2.getRGB(i, j);
                somme += distanceCouleur(new Color(rgb1), new Color(rgb2));
            }
        }
        return somme;
    }

    public static int[] getRGB(int rgb) {
        int[] tabRGB = new int[3];
        tabRGB[0] = (rgb & 0xFF0000) >> 16;
        tabRGB[1] = (rgb & 0xFF00) >> 8;
        tabRGB[2] = (rgb & 0xFF);
        return tabRGB;
    }

    public static int distanceCouleur(Color c1, Color c2) {
        int[] rgb1 = getRGB(c1.getRGB());
        int[] rgb2 = getRGB(c2.getRGB());
        int distance = 0;
        for (int i = 0; i < 3; i++) {
            distance += Math.pow(rgb1[i] - rgb2[i], 2);
        }
        return distance;
    }
}
