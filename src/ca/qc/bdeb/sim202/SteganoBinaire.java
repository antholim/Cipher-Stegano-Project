package ca.qc.bdeb.sim202;

import java.io.*;

/**
 * Classe qui hérite de Steganographeur et qui encore ou décode un message à l'aide de fichier binaires
 *
 * @author Hugo Valente
 * @version JDK 16.0.2
 */
public class SteganoBinaire extends Steganographeur {
    /**
     * Constructeur de Stegano Binaire
     *
     * @param crypteur est le crypteur choisi par l'utilisateur qui encodera ou déodera un message
     */
    public SteganoBinaire(Crypteur crypteur) {
        this.crypteur = crypteur;
    }

    /**
     * Méthode qui encode un message en le mettant dans un fichier binaire
     *
     * @param nomFichier est le nom du fichier dans lequel on encodera le message
     * @param message    est le message à encoder
     */
    @Override
    public void encoderFichier(String nomFichier, String message) {
        message = crypteur.encoder(message);
        try (DataOutputStream fichier = new DataOutputStream(new FileOutputStream(nomFichier))) {
            for (int i = 0; i < message.length(); i++) {
                fichier.writeDouble(message.charAt(i));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui décode un fichier binaire à l'aide d'un RandomAccessFile
     *
     * @param nomFichier est le nom du fichier qui contient le message crtpté
     * @return crypteur.decoder(messageSecret), soit le message décodé
     */
    @Override
    public String decoderFichier(String nomFichier) {
        int position = 0;
        char lettre;
        messageSecret = "";
        try (RandomAccessFile fichier = new RandomAccessFile(nomFichier, "r")) {
            do {
                fichier.seek(position);
                lettre = (char) fichier.readDouble();
                messageSecret += lettre;
                position += Double.BYTES;
            } while (position < fichier.length());
        } catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé");
        } catch (IOException e) {
            System.out.println("IOException");
        }
        return crypteur.decoder(messageSecret);
    }

    /**
     * Méthode qui renvoie un String de l'objet bien étiquette en mentionnant le type de steganographe utilisé ainsi que ses
     * composantes s'il y a lieu
     *
     * @return Retourne le String
     */
    @Override
    public String toString() {
        return "Steganographeur[Type: Binaire | Crypteur[Algorithme: " + crypteur.getEtiquette() + "]";
    }
}
