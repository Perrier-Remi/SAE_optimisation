import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Main_Q1 {
    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("src/main/resources/images_etudiants/copie.png"));
            System.out.println("Le fichier a bien été lu");

            boolean success = ImageIO.write(image, "png", new File("src/main/resources/questions/question1/copie.png"));
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
