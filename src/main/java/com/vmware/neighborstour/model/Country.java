package com.vmware.neighborstour.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country implements Serializable {
    public static final long serialVersionUID = 1L;

    private String alpha2Code;

    private String alpha3Code;

    private String name;

    @JsonProperty("borders")
    private String[] neighbors;

    private String[] currencies;

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(String[] neighbors) {
        this.neighbors = neighbors;
    }

    public String[] getCurrencies() {
        return currencies;
    }

    public void setCurrencies(String[] currencies) {
        this.currencies = currencies;
    }
}
