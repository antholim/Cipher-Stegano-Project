package ca.qc.bdeb.sim202;

import java.util.ArrayList;

/**
 * Une classe qui hérite de Crypteur qui encrypte/décrypte un message en utilisant les algorithmes d'encryptages
 * d'un ArrayList
 * @author Anthony Lim
 * @version JDK 17.0.1
 */
public class CrypteurMultiple extends Crypteur {
    private ArrayList<Crypteur> listeDeCrypteurs;

    /**
     * Constructeur
     *
     * @param listeDeCrypteurs ArrayList des crypteurs qui seront utilisés
     */
    public CrypteurMultiple(ArrayList<Crypteur> listeDeCrypteurs) {
        this.listeDeCrypteurs = listeDeCrypteurs;
    }

    /**
     * Une méthode qui cryptera un message
     * @param messageClair Le message entré par l'utilisateur qui sera encrypté
     * @return Retourne le message crypté
     */
    @Override
    public String encoder(String messageClair) {
        for (int i = 0; i < listeDeCrypteurs.size(); i++) {
            messageClair = listeDeCrypteurs.get(i).encoder(messageClair);
        }
        messageEncoder = messageClair;
        return messageEncoder;
    }

    /**
     * Une méthode qui décryptera un message encodé par le Chiffre de Vigenère
     * @param messageEncoder Le message entré par l'utilisateur qui sera décrypté
     * @return Retourne le message décrypté
     */
    @Override
    public String decoder(String messageEncoder) {
        for (int i = listeDeCrypteurs.size() - 1; i >= 0; i--) {
            messageEncoder = listeDeCrypteurs.get(i).decoder(messageEncoder);
        }
        message = messageEncoder;
        return message;
    }

    /**
     * Méthode qui renvoie un String de l'objet bien étiquette en mentionnant le ou les algorithme(s) utilisé(s) ainsi que leurs
     * composantes s'il y a lieu
     * @return Retourne le String
     */
    @Override
    public String toString() {
        String messageTexte = "Crypteur[Algorithme: ";
        for (int i = 0; i < listeDeCrypteurs.size(); i++) {
            messageTexte = messageTexte + listeDeCrypteurs.get(i).getEtiquette();
            messageTexte = messageTexte + ", ";
        }
        return messageTexte + "]";
    }
    /**
     * Une méthode qui permet d'avoir les composantes du/des crypteur(s) en question, afin d'avoir des méthodes toString esthétiques
     * @return Un String avec les composantes
     */
    public String getEtiquette() {
        String etiquette = nomDAlgorithme;
        return etiquette;
    }
}
