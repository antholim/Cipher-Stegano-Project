package ca.qc.bdeb.sim202;

import java.io.*;

/**
 * Classe qui hérite de Steganographeur et qui encode ou décode un message à partir d'un fichier .txt
 * @author Hugo Valente
 * @version JDK 16.0.2
 *
 */
public class SteganoLivre extends Steganographeur{
    private int nbCaracteres;
    private String nomFichierLire;

    /**
     * Constructeur de SteganoLivre
     * @param crypteur est le crypteur rentré par l'utilisateur
     * @param nomFichier est le nom du fichier qui sera lu par notre BufferedReader dans la méthode encoderFichier
     * @param nbCaracteres est le nombre de caractères entre chaque lettre du mot
     */
    public SteganoLivre(Crypteur crypteur, String nomFichier, int nbCaracteres){
        this.nbCaracteres = nbCaracteres;
        this.crypteur = crypteur;
        this.nomFichierLire = nomFichier;
    }
    /**
     * Méthode qui encode un message en écrivant dans un fichier un nombre de caractères suivant d'une lettre du message et ainsi de suite en terminant par le nombre de caractères
     * @param nomFichier sera le fichier dans lequel on écrira le message encodé
     * @param message sera le message écrit par l'utilisateur qui sera encrypté dans un fichier
     */
    @Override
    public void encoderFichier(String nomFichier, String message) {
        int compteur = 0;
        int numero = 0;
        char lettre;
        try(BufferedReader lecteur = new BufferedReader(new FileReader(nomFichierLire))) {
            try (BufferedWriter ecrire = new BufferedWriter(new FileWriter(nomFichier))){
                while (compteur < message.length()+1) {
                    for (int i = 0; i < nbCaracteres; i++) {
                        numero = lecteur.read();
                        lettre = (char) numero;
                        ecrire.write(lettre);
                    }
                    if (compteur < message.length())
                        ecrire.write(crypteur.decoder(message).charAt(compteur));
                    compteur++;
                }
            }catch (FileNotFoundException e){
                System.out.println("Fichier non trouvé");
            }catch (IOException e){
                System.out.println("IOException");
            }
        } catch (FileNotFoundException e){
            System.out.println("Fichier non trouvé");
        } catch (IOException e){
            System.out.println("IOException");
        }
    }
    /**
     *Méthode qui décode un fichier encodé qui est entré en paramètre par l'utilisateur
     *
     * @param nomFichierCrypte correspond au fichier crypté
     * @return messageSecret, soit le message décodé
     */
    @Override
    public String decoderFichier(String nomFichierCrypte) {
        int position = 0;
        int numero = 0;
        char lettre;
        messageSecret = "";
        try(RandomAccessFile fichier = new RandomAccessFile(nomFichierCrypte,"rw")) {
            while (position < fichier.length()-nbCaracteres) {
                position += nbCaracteres;
                fichier.seek(position);
                numero = fichier.read();
                lettre = (char) numero;
                messageSecret += lettre;
                position++;
            }
            messageSecret = crypteur.decoder(messageSecret);
        } catch (FileNotFoundException e){
            System.out.println("Fichier non trouvé");
        } catch (IOException e){
            System.out.println("IOException");
        }
        return messageSecret;
    }
    /**
     * Méthode qui renvoie un String de l'objet bien étiquette en mentionnant le type de steganographe utilisé ainsi que ses
     * composantes s'il y a lieu
     *
     * @return Retourne le String
     */
    @Override
    public String toString() {
        return "Steganographeur[Type: Livre | Crypteur[Algorithme:" + crypteur.getEtiquette() + "] | " + nomFichierLire + " | " + "Espacement: " + nbCaracteres + "]";
    }
}
