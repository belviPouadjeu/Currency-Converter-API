package com.belvinard.currency_converter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class ExchangeRateService {

    private final WebClient webClient;
    private final String apiUrl;

    public ExchangeRateService(WebClient.Builder webClientBuilder,
                               @Value("${exchange.rate.api.url}") String apiUrl) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
        this.apiUrl = apiUrl.endsWith("/") ? apiUrl : apiUrl + "/";
    }

    public Mono<Map<String, Double>> getExchangeRates(String baseCurrency) {
        String finalUrl = apiUrl + baseCurrency;

        return webClient.get()
                .uri("/{baseCurrency}", baseCurrency)
                .retrieve()
                .bodyToMono(ExchangeRateResponse.class)
                .flatMap(response -> {
                    if (!"success".equalsIgnoreCase(response.getResult())) {
                        return Mono.error(new RuntimeException("Erreur API : " + response.getResult()));
                    }

                    if (response.getConversionRates() == null || response.getConversionRates().isEmpty()) {
                        return Mono.error(new RuntimeException("Taux de conversion absents."));
                    }

                    return Mono.just(response.getConversionRates());
                })
                .onErrorResume(error -> Mono.empty());
    }

    // ✅ Nouvelle méthode pour convertir les devises
    public Mono<Double> convert(String fromCurrency, String toCurrency, double amount) {
        return getExchangeRates(fromCurrency)
                .map(rates -> {
                    if (!rates.containsKey(toCurrency)) {
                        throw new IllegalArgumentException("Devise cible invalide : " + toCurrency);
                    }
                    double rate = rates.get(toCurrency);
                    return amount * rate;
                });
    }
}
