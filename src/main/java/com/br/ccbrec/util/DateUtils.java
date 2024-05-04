package com.br.ccbrec.util;

import java.util.HashMap;
import java.util.Map;

public class DateUtils {

    /**
     *
     * Splits date (dd/mm/yyyy) into a HashMap: (day->dd | month->mm | year-yyyy)
     *
     * @param date: 01/01/2024
     * @return HashMap
     */
    public static Map<String, String> splitRecitativosDate(String date){
        String day = DateUtils.normalizeDayOrMonth(date.substring(8, 10));
        String month = DateUtils.normalizeDayOrMonth(date.substring(6, 7));
        String year = date.substring(0, 4);

        Map<String, String> output = new HashMap<>();

        output.put("day", day);
        output.put("month", month);
        output.put("year", year);

        return output;
    }

    public static String normalizeDayOrMonth(String piece){
        if (piece.length() < 2){
            piece = String.format("0%s", piece);
        }
        return piece;
    }
}
