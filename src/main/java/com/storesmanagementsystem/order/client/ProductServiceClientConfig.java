package com.storesmanagementsystem.order.client;

import feign.Logger;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductServiceClientConfig {
    @Bean
    public OkHttpClient getProductClient(){
        return new OkHttpClient();
    }

    @Bean
    Logger.Level productLoggerLevel() {
        return Logger.Level.FULL;
    }
}
