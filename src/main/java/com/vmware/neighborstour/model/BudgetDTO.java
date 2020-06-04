package com.vmware.neighborstour.model;

import java.io.Serializable;
import java.util.Map;

public class BudgetDTO implements Serializable {
    public static final long serialVersionUID = 1L;

    private int numberOfTrips;
    private int remainingBudget;
    private Map<String, String> budgetInLocalCurrencies;

    public BudgetDTO(int numberOfTrips, int remainingBudget,
            Map<String, String> budgetInLocalCurrencies) {
        this.numberOfTrips = numberOfTrips;
        this.remainingBudget = remainingBudget;
        this.budgetInLocalCurrencies = budgetInLocalCurrencies;
    }

    public int getNumberOfTrips() {
        return numberOfTrips;
    }

    public void setNumberOfTrips(int numberOfTrips) {
        this.numberOfTrips = numberOfTrips;
    }

    public int getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(int remainingBudget) {
        this.remainingBudget = remainingBudget;
    }

    public Map<String, String> getBudgetInLocalCurrencies() {
        return budgetInLocalCurrencies;
    }

    public void setBudgetInLocalCurrencies(Map<String, String> budgetInLocalCurrencies) {
        this.budgetInLocalCurrencies = budgetInLocalCurrencies;
    }
}
