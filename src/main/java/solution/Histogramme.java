package solution;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Histogramme {
    private BufferedImage image;
    private Map<Color, Integer> histogramme;

    public Histogramme(BufferedImage image) {
        this.image = image;
        this.histogramme = new java.util.HashMap<>();
    }

    public Map calculerHistogramme() {
        int largeur = image.getWidth();
        int hauteur = image.getHeight();

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                Color couleur = new Color(image.getRGB(i, j));
                if (histogramme.containsKey(couleur)) {
                    histogramme.put(couleur, histogramme.get(couleur) + 1);
                } else {
                    histogramme.put(couleur, 1);
                }
            }
        }
        return histogramme;
    }

    public Color[] getColors(Map<Color, Integer> histogramme, int nbCoul) {
        Color couleurs [] = new Color[nbCoul];
        couleurs[0] = Color.BLACK;
        couleurs[1] = Color.BLACK;
        couleurs[2] = Color.BLACK;
        couleurs[3] = Color.BLACK;
        couleurs[4] = Color.BLACK;
        for (Color c : histogramme.keySet()) {
            for (int i = 0; i < nbCoul; i++) {
                if (histogramme.get(c) > histogramme.get(couleurs[i]) && !c.equals(couleurs[i])) {
                    couleurs[i] = c;

                }
            }
        }
        return couleurs;
    }
}
