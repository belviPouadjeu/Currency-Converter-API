package com.belvinard.currency_converter;

import com.belvinard.currency_converter.service.ExchangeRateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

import java.util.Map;

@SpringBootApplication
public class CurrencyConverterApiApplication implements CommandLineRunner { // ✅ Ajouter CommandLineRunner

	private final ExchangeRateService exchangeRateService;

	public CurrencyConverterApiApplication(ExchangeRateService exchangeRateService) {
		this.exchangeRateService = exchangeRateService;
	}

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConverterApiApplication.class, args);
	}

	@Override
	public void run(String... args) { // ✅ Plus d'erreur !
		// Tester avec une devise de base (ex: USD)
		Mono<Map<String, Double>> ratesMono = exchangeRateService.getExchangeRates("USD");

		ratesMono.subscribe(rates -> {
			System.out.println("Taux de change pour USD : " + rates);
		});
	}
}
