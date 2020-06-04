package com.vmware.neighborstour.model;

public class BudgetInLocalCurrency {

    private String country;

    private String budget;

    public BudgetInLocalCurrency(String country, String budget) {
        this.country = country;
        this.budget = budget;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }
}
