package org.xmlws.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TypeConverter {

    public static LocalDate parseDate(String value) {
        return LocalDate.parse(value);
    }

    public static LocalDateTime parseDateTime(String value) {
        return LocalDateTime.parse(value);
    }

    public static Boolean parseBoolean(String value) {
        return new Boolean(value);
    }

    public static Integer parseInteger(String value) {
        return new Integer(value);
    }

    public static Long parseLong(String value) {
        return new Long(value);
    }
}