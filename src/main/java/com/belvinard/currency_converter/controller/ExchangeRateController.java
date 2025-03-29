package com.belvinard.currency_converter.controller;
import com.belvinard.currency_converter.service.ExchangeRateService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/convert")
    public Mono<String> convertCurrency(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount) {

        return exchangeRateService.convert(from.toUpperCase(), to.toUpperCase(), amount)
                .map(convertedAmount -> String.format("%.2f %s = %.2f %s",
                        amount, from.toUpperCase(), convertedAmount, to.toUpperCase()))
                .onErrorResume(IllegalArgumentException.class, e -> Mono.just("Erreur : " + e.getMessage())) // ✅ Devise invalide
                .onErrorResume(RuntimeException.class, e -> Mono.just("Erreur technique : " + e.getMessage())); // ✅ Erreur API ou connexion
    }

}
