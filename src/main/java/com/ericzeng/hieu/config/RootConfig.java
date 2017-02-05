package com.ericzeng.hieu.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"com.ericzeng.hieu.config","com.ericzeng.hieu.controller"})
@PropertySources({
    @PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true),
    @PropertySource(value = "file:${user.home}/next-platform.properties", ignoreResourceNotFound = true),
    @PropertySource(value = "file:/suportedbdc_config/banco_digital/next/next-platform.properties", ignoreResourceNotFound = true),
    @PropertySource(value = "classpath:buildInfo", ignoreResourceNotFound = true)
})

@EnableTransactionManagement
@EnableAspectJAutoProxy
public class RootConfig {

}
