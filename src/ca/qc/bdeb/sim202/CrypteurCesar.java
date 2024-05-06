package ca.qc.bdeb.sim202;

/**
 * Une classe qui hérite de Crypteur qui encrypte/décrypte un message en utilisant le Chiffre de César
 *
 * @author Anthony Lim
 * @version JDK 17.0.1
 */

public class CrypteurCesar extends Crypteur {
    protected int decalage;
    protected String nomDAlgorithme = "César";

    /**
     * Constructeur
     *
     * @param decalage Le décalage des lettres
     */
    public CrypteurCesar(int decalage) {
        this.decalage = decalage;
    }

    /**
     * Une méthode qui encryptera un message en utilisant le Chiffre de César
     *
     * @param message Le message entré par l'utilisateur qui sera encrypté
     * @return Retourne le message crypté
     */
    @Override
    public String encoder(String message) {
        char messageEnChar[] = message.toCharArray();
        for (int i = 0; i < messageEnChar.length; i++) {
            if (Character.isUpperCase(messageEnChar[i])) {
                int J = messageEnChar[i];
                J = J + decalage;
                while (J > 90) {
                    J = J - 26;
                }
                messageEnChar[i] = (char) J;
            } else if (Character.isLowerCase(messageEnChar[i])) {
                int J = messageEnChar[i];
                J = J + decalage;
                while (J > 122) {
                    J = J - 26;
                }
                messageEnChar[i] = (char) J;
            }
        }
        messageEncoder = String.valueOf(messageEnChar);
        return messageEncoder;
    }

    /**
     * Une méthode qui décryptera un message encodé par le Chiffre de César
     *
     * @param messageEncoder Le message entré par l'utilisateur qui sera décrypté
     * @return Retourne le message décrypté
     */
    @Override
    public String decoder(String messageEncoder) {
        char messageEncoderEnChar[] = messageEncoder.toCharArray();
        for (int i = 0; i < messageEncoderEnChar.length; i++) {
            if (Character.isUpperCase(messageEncoderEnChar[i])) {
                int J = messageEncoderEnChar[i];
                J = J - decalage;
                while (J < 65) {
                    J = J + 26;
                }
                messageEncoderEnChar[i] = (char) J;
            } else if (Character.isLowerCase(messageEncoderEnChar[i])) {
                int j = messageEncoderEnChar[i];
                j = j - decalage;
                while (j < 97) {
                    j = j + 26;
                }
                messageEncoderEnChar[i] = (char) j;
            }
        }
        String messageDecoder = String.valueOf(messageEncoderEnChar);
        return messageDecoder;
    }

    /**
     * Méthode qui renvoie un String de l'objet bien étiquette en mentionnant l'algorithme utilisé ainsi que ses
     * composantes s'il y a lieu
     *
     * @return Retourne le String
     */
    @Override
    public String toString() {
        return "Crypteur[Algorithme : " + getEtiquette().toString() + "]";
    }

    /**
     * Une méthode qui permet d'avoir les composantes du crypteur en question, afin d'avoir des méthodes toString esthétiques
     *
     * @return Un String avec les composantes
     */
    @Override
    public String getEtiquette() {
        String etiquette = nomDAlgorithme + " | " + "Décalage: " + decalage;
        return etiquette;
    }
}
