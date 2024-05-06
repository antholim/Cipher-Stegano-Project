package ca.qc.bdeb.sim202;

/**
 * Classe qui extends Crypteur et qui écrit les lettres d'un mot dans l'ordre inverse
 * @author Hugo Valente
 * @version JDK 16.0.2
 */
public class CrypteurInverse extends Crypteur {
    private String nomDAlgorithme = "Inversion";

    /**
     * Méthode encoder fait la même chose que la méthode décoder car les deuc méthodes inversent les lettres d'un message
     *
     * @param message est le message non-encodé dans les paramètres de la méthode
     * @return le message encoder
     */

    @Override
    public String encoder(String message) {
        return decoder(message);
    }

    /**
     * Méthode pour décoder un message
     *
     * @param message est le message encodé que l'on veut décoder
     * @return le message inversé
     */
    @Override
    public String decoder(String message) {
        char lettre;
        String motInverse = "";
        for (int i = message.length(); i > 0; i--) {
            lettre = message.charAt(i - 1);
            motInverse += lettre;
        }
        return motInverse;
    }

    /**
     * Méthode qui renvoie un String de l'objet bien étiquette en mentionnant l'algorithme utilisé
     *
     * @return Retourne le String
     */
    @Override
    public String toString() {
        String messageTexte = "Crypteur[Algorithme : ";
        return messageTexte + getEtiquette().toString() + "]";
    }

    /**
     * Une méthode qui permet d'avoir les composantes du crypteur en question, afin d'avoir des méthodes toString esthétiques
     *
     * @return Un String avec les composantes
     */
    @Override
    public String getEtiquette() {
        String etiquette = nomDAlgorithme;
        return etiquette;
    }
}

