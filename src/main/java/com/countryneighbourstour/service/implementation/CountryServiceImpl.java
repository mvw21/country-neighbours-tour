package com.countryneighbourstour.service.implementation;

import com.countryneighbourstour.common.GlobalConstants;
import com.countryneighbourstour.model.dto.CountrySeedDto;
import com.countryneighbourstour.model.entity.Country;
import com.countryneighbourstour.repository.CountryRepository;
import com.countryneighbourstour.service.CountryService;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, Gson gson) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        CountrySeedDto[] countrySeedDtos = this.gson
                .fromJson(new FileReader(GlobalConstants.COUNTRIES_FILE_PATH), CountrySeedDto[].class);
        System.out.println();
        Arrays.stream(countrySeedDtos)
                .forEach(dto -> {
                    if (this.countryRepository
                            .findByCountryName(dto.getCountryName()) == null) {

                        Country country = this.modelMapper.map(dto, Country.class);

                        String currency = "";

                        try {
                            currency = getCountryCurrency(country.getCountryCode());
                            System.out.println(currency);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        country.setCurrencyCode(currency);
                        this.countryRepository.saveAndFlush(country);

                        sb.append(String.format("Successfully imported country - %s",
                                dto.getCountryName()))
                                .append(System.lineSeparator());

                    } else {
                        sb.append("Already in DB")
                                .append(System.lineSeparator());
                    }

                });


    }

    private String getCountryCurrency(String countryCode) throws FileNotFoundException {
        JsonObject jsonObject = this.gson
                .fromJson(new FileReader(GlobalConstants.CURRENCY_FILE_PATH), JsonObject.class);

        return jsonObject.get(countryCode).getAsString();
    }

    @Override
    public String readCountriesFileContent() throws IOException {
        return Files.readString(Path.of(GlobalConstants.COUNTRIES_FILE_PATH));
    }

    @Override
    public List<CountrySeedDto> getAllCountries() {
        return this.countryRepository.findAll()
                .stream()
                .map(e -> this.modelMapper.map(e, CountrySeedDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ArrayList<String> getNeighbours(String countryCode) throws IOException {
        ArrayList<String> neighbours = new ArrayList<>();

        JsonObject neighboursJsonObject = this.gson
                .fromJson(new FileReader(GlobalConstants.NEIGHBOURS_FILE_PATH), JsonObject.class);

        JsonArray object = neighboursJsonObject.getAsJsonObject("countries")
                .getAsJsonObject(countryCode)
                .getAsJsonArray("neighbours");

        object.forEach(v -> neighbours.add(v.getAsString()));

        return neighbours;
    }

    @Override
    public BigDecimal getExchangeRate(String currencyCode, String currency) throws IOException {

        URL obj = new URL(GlobalConstants.EXCHANGE_FILE_PATH.concat(currency.toUpperCase()));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String responseToString = response.toString();

        JsonObject myResponse = JsonParser.parseString(responseToString).getAsJsonObject();

        JsonElement currencyObject = myResponse.get("conversion_rates").getAsJsonObject()
                .get("BGN");
        System.out.println(currencyObject);

        BigDecimal objectValue = myResponse.get("conversion_rates").getAsJsonObject()
                .get(currencyCode).getAsBigDecimal();

        return objectValue;
    }
}
