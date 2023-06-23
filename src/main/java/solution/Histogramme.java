package solution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
