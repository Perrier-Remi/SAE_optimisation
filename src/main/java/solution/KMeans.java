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
        // initialisation des centroïdes avec des couleurs aléatoires de l'image
        HashMap<Color, ArrayList<Color>> centroides = new HashMap<>();
        for (int i = 0; i < nbCoul; i++) {
            Color c = new Color(image.getRGB((int) (Math.random() * image.getWidth()), (int) (Math.random() * image.getHeight())));
            centroides.put(c, new ArrayList<>());
        }

        int largeur = image.getWidth();
        int hauteur = image.getHeight();


        Color[] anciensBarycentres = new Color[nbCoul];
        int distanceBarycentres = Integer.MAX_VALUE;

        while (distanceBarycentres > 1) {
            // affectation des couleurs de l'image à un centroïde
            anciensBarycentres = centroides.keySet().toArray(new Color[0]);
            for (int i = 0; i < largeur; i++) {
                for (int j = 0; j < hauteur; j++) {
                    Color couleurImage = new Color(image.getRGB(i, j));
                    Color couleurBarycentrePlusProche = null;
                    int distanceMin = Integer.MAX_VALUE;
                    for (Color c : centroides.keySet()) {
                        int distance = Distance.distanceCouleur(c, couleurImage);
                        if (distance < distanceMin) {
                            distanceMin = distance;
                            couleurBarycentrePlusProche = c;
                        }
                    }
                    centroides.get(couleurBarycentrePlusProche).add(couleurImage);
                }
            }

            // calcul des nouveaux centroïdes sur les barycentres des nouveaux groupes
            for (int i = 0; i < nbCoul; i++) {
                int r = 0;
                int g = 0;
                int b = 0;
                for (Color c : centroides.get(anciensBarycentres[i])) {
                    r += c.getRed();
                    g += c.getGreen();
                    b += c.getBlue();
                }
                // si un groupe est vide, cela veut dire que sa couleur est trop éloignée de l'image, on lui attribue donc une nouvelle couleur aléatoire
                if (centroides.get(anciensBarycentres[i]).size() != 0) {
                    Color nouveauBarycentre = new Color(r / centroides.get(anciensBarycentres[i]).size(), g / centroides.get(anciensBarycentres[i]).size(), b / centroides.get(anciensBarycentres[i]).size());
                    ArrayList<Color> groupe = centroides.get(anciensBarycentres[i]);
                    centroides.remove(anciensBarycentres[i]);
                    centroides.put(nouveauBarycentre, groupe);
                } else {
                    Color nouveauBarycentre = new Color(image.getRGB((int) (Math.random() * image.getWidth()), (int) (Math.random() * image.getHeight())));
                    ArrayList<Color> groupe = new ArrayList<>();
                    centroides.remove(anciensBarycentres[i]);
                    centroides.put(nouveauBarycentre, groupe);
                }
            }
            distanceBarycentres = distanceBarycentres(anciensBarycentres, centroides.keySet().toArray(new Color[0]));
        }
        return anciensBarycentres;
    }

    // méthode pour calculer la distance entre les anciens barycentres et les nouveaux barycentres
    public int distanceBarycentres(Color[] barycentres, Color[] nouveauxBarycentres) {
        int distance = 0;
        for (int i = 0; i < barycentres.length; i++) {
            // si un barycentre est null, cela veut dire qu'il n'a pas de groupe associé, on lui attribue donc une distance très grande
            if (barycentres[i] != null && nouveauxBarycentres[i] != null) {
                distance += Distance.distanceCouleur(barycentres[i], nouveauxBarycentres[i]);
            } else {
                distance += 10000000;
            }
        }
        return distance;
    }

    public static void main(String[] args) throws IOException {

        // paramètres
        String dossier = "film";
        String file = "indianajones_small";
        String extension = ".png";
        int nbCoul = 100;


        BufferedImage imageOriginale = ImageIO.read(new File("src/main/resources/" + dossier + "/" + file + extension));
        KMeans kmeans = new KMeans(imageOriginale);

        long start = System.currentTimeMillis();

        // récupération des couleurs dans l'image avec l'algorithme des k-means
        Color[] color = kmeans.getColors(nbCoul);

        int largeur = imageOriginale.getWidth();
        int hauteur = imageOriginale.getHeight();

        BufferedImage imageCopie = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_3BYTE_BGR);

        Distance distance = new Distance();

        // on remplace chaque pixel de l'image par la couleur du barycentre le plus proche
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                int rgb = imageOriginale.getRGB(j, i);

                int indexDistanceMin = 0;
                int[] distances = new int[color.length];
                for (int k = 0; k < color.length; k++) {
                    distances[k] = distance.distanceCouleur(color[k], new Color(rgb));
                    if (distances[k] < distances[indexDistanceMin]) {
                        indexDistanceMin = k;
                    }
                }

                imageCopie.setRGB(j ,i , color[indexDistanceMin].getRGB());
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("Temps d'exécution : " + (end - start) + " ms");
        Distance distanceImages = new Distance();
        System.out.println("Distance entre les deux images : " + distanceImages.distance(imageOriginale, imageCopie));


        boolean success = ImageIO.write(imageCopie, "png", new File("src/main/resources/questions/solution_etudiant/kmeans_" + nbCoul + "_" + file + ".jpg"));
        if (success) {
            System.out.println("Le fichier a bien été écrit");
        } else {
            System.out.println("Pas de format d'écriture approprié trouvé");
        }
    }
}
