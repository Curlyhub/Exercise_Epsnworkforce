package com.epsnworkforce.homework.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Convertor  {
    @JsonProperty("rate")
    private double rate;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "{" + "rate='" + rate + '\'' + '}';
    }

}
