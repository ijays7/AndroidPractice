package com.ijays.apolo.util;

/**
 * Created by ijaysdev on 24/07/2017.
 */

public class MathUtil {
    public static int constrain(int amount, int low, int high) {
        return amount < low ? low : (amount > high ? high : amount);
    }
}
