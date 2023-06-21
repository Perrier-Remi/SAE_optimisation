import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Q4 {
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

                    // pour ne garder que le rouge :
//                    int r = rgb & 0xFF0000;
//                    imageCopie.setRGB(i, j, r);

                    // pour ne garder que le vert et le bleu
                    int gb = rgb & 0x00FFFF;
                    imageCopie.setRGB(i, j, gb);
                }
            }

            boolean success = ImageIO.write(imageCopie, "png", new File("src/main/resources/questions/question4/vert_bleu.png"));
            if (success) {
                System.out.println("Le fichier a bien été écrit");
            } else {
                System.out.println("Pas de format d'écriture approprié trouvé");
            }

        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier");
        }
    }
}
