package com.countryneighbourstour.repository;

import com.countryneighbourstour.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country,String> {

        Country findByCountryName(String countryName);

        Country findByCurrencyCode(String currencyCode);

        Country findByCountryCode(String countryCode);



}
