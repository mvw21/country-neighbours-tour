package com.countryneighbourstour.init;

import com.countryneighbourstour.service.CountryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
    private final CountryService countryService;

    public DataInit(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.countryService.importCountries();
    }
}
