package com.beersAPI.beers.Enumerator;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BeerType {

    @JsonProperty("Lager")
    LAGER("Lager"),
    @JsonProperty("Pilsner")
    PILSNER("Pilsner");

    private final String beerType;

    BeerType(String beerType) {
        this.beerType = beerType;
    }

    public String getBeerType() {
        return beerType;
    }
}
