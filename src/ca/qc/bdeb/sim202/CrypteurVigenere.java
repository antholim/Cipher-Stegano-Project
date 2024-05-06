package ca.qc.bdeb.sim202;

/**
 * Une classe qui hérite de Crypteur qui encrypte/décrypte un message en utilisant le Chiffre de Vigenère
 * @author Anthony Lim
 * @version JDK 17.0.1
 */
public class CrypteurVigenere extends Crypteur {
    private String motDePasse;
    private String nomDAlgorithme = "Vigenère";

    /**
     * Constructeur
     *
     * @param motDePasse Le mot de passe qui permet d'encrypter/décrypter
     */
    public CrypteurVigenere(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /**
     * Une méthode qui encryptera un message en utilisant le Chiffre de Vigenère
     * @param message Le message entré par l'utilisateur qui sera encrypté
     * @return Retourne le message crypté
     */
    public String encoder(String message) {
        char messageEnTab[] = message.toCharArray();
        char motDePasseEnTab[] = motDePasse.toCharArray();
        int distanceMotDePasse[] = new int[motDePasseEnTab.length];

        for (int i = 0; i < distanceMotDePasse.length; i++) {
            motDePasseEnTab[i] = Character.toUpperCase(motDePasseEnTab[i]);
            distanceMotDePasse[i] = (int) motDePasseEnTab[i] - 65;
        }

        int compteur = 0;

        for (int i = 0; i < messageEnTab.length; i++) {
            if (Character.isUpperCase(messageEnTab[i])) {
                int valeurAsciiDuChar = messageEnTab[i];
                valeurAsciiDuChar = valeurAsciiDuChar + distanceMotDePasse[compteur % distanceMotDePasse.length];
                while (valeurAsciiDuChar > 90) {
                    valeurAsciiDuChar = valeurAsciiDuChar - 26;
                }
                messageEnTab[i] = (char) valeurAsciiDuChar;
            } else if (Character.isLowerCase(messageEnTab[i])) {
                int j = messageEnTab[i];
                j = j + distanceMotDePasse[compteur % distanceMotDePasse.length];
                while (j > 122) {
                    j = j - 26;
                }
                messageEnTab[i] = (char) j;
            } else if (!Character.isLetter(messageEnTab[i])) {
                compteur--;
            }
            compteur++;
        }
        String messageEncoder = String.valueOf(messageEnTab);
        return messageEncoder;
    }

    /**
     * Une méthode qui décryptera un message encodé par le Chiffre de Vigenère
     * @param messageEncoder Le message entré par l'utilisateur qui sera décrypté
     * @return Retourne le message décrypté
     */
    public String decoder(String messageEncoder) {
        char messageEncoderEnTab[] = messageEncoder.toCharArray();
        char motDePasseEnTab[] = motDePasse.toCharArray();
        int distanceMotDePasse[] = new int[motDePasseEnTab.length];

        for (int i = 0; i < distanceMotDePasse.length; i++) {
            motDePasseEnTab[i] = Character.toUpperCase(motDePasseEnTab[i]);
            distanceMotDePasse[i] = (int) motDePasseEnTab[i] - 65;
        }

        int compteur = 0;

        for (int i = 0; i < messageEncoderEnTab.length; i++) {
            if (Character.isUpperCase(messageEncoderEnTab[i])) {
                int j = messageEncoderEnTab[i];
                j = j - distanceMotDePasse[compteur % distanceMotDePasse.length];
                while (j < 65) {
                    j = j + 26;
                }
                messageEncoderEnTab[i] = (char) j;
            } else if (Character.isLowerCase(messageEncoderEnTab[i])) {
                int j = messageEncoderEnTab[i];
                j = j - distanceMotDePasse[compteur % distanceMotDePasse.length];
                while (j < 97) {
                    j = j + 26;
                }
                messageEncoderEnTab[i] = (char) j;
            } else if (!Character.isLetter(messageEncoderEnTab[i])) {
                compteur--;
            }
            compteur++;
        }
        String message = String.valueOf(messageEncoderEnTab);
        return message;
    }

    /**
     * Méthode qui renvoie un String de l'objet bien étiquette en mentionnant l'algorithme utilisé ainsi que ses
     * composantes s'il y a lieu
     * @return Retourne le String
     */
    @Override
    public String toString() {
        return "Crypteur[Algorithme : " + getEtiquette().toString() + "]";
    }

    /**
     * Une méthode qui permet d'avoir les composantes du crypteur en question, afin d'avoir des méthodes toString esthétiques
     * @return Un String avec les composantes
     */
    @Override
    public String getEtiquette() {
        String etiquette = nomDAlgorithme + " | Clé : " + motDePasse;
        return etiquette;
    }

}
