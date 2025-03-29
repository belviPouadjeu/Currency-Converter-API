package com.belvinard.currency_converter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${exchange.rate.api.url}")
    private String exchangeRateApiUrl;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {

        return builder.baseUrl(exchangeRateApiUrl).build();
    }
}
