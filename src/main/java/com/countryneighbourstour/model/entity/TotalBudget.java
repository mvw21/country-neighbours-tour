package com.countryneighbourstour.model.entity;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class TotalBudget extends BaseEntity{
    private BigDecimal totalBudget;
    private String currency;

    public TotalBudget() {
    }

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
