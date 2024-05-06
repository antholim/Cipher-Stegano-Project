package ca.qc.bdeb.sim202;

import java.util.ArrayList;

public class TestCrypteurs {

    public static final String TEXTE_DEFAUT = "\033[0m";
    public static final String TEXTE_ROUGE = "\033[1;31m";
    public static final String TEXTE_VERT = "\033[1;32m";

    private int testsPasses = 0;
    private int testsTotaux = 0;

    /**
     * Lance une série de tests pour les différents crypteurs
     */
    public static void tester() {
        TestCrypteurs testeur = new TestCrypteurs();
        testeur.executerTests();
    }

    public void executerTests() {
        Crypteur crypteurInverse = new CrypteurInverse();
        testCrypteur(crypteurInverse, "bonjour", "ruojnob");
        testCrypteur(crypteurInverse, "laval", "laval");
        testCrypteur(crypteurInverse, "abc123ABC   '", "'   CBA321cba");

        CrypteurCesar crypteurCesar1 = new CrypteurCesar(1);
        testCrypteur(crypteurCesar1, "abc", "bcd");
        testCrypteur(crypteurCesar1, "xyz", "yza");
        testCrypteur(crypteurCesar1, "ABC", "BCD");
        testCrypteur(crypteurCesar1, "abcABC", "bcdBCD");
        testCrypteur(crypteurCesar1, "123!", "123!");

        CrypteurCesar crypteurCesar5 = new CrypteurCesar(5);
        testCrypteur(crypteurCesar5, "abc", "fgh");
        testCrypteur(crypteurCesar5, "xyz", "cde");
        testCrypteur(crypteurCesar5, "ABC", "FGH");
        testCrypteur(crypteurCesar5, "abcABC", "fghFGH");
        testCrypteur(crypteurCesar5, "123!", "123!");

        CrypteurCesar crypteurCesar25 = new CrypteurCesar(25);
        testCrypteur(crypteurCesar25, "abc", "zab");
        testCrypteur(crypteurCesar25, "xyz", "wxy");
        testCrypteur(crypteurCesar25, "ABC", "ZAB");
        testCrypteur(crypteurCesar25, "abcABC", "zabZAB");
        testCrypteur(crypteurCesar25, "123!", "123!");

        // Passer le paramètre 0 n'est pas invalide, ça veut juste dire que
        // le décalage est de 0, donc qu'on laisse le texte tel quel
        CrypteurCesar crypteurCesar0 = new CrypteurCesar(0);
        for (String message : new String[]{"abc", "ABC", " 123!! ", "zzz"}) {
            testCrypteur(crypteurCesar0, message, message);
        }

        Crypteur crypteurRot13 = new CrypteurRot13();
        testCrypteur(crypteurRot13, "abc", "nop");
        testCrypteur(crypteurRot13,
                "Ceci est un test d'encodage en ROT13!",
                "Prpv rfg ha grfg q'rapbqntr ra EBG13!");

        Crypteur crypteurVigenere1 = new CrypteurVigenere("TEST");
        testCrypteur(crypteurVigenere1, "Bonjour!", "Usfchyj!");
        testCrypteur(crypteurVigenere1, "Les carottes sont cuites :(", "Eik vtvgmmik lhrl vnmlxl :(");

        // La clé peut être donnée en majuscule ou en minuscule, ça devrait donner le même résultat
        Crypteur crypteurVigenere2 = new CrypteurVigenere("test");
        testCrypteur(crypteurVigenere2, "Bonjour!", "Usfchyj!");
        testCrypteur(crypteurVigenere2, "Les carottes sont cuites :(", "Eik vtvgmmik lhrl vnmlxl :(");

        Crypteur crypteurVigenere3 = new CrypteurVigenere("SECRET");
        testCrypteur(crypteurVigenere3, "J'ai un truc a dire", "B'ek lr mjye r hbji");
        testCrypteur(crypteurVigenere3,
                "Mais ce n'est rien d'interessant...",
                "Eekj gx f'iuk vbwr f'zrmwvgjwtfx...");


        // Le cryptage par séquences aléatoires ne donne jamais le même secret pour un message donné
        // On peut cependant tester :
        // - Le décodage
        // - decoder(encoder(message)) == message
        // - Le nombre de lettres attendu dans le message encodé
        Crypteur crypteurSequenceAleatoire1 = new CrypteurSequenceAleatoire(new int[]{1});
        testEncodageDecodage(crypteurSequenceAleatoire1, "t.eAsztG", "test", false);
        testEncodageDecodage(crypteurSequenceAleatoire1,
                crypteurSequenceAleatoire1.encoder("test"),
                "test", false);
        testLongueurEncodage(crypteurSequenceAleatoire1, "test", 8);
        testEncodageDecodage(crypteurSequenceAleatoire1, "H!a!h!a!h!a!", "Hahaha", false);
        testEncodageDecodage(crypteurSequenceAleatoire1,
                crypteurSequenceAleatoire1.encoder("Hahaha"),
                "Hahaha", false);
        testLongueurEncodage(crypteurSequenceAleatoire1, "Hahaha", 12);
        testEncodageDecodage(crypteurSequenceAleatoire1, "z;", "z", false);
        testEncodageDecodage(crypteurSequenceAleatoire1,
                crypteurSequenceAleatoire1.encoder("z"),
                "z", false);
        testLongueurEncodage(crypteurSequenceAleatoire1, "z", 2);

        Crypteur crypteurSequenceAleatoire321 = new CrypteurSequenceAleatoire(new int[]{3, 2, 1});
        testEncodageDecodage(crypteurSequenceAleatoire321, "1xa1Aof!!", "1A!", false);
        testEncodageDecodage(crypteurSequenceAleatoire321,
                crypteurSequenceAleatoire321.encoder("1A!"),
                "1A!", false);
        testLongueurEncodage(crypteurSequenceAleatoire321, "1A!", 9);
        testEncodageDecodage(crypteurSequenceAleatoire321,
                "B&7-o%/nijxLhoy#uCr4H1!-0",
                "Bonjour!", false);
        testEncodageDecodage(crypteurSequenceAleatoire321,
                crypteurSequenceAleatoire321.encoder("Bonjour!"),
                "Bonjour!", false);
        testLongueurEncodage(crypteurSequenceAleatoire321, "Bonjour1", 25);
        testEncodageDecodage(crypteurSequenceAleatoire321, "xAAA", "x", false);
        testEncodageDecodage(crypteurSequenceAleatoire321,
                crypteurSequenceAleatoire321.encoder("x"),
                "x", false);
        testLongueurEncodage(crypteurSequenceAleatoire321, "x", 4);
        testEncodageDecodage(crypteurSequenceAleatoire321, "xAAAyBB", "xy", false);
        testEncodageDecodage(crypteurSequenceAleatoire321,
                crypteurSequenceAleatoire321.encoder("xy"),
                "xy", false);

        ArrayList<Crypteur> algos1 = new ArrayList<>();
        algos1.add(new CrypteurCesar(1));
        algos1.add(new CrypteurInverse());
        CrypteurMultiple crypteurMultiple1 = new CrypteurMultiple(algos1);

        testCrypteur(crypteurMultiple1, "21", "12");
        testCrypteur(crypteurMultiple1, "za", "ba");
        testCrypteur(crypteurMultiple1, "Bonjour!", "!svpkopC");

        // Ces différentes algos devraient s'annuler les uns les autres
        ArrayList<Crypteur> algos2 = new ArrayList<>();
        algos2.add(new CrypteurInverse());
        algos2.add(new CrypteurRot13());
        algos2.add(new CrypteurCesar(10));
        algos2.add(new CrypteurRot13());
        algos2.add(new CrypteurCesar(16));
        algos2.add(new CrypteurInverse());
        CrypteurMultiple crypteurMultiple2 = new CrypteurMultiple(algos2);

        for (String message : new String[]{"abc", "ABC", " 123!! ", "zzz", "Message TROP complexe"}) {
            testCrypteur(crypteurMultiple2, message, message);
        }

        afficherStatistiques();
    }

