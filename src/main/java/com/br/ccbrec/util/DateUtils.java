package com.br.ccbrec.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtils {

    /**
     * Splits date (yyyy-mm-dd) into a SplitedDate instance: (day->dd | month->mm | year-yyyy)
     *
     * @param date: 01/01/2024
     * @return SplitedDate
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
     *
     * Get day, month, year and returns A SplitedDate instance with an add of (n) days
     *
     * @param year: 2024
     * @param month: 04
     * @param day: 12
     * @param days: 7
     * @return Splited date
     */
    public static SplitedDate addDays(String year, String month, String day, int days){
        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);
        int dayInt = Integer.parseInt(day);

        Calendar calendar = new GregorianCalendar(yearInt, monthInt, dayInt);
        calendar.add(Calendar.DAY_OF_WEEK, days);

        return new SplitedDate(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),
                String.valueOf(calendar.get(Calendar.MONTH)), String.valueOf(calendar.get(Calendar.YEAR)));
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

    /**
     *
     * Get Splited date and return String date representation in BR pattern (dd/mm/yyyy)
     *
     * @param year
     * @param month
     * @param day
     * @return String
     */
    public static String transformSplitedDateIntoStr(String year, String month, String day) {
        return String.format("%s/%s/%s", day, month, year);
    }
}
