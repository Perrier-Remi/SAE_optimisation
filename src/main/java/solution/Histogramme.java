package solution;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Histogramme {
    private BufferedImage image;
    private HashMap<Color, Integer> histogramme;

    public Histogramme(BufferedImage image) {
        this.image = image;
        this.histogramme = new HashMap<>();
    }

    public HashMap calculerHistogramme() {
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

    public Color[] getColors(HashMap<Color, Integer> histogramme, int nbCoul) {
        Color[] colors = new Color[nbCoul];
        int[] nbPixels = new int[nbCoul];
        for (int i = 0; i < nbCoul; i++) {
            nbPixels[i] = 0;
        }
        for (Map.Entry<Color, Integer> entry : histogramme.entrySet()) {
            for (int i = 0; i < nbCoul; i++) {
                if (entry.getValue() > nbPixels[i]) {
                    for (int j = nbCoul - 1; j > i; j--) {
                        nbPixels[j] = nbPixels[j - 1];
                        colors[j] = colors[j - 1];
                    }
                    nbPixels[i] = entry.getValue();
                    colors[i] = entry.getKey();
                    break;
                }
            }
        }
        return colors;
    }
}
