package com.ericzeng.hieu.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

/**
 * Convert String to LocalDateTime type.
 * 
 * @author jtao
 *
 */
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    private final DateTimeFormatter formatter;
    
    public LocalDateTimeConverter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }
 
    @Override
    public LocalDateTime convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
 
        return LocalDateTime.parse(source, formatter);
    }
}
