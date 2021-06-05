package com.mygames.flappybird.utils;

import java.util.Random;

public class Utils {
    private static Random rand = new Random();

    public static int getRandomNumberByInterval(int interval) {
        return rand.nextInt(interval);
    }
}
