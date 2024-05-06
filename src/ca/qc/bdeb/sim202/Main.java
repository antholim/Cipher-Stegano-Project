package ca.qc.bdeb.sim202;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        TestStegano testSteg = new TestStegano();
        testSteg.executerTests();
        TestCrypteurs testCrypteurs = new TestCrypteurs();
        testCrypteurs.executerTests();
    }
}
