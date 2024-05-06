package ca.qc.bdeb.sim202;

/**
 * Une classe abstraite qui permet d'encrypter des messages et de décrypter des messages. Le type d'encryptage/décryptage
 * est déterminé selon le *Crypteur* utilisé
 * @author Anthony Lim et Hugo Galvao Valente
 * @version JDK 17.0.1
 */

public abstract class Crypteur {
    protected String message;
    protected String messageEncoder;
    protected String nomDAlgorithme;

    /**
     * Une méthode qui cryptera un message
     * @param messageClair Le message entré par l'utilisateur qui sera encrypté
     * @return Retourne le message crypté
     */
    public abstract String encoder(String messageClair);

    /**
     * Une méthode qui décryptera un message
     * @param messageCrypte Le message crypté entré par l'utilisateur qui sera décrypté
     * @return Retourne le message décrypté
     */
    public abstract String decoder(String messageCrypte);

    /**
     * Une méthode qui permet d'avoir les composantes du crypteur en question, afin d'avoir des méthodes toString esthétiques
     * @return Un String avec les composantes
     */
    public abstract String getEtiquette();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
