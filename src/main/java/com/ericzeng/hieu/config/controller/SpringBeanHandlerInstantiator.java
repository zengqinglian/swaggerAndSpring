package com.ericzeng.hieu.config.controller;

import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;

public class SpringBeanHandlerInstantiator extends HandlerInstantiator {

	private ApplicationContext applicationContext;


    public SpringBeanHandlerInstantiator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Override
    public JsonDeserializer<?> deserializerInstance(DeserializationConfig config, Annotated annotated, Class<?> deserClass) {
        try {
            return (JsonDeserializer<?>) applicationContext.getBean(deserClass);
        } catch (Exception e) {
            // Return null and let the default behavior happen
        }
        return null;
    }

    @Override
    public KeyDeserializer keyDeserializerInstance(DeserializationConfig config, Annotated annotated, Class<?> keyDeserClass) {
        try {
            return (KeyDeserializer) applicationContext.getBean(keyDeserClass);
        } catch (Exception e) {
            // Return null and let the default behavior happen
        }
        return null;
    }

    @Override
    public JsonSerializer<?> serializerInstance(SerializationConfig config, Annotated annotated, Class<?> serClass) {
        try {
            return (JsonSerializer<?>) applicationContext.getBean(serClass);
        } catch (Exception e) {
            // Return null and let the default behavior happen
        }
        return null;
    }

    @Override
    public TypeResolverBuilder<?> typeResolverBuilderInstance(MapperConfig<?> config, Annotated annotated, Class<?> builderClass) {
        return null;
    }

    @Override
    public TypeIdResolver typeIdResolverInstance(MapperConfig<?> config, Annotated annotated, Class<?> resolverClass) {
        return null;
    }
}
