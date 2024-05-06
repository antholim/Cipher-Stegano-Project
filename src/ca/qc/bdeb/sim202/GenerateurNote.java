package ca.qc.bdeb.sim202;

public class GenerateurNote {
    public static int FREQUENCE_ECHANTILLONNAGE = 44100;
    public static int AMPLITUDE = 60;

    private int periode;
    private int compteur = 0;

    public GenerateurNote(int frequence) {
        this.periode = FREQUENCE_ECHANTILLONNAGE / frequence;
    }

    public int prochaineAmplitude() {
        int son;

        if (compteur / periode % 2 == 0)
            son = AMPLITUDE;
        else
            son = -AMPLITUDE;

        compteur++;

        return son;
    }
    public static int getFrequenceEchantillonnage() {
        return FREQUENCE_ECHANTILLONNAGE;
    }
}

