package ca.qc.bdeb.sim202;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Classe qui hérite de Crypteur et qui encore ou décode un mot à l'aide de séquences aléatoires ayant une forme prédéfinie
 * @author Hugo Valente
 * @version JDK 16.0.2
 */

public class CrypteurSequenceAleatoire extends Crypteur {
    private ArrayList<Character> tableauDecrypte;
    private ArrayList<Character> tableauCrypte;
    private int tabSequence[];
    private String nomDAlgorithme = "Séquence aléatoires";
    /**
     * Constructeur pour séquences aléatoires
     *
     * @param tab est le tableau de sequences qui sera utilisé pour encoder ou décoder le message
     */
    public CrypteurSequenceAleatoire(int tab[]) {
        this.tabSequence = tab;
    }

    /**
     * Méthode qui décode un message encrypté en créant un Array List qui stockera les lettres du message qui fut initialisé en paramètres
     *
     * @param message est le message encodé
     * @return message, soit le message décrypté
     */
    @Override
    public String decoder(String message) {
        String motDecrypte = "";
        int position = 0;
        tableauDecrypte = new ArrayList<>();
        tableauDecrypte.add(0, message.charAt(0));
        try {
            do {
                for (int i = 0; i < tabSequence.length; i++) {
                    position += tabSequence[i] + 1;
                    tableauDecrypte.add(message.charAt(position));
                }
            } while (position < message.length());
        } catch (StringIndexOutOfBoundsException e) {
            System.out.print("StringIndexOutOfBoundsException");
        }
        for (char lettre : tableauDecrypte)
            motDecrypte += "" + lettre;
        return motDecrypte;
    }

    /**
     * Méthode qui encode le message en ajoutant N caractères et une lettre du message décrypté au message crypté.
     *
     * @param message est le message décodé
     * @return messageEncoder qui est le message encodé
     */
    @Override
    public String encoder(String message) {
        String motCrypte = "";
        Random random = new Random();
        tableauCrypte = new ArrayList<>();
        int compteur = 0;
        try {
            while (compteur < message.length()) {
                for (int i = 0; i < tabSequence.length; i++) {
                    tableauCrypte.add(message.charAt(compteur));
                    for (int y = 0; y < tabSequence[i]; y++) {
                        if (compteur < message.length()) {
                            int numeroAleatoire = random.nextInt(122);
                            char symbole = (char) numeroAleatoire;
                            tableauCrypte.add(symbole);
                        }
                    }
                    compteur++;
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.print("StringIndexOutOfBoundsException");
        }
        for (char a : tableauCrypte)
            motCrypte += "" + a;
        return motCrypte;
    }

    /**
     * Méthode qui renvoie un String de l'objet bien étiquette en mentionnant l'algorithme utilisé
     * ainsi que les valeurs du tableau de séquences
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
        String etiquette = nomDAlgorithme + " | Séquence: " + Arrays.toString(tabSequence);
        return etiquette;
    }
}

