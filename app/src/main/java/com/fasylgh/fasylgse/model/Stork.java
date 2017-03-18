package com.fasylgh.fasylgse.model;

/**
 * Created by edem on 18/03/17.
 *
 *  Data model for stork recieved from GSE API.
 *
 */

public class Stork {
    private String name,price,volume;
    private Double change;


    public Stork(){}

    public Stork(String name, String price, String volume, Double change) {
        this.name = name;
        this.price = price;
        this.volume = volume;
        this.change = change;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }


    @Override
    public String toString() {
        return "Stork{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", volume='" + volume + '\'' +
                ", change=" + change +
                '}';
    }
}
