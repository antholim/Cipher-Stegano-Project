package ca.qc.bdeb.sim202;

/**
 * Une classe abstraite qui permet de cacher des messages encryptés dans d'autres objets, ex (un fichier .txt, un fichier
 * binaire, etc)
 * @author Anthony Lim et Hugo Galvao Valente
 * @version JDK 17.0.1
 */

public abstract class Steganographeur {
    protected Crypteur crypteur;
    protected String messageSecret;

    /**
     * Une méthode qui cachera un message dans un fichier
     * @param nomFichier Le nom du fichier
     * @param message Le message à cacher et encrypté
     */

    public abstract void encoderFichier(String nomFichier,String message);
    /**
     * Une méthode qui trouvé le message caché dans un fichier
     * @param nomFichier Le nom du fichier
     */
    public abstract String decoderFichier(String nomFichier);

}
