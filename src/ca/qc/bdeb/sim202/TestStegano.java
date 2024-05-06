package ca.qc.bdeb.sim202;

import java.io.*;

public class TestStegano {

    public static final String TEXTE_DEFAUT = "\033[0;30m";
    public static final String TEXTE_ROUGE = "\033[1;31m";
    public static final String TEXTE_VERT = "\033[1;32m";

    private int testsPasses = 0;
    private int testsTotaux = 0;

    /**
     * Lance une série de tests pour les différents crypteurs
     */
    public static void tester() {
        TestStegano testeur = new TestStegano();
        testeur.executerTests();
    }

    public void executerTests() {

        Crypteur crypteurSimple = new CrypteurInverse();

        Steganographeur steg1 = new SteganoLivre(crypteurSimple, "les_miserables.txt", 30);

        testEncodageDecodage(steg1, "message tres chouette", "test-livre-1.txt");
        testEncodageDecodage(steg1, "Ceci est un message d'interet public", "test-livre-2.txt");

        Steganographeur steg2 = new SteganoLivre(crypteurSimple, "les_miserables.txt", 185);
        testEncodageDecodage(steg2, "message tres chouette", "test-livre-3.txt");
        testEncodageDecodage(steg2, "Ceci est un message d'interet public", "test-livre-4.txt");

        Steganographeur steg3 = new SteganoBinaire(crypteurSimple);
        testEncodageDecodage(steg3, "message tres chouette", "test-doubles-1.bin");
        testEncodageDecodage(steg3, "Ceci est un message d'interet public", "test-doubles-2.bin");

        // C'est plus difficile de tester l'encodage ici, puisqu'un SteganoMusique
        // écrit des notes au hasard. On peut cependant vérifier :
        // - Le décodage
        // - decodage(encodage(message)) = message
        // - L'en-tête du fichier .wav (les premières valeurs binaires écrites, avant
        //   les données qui forment l'onde sonore)

        Steganographeur steg4 = new SteganoMusique(crypteurSimple, "Ceci est un message d'interet public");

        testDecodage(steg4, "test-musique-1.wav", "message tres chouette");
        testDecodage(steg4, "test-musique-2.wav", "Ceci est un message d'interet public");
        testDecodage(steg4, "test-musique-3.wav", "Les carottes sont tres tres bien cuites!");

        steg4.encoderFichier("test-encodage-musique.wav", "Les carottes sont tres tres bien cuites!");
        testEnteteCorrecte("test-encodage-musique.wav", "test-musique-3.wav");
        testDecodage(steg4, "test-encodage-musique.wav", "Les carottes sont tres tres bien cuites!");


        afficherStatistiques();
    }

    /**
     * Teste un stéganographeur en faisant deux tests, un encodage et un décodage
     *
     * @param steg Stéganographeur à utiliser (n'importe quelle sous-classe)
     * @param message Message (texte clair) à encoder/décoder
     * @param nomFichierExemple Fichier qui contient déjà le résultat attendu
     */
    private void testEncodageDecodage(Steganographeur steg, String message, String nomFichierExemple) {
        testEncodage(steg, message, nomFichierExemple);
        testDecodage(steg, nomFichierExemple, message);
    }

    /**
     * Valide l'en-tête .wav en comparant deux fichiers
     *
     * @param nomFichier Nom du fichier à tester
     * @param nomFichierAttendu Nom du fichier qui contient l'en-tête correcte attendue
     */
    private void testEnteteCorrecte(String nomFichier, String nomFichierAttendu) {
        testsTotaux++;

        if (fichiersIdentiques(nomFichier, nomFichierAttendu, 44)) {
            testsPasses++;
        } else {
            System.out.print(TEXTE_ROUGE);
            System.out.println("ERREUR à l'écriture de l'EN-TÊTE .wav");
            System.out.println("\tFichier écrit : " + nomFichier);
            System.out.println("\tL'en-tête binaire écrite est différent de l'en-tête de " + nomFichierAttendu);
            System.out.print(TEXTE_DEFAUT);
        }
    }

    /**
     * Teste l'encodage
     *
     * @param steg Stéganographeur (n'importe quelle sous-classe)
     * @param message Message à cacher dans le fichier
     * @param nomFichierAttendu Fichier qui devrait être produit par le Steganographeur
     */
    private void testEncodage(Steganographeur steg, String message, String nomFichierAttendu) {
        testsTotaux++;

        steg.encoderFichier("test-stegano.dat", message);

        if (fichiersIdentiques("test-stegano.dat", nomFichierAttendu)) {
            testsPasses++;
        } else {
            System.out.print(TEXTE_ROUGE);
            System.out.println("ERREUR à l'ENCODAGE de fichier");
            System.out.println("\tSteganographeur: " + steg);
            System.out.println("\tMessage: " + message);
            System.out.println("\tLe fichier écrit est différent de " + nomFichierAttendu);
            System.out.print(TEXTE_DEFAUT);
        }
    }

    /**
     * Teste le décodage
     *
     * @param steg Stéganographeur (n'importe quelle sous-classe)
     * @param nomFichier Fichier à décoder
     * @param messageAttendu Message caché dans le fichier à décoder
     */
    private void testDecodage(Steganographeur steg, String nomFichier, String messageAttendu) {
        testsTotaux++;

        String messageCache = steg.decoderFichier(nomFichier);

        if (messageCache.equals(messageAttendu)) {
            testsPasses++;
        } else {
            System.out.print(TEXTE_ROUGE);
            System.out.println("ERREUR au DECODAGE de fichier");
            System.out.println("\tSteganographeur: " + steg);
            System.out.println("\tMessage lu: " + messageCache);
            System.out.println("\tMessage attendu: " + messageAttendu);
            System.out.print(TEXTE_DEFAUT);
        }
    }

    private boolean fichiersIdentiques(String nomFichier1, String nomFichier2) {
        return fichiersIdentiques(nomFichier1, nomFichier2, -1);
    }

    /**
     * Compare tous les 0 et les 1 de deux fichiers pour déterminer
     * s'ils sont identiques ou non
     *
     * @param nomFichier1 Nom du premier fichier
     * @param nomFichier2 Nom du second fichier
     * @param nbBytes     Le nombre de bytes maximum à vérifier, ou -1 pour vérifier le fichier au complet
     * @return true si tous les bytes des fichiers sont identiques, false sinon
     */
    private boolean fichiersIdentiques(String nomFichier1, String nomFichier2, long nbBytes) {

        try (RandomAccessFile fichier1 = new RandomAccessFile(nomFichier1, "r");
             RandomAccessFile fichier2 = new RandomAccessFile(nomFichier2, "r")) {

            if (fichier1.length() != fichier2.length()) {
                return false;
            }

            while (fichier1.getFilePointer() < fichier1.length()) {
                if (fichier1.readByte() != fichier2.readByte()) {
                    return false;
                }

                if (nbBytes != -1 && fichier1.getFilePointer() >= nbBytes) {
                    break;
                }
            }

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Affiche les statistiques sur les tests passés/échoués
     */
    public void afficherStatistiques() {
        System.out.println("Tests des Stéganographeurs");
        System.out.println("Nombre de tests réussis : " + testsPasses + " / " + testsTotaux);

        if (testsPasses == testsTotaux) {
            System.out.println(TEXTE_VERT + "=> Tout est bon!" + TEXTE_DEFAUT);
        } else {
            System.out.println(TEXTE_ROUGE + (testsTotaux - testsPasses) + " tests échoués" + TEXTE_DEFAUT);
        }
    }
}
