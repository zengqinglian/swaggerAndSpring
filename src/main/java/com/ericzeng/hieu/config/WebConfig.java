package com.ericzeng.hieu.config;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ericzeng.hieu.config.controller.CustomObjectMapper;
import com.ericzeng.hieu.config.controller.SpringBeanHandlerInstantiator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
public class WebConfig extends WebMvcConfigurerAdapter {
	 @Autowired
	 private ApplicationContext appContext;
	
	@Bean
    public MappingJackson2HttpMessageConverter converter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(customObjectMapper());
        return converter;
    }

    @Bean
    public ObjectMapper customObjectMapper() {
        CustomObjectMapper mapper = new CustomObjectMapper();
        mapper.setHandlerInstantiator(new SpringBeanHandlerInstantiator(appContext));
        return mapper;
    }
    

    @Bean
    public SerializationConfig serializationConfig() {
        return customObjectMapper().getSerializationConfig();
    }

    @Bean
    public Validator validator() {
      return new LocalValidatorFactoryBean();
    }
    
    @Bean
    public MethodValidationPostProcessor mvpp() {
      MethodValidationPostProcessor mvpp = new MethodValidationPostProcessor();
      mvpp.setValidator(validator());
      return mvpp;
    }

    @Bean(name = "multipartResolver")
  	public CommonsMultipartResolver getResolver() throws IOException {
  		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
  		resolver.setMaxUploadSize(5242880);
  		return resolver;
  	}

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        arrayHttpMessageConverter.setSupportedMediaTypes(getSupportedMediaTypes());
        return arrayHttpMessageConverter;
    }

    private List<MediaType> getSupportedMediaTypes() {
        List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.IMAGE_PNG);
        return list;
    }

    
    /**
     * Forward all requests for static content to the containers default servlet.
     *
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateConverter(DateTimeFormatter.ISO_DATE));
        registry.addConverter(new LocalDateTimeConverter(DateTimeFormatter.ISO_DATE_TIME));
        registry.addConverter(new ZonedDateTimeConverter(DateTimeFormatter.ISO_DATE_TIME));
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter());
        extendMessageConverters(converters);
    }

    @Override
    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
        converters.add(byteArrayHttpMessageConverter());
    }
}
