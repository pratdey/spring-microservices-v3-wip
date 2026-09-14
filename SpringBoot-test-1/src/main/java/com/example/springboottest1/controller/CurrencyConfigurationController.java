package com.example.springboottest1.controller;

import com.example.springboottest1.configuration.CurrencyServiceConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConfigurationController {

    /* constructor injection */
    /* private final CurrencyServiceConfiguration currencyServiceConfiguration;

    public CurrencyConfigurationController(CurrencyServiceConfiguration currencyServiceConfiguration) {
        this.currencyServiceConfiguration = currencyServiceConfiguration;
    } */

    /* field injection */
    @Autowired
    private  CurrencyServiceConfiguration currencyServiceConfiguration;

    @GetMapping("/currency-service")
    public CurrencyServiceConfiguration retrieveCurrencyServiceConfiguration() {
        return currencyServiceConfiguration;
    }
}
