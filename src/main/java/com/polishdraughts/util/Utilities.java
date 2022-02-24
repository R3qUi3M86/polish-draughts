package com.polishdraughts.util;

import com.polishdraughts.model.Pawn;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

    public static boolean arrayContains(Integer[] array, Integer i){
        for (Integer element : array){
            if (i == element){
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
