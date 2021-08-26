package com.countryneighbourstour.web.controller;

import com.countryneighbourstour.model.dto.CountrySeedDto;
import com.countryneighbourstour.model.dto.InputDto;
import com.countryneighbourstour.model.entity.Result;
import com.countryneighbourstour.service.CountryService;
import com.countryneighbourstour.service.InputService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class CountryController {

    private final CountryService countryService;
    private final InputService inputService;

    public CountryController(CountryService countryService, InputService inputService) {
        this.countryService = countryService;
        this.inputService = inputService;
    }

    @GetMapping("/countries")
    public List<CountrySeedDto> importCountries() throws IOException {
        return this.countryService.getAllCountries();
    }

    @PostMapping("/input")
    public Result result(@RequestBody InputDto inputDto) throws IOException {
        return this.inputService.getData(inputDto);

    }

}
