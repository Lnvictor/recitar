package com.br.ccbrec.util;

public class DateUtils {

    /**
     * Splits date (yyyy-mm-dd) into a HashMap: (day->dd | month->mm | year-yyyy)
     *
     * @param date: 01/01/2024
     * @return HashMap
     */
    public static SplitedDate splitRecitativosDate(String date) {
        String day = DateUtils.normalizeDayOrMonth(date.substring(8, 10));
        String month = DateUtils.normalizeDayOrMonth(date.substring(6, 7));
        String year = date.substring(0, 4);

        return new SplitedDate(day, month, year);
    }

    public static String normalizeDayOrMonth(String piece) {
        if (piece.length() < 2) {
            piece = String.format("0%s", piece);
        }
        return piece;
    }

    /**
     * Transforms a brDate ( dd/mm/yyyy ) into ( yyyy-mm-dd )
     *
     * @param brDate
     * @return String
     */
    public static String transformBrIntoPattern(String brDate) {
        String day = DateUtils.normalizeDayOrMonth(brDate.substring(0, 2));
        String month = DateUtils.normalizeDayOrMonth(brDate.substring(3, 5));
        String year = brDate.substring(6);

        return year + "-" + month + "-" + day;
    }
}
