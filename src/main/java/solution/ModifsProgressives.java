package solution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ModifsProgressives {
    private BufferedImage image;
    private HashMap<Color, Integer> histogramme;

    public ModifsProgressives(BufferedImage image) {
        this.image = image;
        this.histogramme = new HashMap<Color, Integer>();
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
        // Créer une liste d'entrées du HashMap
        List<Map.Entry<Color, Integer>> entries = new ArrayList<>(histogramme.entrySet());

        // Trier les entrées en utilisant un comparateur basé sur la valeur (nombre de pixels)
        entries.sort(Map.Entry.comparingByValue());

        // Récupérer les couleurs triées
        ArrayList<Color> colors = new ArrayList<>();
        for (int i = 0; i < histogramme.size() && i < entries.size(); i++) {
            colors.add(entries.get(i).getKey());
        }

        // print ten first elements
        for (int i = 0; i < 100; i++) {
            System.out.println(colors.get(i) + " " + histogramme.get(colors.get(i)));
        }

        Distance distance = new Distance();
        while (colors.size() > nbCoul) {
            Color aSupprimer = colors.get(0);
            //find the closest color in histogramme
            int min = Integer.MAX_VALUE;
            Color closest = null;
            for (Color c : histogramme.keySet()) {
                int dist = distance.distanceCouleur(c, aSupprimer);
                if (dist < min && dist > 0) {
                    min = dist;
                    closest = c;
                }
            }

            histogramme.replace(closest, histogramme.get(closest) + histogramme.get(aSupprimer));
            histogramme.remove(aSupprimer);
            colors.remove(aSupprimer);
            System.out.println(colors.size());
        }

        return colors.toArray(new Color[0]);
    }


    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(new File("src/main/resources/images_etudiants/copie.png"));
        ModifsProgressives modifsProgressives = new ModifsProgressives(image);
        HashMap res = modifsProgressives.calculerHistogramme();
        Color[] color = modifsProgressives.getColors(res, 500);
        System.out.println(color);


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

        boolean success = ImageIO.write(imageCopie, "png", new File("src/main/resources/questions/solution_etudiant/progressifx.png"));
        if (success) {
            System.out.println("Le fichier a bien été écrit");
        } else {
            System.out.println("Pas de format d'écriture approprié trouvé");
        }
    }
}
