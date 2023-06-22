import solution.Distance;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Alea {
    public static void main(String[] args){
        try {
            // On lit l'image
            BufferedImage image = ImageIO.read(new File("src/main/resources/images_etudiants/copie.png"));
            System.out.println("Le fichier a bien été lu");

            /*int largeur = image.getWidth();
            int hauteur = image.getHeight();

            // On crée une image copie de même taille
            BufferedImage imageCopie = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_3BYTE_BGR);*/

            // On fixe un nombre de couleurs par défaut
            int nbCouleurs = 5;

            // Si l'utilisateur a passé un argument, on le prend en compte en tant que nombre de couleurs
            if (args.length > 0) {
                nbCouleurs = Integer.parseInt(args[0]);
            }

            // On récupère les couleurs aléatoires à partir de l'image de base
            Color[] couleurs = getAleaCouleurs(image, nbCouleurs);

            /*for (int i = 0; i < hauteur; i++) {
                for (int j = 0; j < largeur; j++) {
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
            }*/

            // On effectue 10 itérations de calcul d'image et on conserve celle qui a la distance la plus faible avec l'image de base
            Distance d = new Distance();
            BufferedImage imageCopie = calculerImage(image, couleurs);
            for(int i = 0; i < 10; i++){
                BufferedImage imageTentative = calculerImage(imageCopie, couleurs);
                if(d.distance(imageCopie, imageTentative) < d.distance(imageCopie, imageCopie)){
                    imageCopie = imageTentative;
                }
            }


            boolean success = ImageIO.write(imageCopie, "png", new File("src/main/resources/images_etudiants/solution_alea.png"));
            if (success) {
                System.out.println("Le fichier a bien été écrit");
            } else {
                System.out.println("Pas de format d'écriture approprié trouvé");
            }

        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier");
        }
    }

    public static Color[] getAleaCouleurs(BufferedImage image, int nbCouleurs){
        int largeur = image.getWidth();
        int hauteur = image.getHeight();
        Color[] couleurs = new Color[nbCouleurs];

        for(int i=0; i<nbCouleurs; i++){
            // On prend une couleur au hasard parmi les couleurs de l'image
            int x = (int) (Math.random() * largeur);
            int y = (int) (Math.random() * hauteur);
            couleurs[i] = new Color(image.getRGB(x, y));
        }
        return couleurs;
    }

    public static BufferedImage calculerImage(BufferedImage image, Color[] couleurs){
        int largeur = image.getWidth();
        int hauteur = image.getHeight();
        BufferedImage imageCopie = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
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