    /**
     * Teste l'encodage *ou* le décodage d'un message secret avec le crypteur passé en paramètre
     *
     * @param crypteur      Le crypteur (n'importe quelle sous-classe de Crypteur) à utiliser
     * @param message       Le message à encoder ou le secret à décoder
     * @param secretAttendu La valeur que le crypteur devrait calculer
     * @param encoder       true si on encode un message en clair, false si on décode un secret
     */
    private void testEncodageDecodage(Crypteur crypteur, String message, String secretAttendu, boolean encoder) {
        testsTotaux++;

        String secretObtenu;

        if (encoder) {
            secretObtenu = crypteur.encoder(message);
        } else {
            secretObtenu = crypteur.decoder(message);
        }

        if (secretObtenu.equals(secretAttendu)) {
            testsPasses++;
        } else {
            System.out.print(TEXTE_ROUGE);
            System.out.println("ERREUR " + (encoder ? "à l'ENCODAGE" : "au DÉCODAGE"));
            System.out.println("\tCrypteur: " + crypteur);
            System.out.println("\tMessage: " + message);
            System.out.println("\tCode secret attendu: " + secretAttendu);
            System.out.println("\tCode secret obtenu: " + secretObtenu);
            System.out.print(TEXTE_DEFAUT);
        }
    }

    /**
     * Teste l'encodage *et* le décodage d'un message à l'aide d'un crypteur donné
     *
     * @param crypteur L'objet crypteur à tester (n'importe quelle sous-classe)
     * @param message  Le message clair
     * @param secret   Le secret résultant une fois le message crypté
     */
    private void testCrypteur(Crypteur crypteur, String message, String secret) {
        testEncodageDecodage(crypteur, message, secret, true);
        testEncodageDecodage(crypteur, secret, message, false);
    }

    /**
     * Teste la longueur du message encodé. Nécessaire pour les crypteurs qui
     * génèrent des messages contenant des lettres aléatoires.
     *
     * @param crypteur       L'objet crypteur à tester (n'importe quelle sous-classe)
     * @param message        Le message (texte clair) à encoder
     * @param tailleAttendue La taille attendue du secret (le message une fois encodé)
     */
    private void testLongueurEncodage(Crypteur crypteur, String message, int tailleAttendue) {
        testsTotaux++;

        String secret = crypteur.encoder(message);
        if (secret.length() == tailleAttendue) {
            testsPasses++;
        } else {
            System.out.print(TEXTE_ROUGE);
            System.out.println("ERREUR à l'ENCODAGE");
            System.out.println("\tCrypteur: " + crypteur);
            System.out.println("\tMessage: " + message);
            System.out.println("\tTaille attendue: " + tailleAttendue);
            System.out.println("\tTaille obtenue : " + secret + " (code secret obtenu: " + secret + ")");
            System.out.print(TEXTE_DEFAUT);
        }
    }

    /**
     * Affiche les statistiques sur les tests passés/échoués
     */
    public void afficherStatistiques() {
        System.out.println("Tests des Crypteurs");
        System.out.println("Nombre de tests réussis : " + testsPasses + " / " + testsTotaux);

        if (testsPasses == testsTotaux) {
            System.out.println(TEXTE_VERT + "=> Tout est bon!" + TEXTE_DEFAUT);
        } else {
            System.out.println(TEXTE_ROUGE + (testsTotaux - testsPasses) + " tests échoués" + TEXTE_DEFAUT);
        }
    }
}
