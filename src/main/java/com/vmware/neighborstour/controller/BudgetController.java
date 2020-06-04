package com.vmware.neighborstour.controller;

import com.vmware.neighborstour.model.BudgetDTO;
import com.vmware.neighborstour.service.BudgetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class BudgetController {
    private static final Set<String> COUNTRY_CODES = new HashSet<>();
    private static final Set<String> CURRENCY_CODES = new HashSet<>();

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;

        COUNTRY_CODES.addAll(Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA2));
        COUNTRY_CODES.addAll(Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3));

        CURRENCY_CODES.addAll(Currency.getAvailableCurrencies().stream().map(Currency::getCurrencyCode)
                .collect(Collectors.toSet()));
    }

    @GetMapping("/budget")
    public BudgetDTO calculateBudget(@RequestParam String startingCountry, @RequestParam Integer budgetPerCountry,
            @RequestParam Integer totalBudget, @RequestParam String currency) {
        if (!COUNTRY_CODES.contains(startingCountry)) {
            throw new IllegalArgumentException("Unknown country: " + startingCountry);
        }

        if (budgetPerCountry < 0) {
            throw new IllegalArgumentException("Budget per country can't be less than 0: " + currency);
        }

        if (totalBudget < 0) {
            throw new IllegalArgumentException("Total budget can't be less than 0: " + currency);
        }

        if (!CURRENCY_CODES.contains(currency)) {
            throw new IllegalArgumentException("Unknown currency: " + currency);
        }

        return budgetService.calculateBudget(startingCountry, budgetPerCountry, totalBudget, currency);
    }
}
