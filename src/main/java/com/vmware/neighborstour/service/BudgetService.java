package com.vmware.neighborstour.service;

import com.vmware.neighborstour.model.BudgetDTO;
import com.vmware.neighborstour.model.BudgetInLocalCurrency;
import com.vmware.neighborstour.model.Country;
import com.vmware.neighborstour.model.CurrencyRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BudgetService {
    private static final double DEFAULT_CURRENCY_RATE = 1;

    @Value("${rapidapi.host}")
    private String rapidApiHost;

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Value("${rapidapi.get.countries.url}")
    private String getCountriesApiBaseUrl;

    @Value("${exchangerates.get.latest.url}")
    private String getExchangeRatesApiBaseUrl;

    public BudgetDTO calculateBudget(String startingCountryCode, Integer budgetPerCountry,
            Integer totalBudget, String baseCurrency) {
        Country country = getStartingCountry(startingCountryCode);

        int numberOfNeighbors = country.getNeighbors().length;
        int numberOfTrips = totalBudget / (numberOfNeighbors * budgetPerCountry);
        int remainingBudget = totalBudget - (numberOfTrips * numberOfNeighbors * budgetPerCountry);

        CurrencyRate currencyRate = getCurrencyRate(baseCurrency);

        Country[] neighbors = getNeighborCountries(country.getNeighbors());
        Map<String, String> budgetInLocalCurrencies = Arrays.stream(neighbors).map(neighbor -> {
            String neighborCurrencyCode = neighbor.getCurrencies()[0];
            double rate = currencyRate.getRates().getOrDefault(neighborCurrencyCode, DEFAULT_CURRENCY_RATE);
            double budget = rate * budgetPerCountry * numberOfTrips;
            String formattedBudget = String.format("%.2f %s", budget,
                    currencyRate.getRates().containsKey(neighborCurrencyCode) ? neighborCurrencyCode : baseCurrency);
            return new BudgetInLocalCurrency(neighbor.getAlpha3Code(), formattedBudget);
        }).collect(Collectors.toMap(BudgetInLocalCurrency::getCountry, BudgetInLocalCurrency::getBudget));

        return new BudgetDTO(numberOfTrips, remainingBudget, budgetInLocalCurrencies);
    }

    private Country getStartingCountry(String startingCountryCode) {
        ResponseEntity<Country[]> response = getCountries(startingCountryCode);

        if (response.getStatusCode().isError() || response.getBody() == null || response.getBody().length != 1) {
            throw new NullPointerException("An error happened while querying starting country: " + startingCountryCode);
        }

        return response.getBody()[0];
    }

    private Country[] getNeighborCountries(String[] neighborCountryCodes) {
        ResponseEntity<Country[]> response = getCountries(neighborCountryCodes);

        if (response.getStatusCode().isError() || response.getBody() == null
                || response.getBody().length != neighborCountryCodes.length) {
            throw new NullPointerException("An error happened while querying neighbor countries: " + Arrays
                    .toString(neighborCountryCodes));
        }

        return response.getBody();
    }

    private ResponseEntity<Country[]> getCountries(String... countryCodes) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-RapidAPI-Host", rapidApiHost);
        httpHeaders.set("X-RapidAPI-Key", rapidApiKey);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        String uri = UriComponentsBuilder.fromHttpUrl(getCountriesApiBaseUrl)
                .queryParam("codes", String.join(";", countryCodes)).toUriString();

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Country[].class);
    }

    private CurrencyRate getCurrencyRate(String baseCurrency) {
        String uri = UriComponentsBuilder.fromHttpUrl(getExchangeRatesApiBaseUrl)
                .queryParam("base", baseCurrency).toUriString();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CurrencyRate> response = restTemplate.getForEntity(uri, CurrencyRate.class);

        if (response.getStatusCode().isError() || response.getBody() == null || response.getBody().getRates() == null) {
            throw new NullPointerException("An error happened while querying currency rate: " + baseCurrency);
        }

        return response.getBody();
    }
}
