package utils;

import java.util.Random;

public class RandomNumber {

    /*
        Generiert eine Zufallszahl
    */

    public static int generate(int min, int max) {
        Random random = new Random();
        int number = random.nextInt(max + min) + min;
        return number;
    }
}
