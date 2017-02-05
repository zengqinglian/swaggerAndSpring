package com.ericzeng.hieu.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

/**
 * Convert String to LocalDate type.
 * 
 * @author jtao
 *
 */
public class LocalDateConverter implements Converter<String, LocalDate> {
    private final DateTimeFormatter formatter;
    
    public LocalDateConverter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }
 
    public LocalDate convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
 
        return LocalDate.parse(source, formatter);
    }
}
