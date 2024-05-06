package ca.qc.bdeb.sim202;

/**
 * Une classe qui hérite de CrypteurCésar qui encrypte/décrypte un message en utilisant le Chiffre de César, mais avec
 * un décalage de 13
 *
 * @author Hugo Valente
 * @version JDK 16.0.2
 */
public class CrypteurRot13 extends CrypteurCesar {
    private CrypteurCesar crypteurCesar;
    private String nomDAlgorithme = "ROT13";

    /**
     * Constructeur
     * <p>
     * Le décalage est toujours 13
     */
    public CrypteurRot13() {
        super(13);
    }

    /**
     * Une méthode qui décryptera un message encodé par le Chiffre de César
     *
     * @param message : Le message entré par l'utilisateur qui sera décrypté
     * @return Retourne le message décrypté
     */
    @Override
    public String decoder(String message) {
        crypteurCesar = new CrypteurCesar(decalage);
        return crypteurCesar.decoder(message);
    }

    /**
     * Une méthode qui encryptera un message en utilisant le Chiffre de César
     *
     * @param message Le message entré par l'utilisateur qui sera encrypté
     * @return Retourne le message crypté
     */
    @Override
    public String encoder(String message) {
        crypteurCesar = new CrypteurCesar(decalage);
        return crypteurCesar.encoder(message);
    }

    /**
     * Méthode qui renvoie un String de l'objet bien étiquette en mentionnant l'algorithme utilisé
     *
     * @return Retourne le String
     */
    @Override
    public String toString() {
        return "Crypteur[Algorithme : " + getEtiquette().toString() + "]";
    }

    @Override
    /**
     * Une méthode qui permet d'avoir les composantes du crypteur en question, afin d'avoir des méthodes toString esthétiques
     * @return Un String avec les composantes
     */
    public String getEtiquette() {
        String etiquette = nomDAlgorithme;
        return etiquette;
    }
}
