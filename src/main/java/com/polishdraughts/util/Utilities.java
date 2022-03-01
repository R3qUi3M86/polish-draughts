package com.polishdraughts.util;

import com.polishdraughts.model.Pawn;

import java.util.Map;
import java.util.Objects;

public abstract class Utilities {
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public static boolean arrayContains(Integer[] array, Integer i){
        for (Integer element : array){
            if (Objects.equals(i, element)){
                return true;
            }
        }
        return false;
    }

    public static Integer getPawnFieldNoFromSet(Map<Integer, Pawn> map, Pawn pawn) {
        Integer result = null;
        if (map.containsValue(pawn)) {
            for (Map.Entry<Integer, Pawn> entry : map.entrySet()) {
                if (Objects.equals(entry.getValue(), pawn)) {
                    result = entry.getKey();
                }
            }
        }
        return result;
    }
}
