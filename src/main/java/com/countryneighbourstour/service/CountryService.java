package com.countryneighbourstour.service;

import com.countryneighbourstour.model.dto.CountrySeedDto;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface CountryService {

    void importCountries() throws IOException;

    String readCountriesFileContent() throws IOException;

    List<CountrySeedDto> getAllCountries();

    ArrayList<String> getNeighbours(String countryName) throws IOException;

    BigDecimal getExchangeRate(String currencyCode,String currency) throws IOException;
}
