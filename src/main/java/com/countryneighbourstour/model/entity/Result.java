package com.countryneighbourstour.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.HashMap;

@Entity
public class Result {
    @Id
    private Long id;
    private int travelAroundCountriesCount;
    private BigDecimal leftOverBudget;
    private HashMap<String,BigDecimal> neededCurrencyForEachCountry;

    public Result(int travelAroundCountriesCount, BigDecimal leftOverBudget) {
        this.travelAroundCountriesCount = travelAroundCountriesCount;
        this.leftOverBudget = leftOverBudget;
        this.neededCurrencyForEachCountry = new HashMap<>();
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
    public HashMap<String, BigDecimal> getNeededCurrencyForEachCountry() {
        return neededCurrencyForEachCountry;
    }

    public void setNeededCurrencyForEachCountry(HashMap<String, BigDecimal> neededCurrencyForEachCountry) {
        this.neededCurrencyForEachCountry = neededCurrencyForEachCountry;
    }
}
