package com.ericzeng.hieu.config;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

public class ZonedDateTimeConverter implements Converter<String, ZonedDateTime> {
    private final DateTimeFormatter formatter;
    
    public ZonedDateTimeConverter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }
 
    @Override
    public ZonedDateTime convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
 
        return ZonedDateTime.parse(source, formatter);
    }
}
