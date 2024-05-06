package ca.qc.bdeb.sim202;

import java.io.*;
import java.util.Random;

/**
 * Une classe qui hérite de Steganographeur qui cachera le message encrypté dans un fichier .wav
 * qui sera créé par la classe
 *
 * @author Anthony Lim
 * @version JDK 17.0.1
 */
public class SteganoMusique extends Steganographeur {
    int tailleTotale;
    int tailleDonnees;
    String messageEncoder;
    String messageInitial;

    /**
     * Constructeur
     *
     * @param crypteur
     * @param message
     */

    public SteganoMusique(Crypteur crypteur, String message) {
        this.crypteur = crypteur;
        this.messageInitial = message;
    }

    /**
     * La méthode commence par créer un fichier .wav avec une en-tête et insère des charactères du message
     * caché en byte dans le fichier .wav ainsi que certaines valeur de l'amplitude
     * Source : Travail Pratique 2 – Cryptage et Stéganographie (4.3)
     *
     * @param nomFichier Le nom du fichier .wav
     * @param message Le message à cacher et encrypté
     */
    @Override
    public void encoderFichier(String nomFichier, String message) {
        messageEncoder = crypteur.encoder(message);
        tailleDonnees = messageEncoder.length() * ((GenerateurNote.FREQUENCE_ECHANTILLONNAGE / 4) + 1);
        tailleTotale = tailleDonnees + 36;
        try (DataOutputStream fichier = new DataOutputStream(new FileOutputStream(nomFichier))) {
            fichier.writeInt(1380533848);
            fichier.writeInt(tailleTotale);
            fichier.writeInt(1463899717);
            fichier.writeInt(1718449184);
            fichier.writeInt(16);
            fichier.writeShort(1);
            fichier.writeShort(1);
            fichier.writeInt(GenerateurNote.FREQUENCE_ECHANTILLONNAGE);
            fichier.writeInt(GenerateurNote.FREQUENCE_ECHANTILLONNAGE);
            fichier.writeShort(2);
            fichier.writeShort(8);
            fichier.writeInt(1684108385);
            fichier.writeInt(tailleDonnees);
            for (int i = 0; i < crypteur.encoder(message).length(); i++) {
                fichier.writeByte(crypteur.encoder(message).charAt(i));
                Random rd = new Random();
                int note = (int) (30 + (100 - 30) * rd.nextDouble());
                int frequence = (int) (440 * (Math.pow(2, ((note - 69)) / 12)));
                GenerateurNote generateurNote1 = new GenerateurNote(frequence);
                for (int j = 0; j < GenerateurNote.FREQUENCE_ECHANTILLONNAGE / 4; j++) {
                    fichier.writeByte(generateurNote1.prochaineAmplitude());
                }
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    /**
     * Cette méthode décrypte le fichier .wav et nous retourne le message décrypter
     * @param nomFichier Le nom du fichier .wav
     * @return
     */
    @Override
    public String decoderFichier(String nomFichier) {
        String message = "";
        try (RandomAccessFile fichier = new RandomAccessFile(nomFichier, "r")) {
            int position = 44;
            fichier.skipBytes(position);
            while (fichier.getFilePointer() < fichier.length()) {
                char lettre = (char) fichier.readByte();
                message += lettre;
                fichier.skipBytes(GenerateurNote.FREQUENCE_ECHANTILLONNAGE / 4);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé");
        } catch (EOFException e) {
            System.out.println("EOFException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
        return crypteur.decoder(message);
    }

    /**
     * Méthode qui renvoie un String de l'objet bien étiquette en mentionnant le type de steganographe utilisé ainsi que ses
     * composantes s'il y a lieu
     *
     * @return Retourne le String
     */
    @Override
    public String toString() {
        return "Steganographeur[Type: Musique | Crypteur[Algorithme: " + crypteur.getEtiquette() + "]]";
    }
}


