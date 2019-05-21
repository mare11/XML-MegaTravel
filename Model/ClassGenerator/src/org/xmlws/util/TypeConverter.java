package org.xmlws.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TypeConverter 
{
	private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy.";
	private static final String DATETIME_FORMAT_PATTERN = "dd.MM.yyyy. HH:mm";

	public static LocalDate parseDate(String value) 
	{
		return LocalDate.parse(value, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
	}

	public static LocalDateTime parseDateTime(String value) 
	{
		return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN));
	}
	
	public static Boolean parseBoolean(String value)
	{
		return new Boolean(value);
	}
	 
	public static Integer parseInteger(String value)
	{
		return new Integer(value);
	}
}