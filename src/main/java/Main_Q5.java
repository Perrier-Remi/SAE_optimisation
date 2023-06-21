import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Q5 {
    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("src/main/resources/images_etudiants/copie.png"));
            System.out.println("Le fichier a bien été lu");

            int largeur = image.getWidth();
            int hauteur = image.getHeight();

            BufferedImage imageCopie = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_3BYTE_BGR);


            int nbCouleurs = 5;
            Color[] couleurs = new Color[nbCouleurs];
            couleurs[0] = Color.GREEN;
            couleurs[1] = Color.YELLOW;
            couleurs[2] = Color.WHITE;
            couleurs[3] = Color.ORANGE;
            couleurs[4] = Color.PINK;


            int[] distances = new int[nbCouleurs];

            for (int i = 0; i < hauteur; i++) {
                for (int j = 0; j < largeur; j++) {
                    int rgb = image.getRGB(i, j);

                    int indexDistanceMin = 0;
                    for (int k = 0; k < couleurs.length; k++) {
                        distances[k] = distance(couleurs[k], new Color(rgb));
                        if (distances[k] < distances[indexDistanceMin]) {
                            indexDistanceMin = k;
                        }
                    }

                    imageCopie.setRGB(i, j, couleurs[indexDistanceMin].getRGB());
                }
            }

            boolean success = ImageIO.write(imageCopie, "png", new File("src/main/resources/questions/question5/vert_jaune_blanc_orange_rose.png"));
            if (success) {
                System.out.println("Le fichier a bien été écrit");
            } else {
                System.out.println("Pas de format d'écriture approprié trouvé");
            }

        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier");
        }
    }

    public static int[] getRGB(int rgb) {
        int[] tabRGB = new int[3];
        tabRGB[0] = (rgb & 0xFF0000) >> 16;
        tabRGB[1] = (rgb & 0xFF00) >> 8;
        tabRGB[2] = (rgb & 0xFF);
        return tabRGB;
    }

    public static int distance(Color c1, Color c2) {
        int[] rgb1 = getRGB(c1.getRGB());
        int[] rgb2 = getRGB(c2.getRGB());
        int distance = 0;
        for (int i = 0; i < 3; i++) {
            distance += Math.pow(rgb1[i] - rgb2[i], 2);
        }
        return distance;
    }
}
