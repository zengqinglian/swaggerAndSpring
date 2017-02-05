package com.ericzeng.hieu.config.controller;

import java.time.ZonedDateTime;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

@Component
@Primary
public class CustomObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = -5829814108020959847L;

    public CustomObjectMapper() {
        JSR310Module javaTimeModule = new JSR310Module();
        javaTimeModule.addSerializer(ZonedDateTime.class, CustomInstantSerializer.ZONED_DATE_TIME);
        registerModule(javaTimeModule);
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        enable(SerializationFeature.INDENT_OUTPUT);
        enable(Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        setSerializationInclusion(Include.NON_NULL);
    }
}

