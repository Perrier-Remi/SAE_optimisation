package solution.aleatoire;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AleaCouleur {
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
}
