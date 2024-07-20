package com.br.ccbrec.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static DataParameterWrapper  splitRecitativosDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        return prepareDataParameter(localDate);
    }

    public static DataParameterWrapper prepareDataParameter(LocalDate date) {
        String day = normalizeDayOrMonth(String.valueOf(date.getDayOfMonth()));
        String month = normalizeDayOrMonth(String.valueOf(date.getMonth().getValue()));
        String year = String.valueOf(date.getYear());

        return new DataParameterWrapper(day, month, year);
    }

    public static String normalizeDayOrMonth(String piece) {
        piece = piece.trim();

        if (piece.length() < 2) {
            piece = String.format("0%s", piece);
        }
        return piece;
    }

    public static String transformBrIntoPattern(String brDate) {
        DateTimeFormatter brFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter usFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(brDate, brFormatter);

        return date.format(usFormatter);
    }

    public static String transformWrapperDateIntoStr(String year, String month, String day) {
        return String.format("%s/%s/%s", day, month, year);
    }

    public static int compareDate(String date1, String date2) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate first = LocalDate.parse(date1, dateTimeFormatter);
        LocalDate second =  LocalDate.parse(date2, dateTimeFormatter);

        return first.compareTo(second);
    }

    public static int compareDateWrapper(DataParameterWrapper wrapper, DataParameterWrapper wrapper2) {
        LocalDate lDate1 = getLocalDateFromWrapper(wrapper);
        LocalDate lDate2 = getLocalDateFromWrapper(wrapper2);

        return lDate1.compareTo(lDate2);
    }

    public static LocalDate getLocalDateFromWrapper(DataParameterWrapper wrapper){
        int day = Integer.parseInt(wrapper.getDay());
        int month = Integer.parseInt(wrapper.getMonth());
        int year = Integer.parseInt(wrapper.getYear());

        return LocalDate.of(year, month, day);
    }
}
