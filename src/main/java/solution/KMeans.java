package solution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class KMeans {
    private BufferedImage image;

    public KMeans(BufferedImage image) {
        this.image = image;
    }

    public Color[] getColors(int nbCoul) {
        HashMap<Color, ArrayList<Color>> centroides = new HashMap<>();
        for (int i = 0; i < nbCoul; i++) {
            Color c = new Color(image.getRGB((int) (Math.random() * image.getWidth()), (int) (Math.random() * image.getHeight())));
            centroides.put(c, new ArrayList<>());
        }

        int largeur = image.getWidth();
        int hauteur = image.getHeight();

        Color[] anciensBarycentres = new Color[nbCoul];
        int distanceBarycentre = Integer.MAX_VALUE;

        while (distanceBarycentre > 0) {
            anciensBarycentres = centroides.keySet().toArray(new Color[0]);
            for (int i = 0; i < hauteur; i++) {
                for (int j = 0; j < largeur; j++) {
                    Color couleur = new Color(image.getRGB(i, j));
                    Color plusProche = null;
                    int distanceMin = Integer.MAX_VALUE;
                    for (Color c : centroides.keySet()) {
                        int distance = Distance.distanceCouleur(c, couleur);
                        if (distance < distanceMin) {
                            distanceMin = distance;
                            plusProche = c;
                        }
                    }
                    centroides.get(plusProche).add(couleur);
                }
            }


            for (int i = 0; i < nbCoul; i++) {
                int r = 0;
                int g = 0;
                int b = 0;
                for (Color c : centroides.get(anciensBarycentres[i])) {
                    r += c.getRed();
                    g += c.getGreen();
                    b += c.getBlue();
                }
                if (centroides.get(anciensBarycentres[i]).size() != 0) {
                    Color nouveauBarycentre = new Color(r / centroides.get(anciensBarycentres[i]).size(), g / centroides.get(anciensBarycentres[i]).size(), b / centroides.get(anciensBarycentres[i]).size());
                    ArrayList<Color> groupe = centroides.get(anciensBarycentres[i]);
                    centroides.remove(anciensBarycentres[i]);
                    centroides.put(nouveauBarycentre, groupe);
                }
            }
            distanceBarycentre = distanceBarycentres(anciensBarycentres, centroides.keySet().toArray(new Color[0]));
        }
        return anciensBarycentres;
    }

    public int distanceBarycentres(Color[] barycentres, Color[] nouveauxBarycentres) {
        int distance = 0;
        for (int i = 0; i < barycentres.length; i++) {
            if (barycentres[i] != null && nouveauxBarycentres[i] != null) {
                distance += Distance.distanceCouleur(barycentres[i], nouveauxBarycentres[i]);
            } else {
                distance += 10000000;
            }
        }
        return distance;
    }

    public static void main(String[] args) throws IOException {
        String file = "copie.png";
        long start = System.currentTimeMillis();
        BufferedImage image = ImageIO.read(new File("src/main/resources/images_etudiants/" + file));
        KMeans kmeans = new KMeans(image);
        int nbCoul = 15;
        Color[] color = kmeans.getColors(nbCoul);

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
        long end = System.currentTimeMillis();
        System.out.println("Temps d'exécution : " + (end - start) + " ms");
        Distance distanceImages = new Distance();
        System.out.println("Distance entre les deux images : " + distanceImages.distance(image, imageCopie));


        boolean success = ImageIO.write(imageCopie, "png", new File("src/main/resources/questions/solution_etudiant/kmeans_" + nbCoul + "_" + file));
        if (success) {
            System.out.println("Le fichier a bien été écrit");
        } else {
            System.out.println("Pas de format d'écriture approprié trouvé");
        }
    }
}
