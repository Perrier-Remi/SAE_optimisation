package solution.aleatoire;

import solution.Distance;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static solution.aleatoire.AleaCouleur.getAleaCouleurs;
import static solution.aleatoire.CalculImage.calculerImage;

public class Main_Alea {
    public static void main(String[] args){
        try {
            // On fixe un fichier par défaut
            String fichier = "src/main/resources/images_etudiants/copie.png";

            // S'il y a un premier argument, on le prend en compte en tant que fichier
            if (args.length > 0) {
                fichier = args[0];
            }

            // On lit l'image
            BufferedImage image = ImageIO.read(new File(fichier));
            System.out.println("Le fichier a bien été lu");

            // On fixe un nombre de couleurs par défaut
            int nbCouleurs = 5;

            // Si l'utilisateur a passé un argument, on le prend en compte en tant que nombre de couleurs
            if (args.length > 1) {
                nbCouleurs = Integer.parseInt(args[1]);
            }

            // On lance un chronomètre
            long start = System.currentTimeMillis();

            // On récupère les couleurs aléatoires à partir de l'image de base
            Color[] couleurs = getAleaCouleurs(image, nbCouleurs);

            // On effectue 10 itérations de calcul d'image avec des nouvelles couleurs aléatoires
            Distance d = new Distance();
            BufferedImage imageCopie = calculerImage(image, couleurs);
            for(int i = 0; i < 10; i++){
                // On récupère des nouvelles couleurs aléatoires
                couleurs = getAleaCouleurs(image, nbCouleurs);
                // On calcule une nouvelle image avec ces couleurs
                BufferedImage imageTentative = calculerImage(imageCopie, couleurs);
                // Si la nouvelle image est plus proche de l'image de base que l'ancienne, on la garde
                if(d.distance(imageTentative, image) < d.distance(imageCopie, image)){
                    imageCopie = imageTentative;
                }
            }

            // On arrête le chronomètre
            long end = System.currentTimeMillis();

            // Fichier de sortie par défaut
            File outputfile = new File("src/main/resources/images_etudiants/solution_alea.png");
            // Si l'utilisateur a passé un troisième argument, on le prend en compte en tant que fichier de sortie
            if(args.length > 2){
                outputfile = new File(args[2]);
            }
            // On écrit l'image résultat dans un fichier
            boolean success = ImageIO.write(imageCopie, "png", outputfile);


            if (success) {
                System.out.println("Le fichier a bien été écrit");

                // On affiche le temps d'exécution
                System.out.println("Image calculée en " + (end - start) + "ms");

                // On affiche la distance entre l'image de base et l'image calculée
                System.out.println("Distance entre l'image de base et l'image calculée : " + d.distance(imageCopie, image));

            } else {
                System.out.println("Pas de format d'écriture approprié trouvé");
            }

        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier");
        }
    }
}

