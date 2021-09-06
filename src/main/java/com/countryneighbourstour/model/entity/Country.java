package com.countryneighbourstour.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity{
    private String countryName;
    private String countryCode;
    private String currencyCode;

    public Country() {
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
