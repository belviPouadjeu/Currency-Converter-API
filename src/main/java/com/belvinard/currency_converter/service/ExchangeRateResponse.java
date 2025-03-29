package com.belvinard.currency_converter.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class ExchangeRateResponse {
    private String result; // Indique si la requête a réussi ("success")
    
    @JsonProperty("base_code")
    private String baseCode; // Devise de base (ex: "USD")

    @JsonProperty("conversion_rates")
    private Map<String, Double> conversionRates; // Liste des taux de conversion

    // Getters
    public String getResult() {
        return result;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }
}
