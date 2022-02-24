package com.polishdraughts.util;

public abstract class Utilities {
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static boolean arrayContains(Integer[] array, int i){
        for (Integer element : array){
            if (i == element){
                return true;
            }
        }
        return false;
    }
}
