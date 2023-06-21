import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Q3 {
    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("src/main/resources/images_etudiants/copie.png"));
            System.out.println("Le fichier a bien été lu");

            int largeur = image.getWidth();
            int hauteur = image.getHeight();

            BufferedImage imageCopie = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_3BYTE_BGR);

            for (int i = 0; i < hauteur; i++) {
                for (int j = 0; j < largeur; j++) {
                    int rgb = image.getRGB(i, j);
                    int sum = 0;
                    int[] tabRGB = getRGB(rgb);
                    for (int k = 0; k < 3; k++) {
                        sum += tabRGB[k];
                    }
                    int moyenne = sum / 3;
                    Color color = new Color(moyenne, moyenne, moyenne) ;
                    int couleur = color.getRGB();
                    imageCopie.setRGB(i, j, couleur);
                }
            }

            boolean success = ImageIO.write(imageCopie, "png", new File("src/main/resources/questions/question3/gris.png"));
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
}
