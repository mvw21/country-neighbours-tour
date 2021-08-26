package com.countryneighbourstour.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.LinkedHashMap;

@Entity
public class Result {
    @Id
    private Long id;
    private int travelAroundCountriesCount;
    private BigDecimal leftOverBudget;
    private LinkedHashMap<String,BigDecimal> neededCurrencyForEachCountry;

    public Result(int travelAroundCountriesCount, BigDecimal leftOverBudget) {
        this.travelAroundCountriesCount = travelAroundCountriesCount;
        this.leftOverBudget = leftOverBudget;
        this.neededCurrencyForEachCountry = new LinkedHashMap<>();
    }

    public Result() {
    }

    public int getTravelAroundCountriesCount() {
        return travelAroundCountriesCount;
    }

    public void setTravelAroundCountriesCount(int travelAroundCountriesCount) {
        this.travelAroundCountriesCount = travelAroundCountriesCount;
    }

    public BigDecimal getLeftOverBudget() {
        return leftOverBudget;
    }

    public void setLeftOverBudget(BigDecimal leftOverBudget) {
        this.leftOverBudget = leftOverBudget;
    }

    @Transient
    public LinkedHashMap<String, BigDecimal> getNeededCurrencyForEachCountry() {
        return neededCurrencyForEachCountry;
    }

    public void setNeededCurrencyForEachCountry(LinkedHashMap<String, BigDecimal> neededCurrencyForEachCountry) {
        this.neededCurrencyForEachCountry = neededCurrencyForEachCountry;
    }
}
