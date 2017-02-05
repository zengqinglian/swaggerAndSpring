package com.ericzeng.hieu.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	 @Bean
	    public Docket newsApi() {
	        return new Docket(DocumentationType.SWAGGER_2).
	        		//Bug in springfox 2.5.0
	        		//https://github.com/springfox/springfox/issues/1346
	        		ignoredParameterTypes(ApiIgnore.class).
	        
	        		apiInfo(new ApiInfoBuilder().license("'name': 'eric'")
	                .title("Eric Platform API").version("1.1.0")
	                .build())

	        // Trick to make LocalDateTime type documented properly. Refer to
	        // http://springfox.github.io/springfox/docs/current
	        .directModelSubstitute(BigDecimal.class, Double.class)
	        .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
	        .directModelSubstitute(ZonedDateTime.class, String.class)
	        .directModelSubstitute(LocalDate.class, java.sql.Date.class)
	        .directModelSubstitute(LocalTime.class, String.class);
	    }

}