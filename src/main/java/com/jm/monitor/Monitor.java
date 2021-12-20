package com.jm.monitor;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Cacheable
public class Monitor {

    @Id
    @GeneratedValue
    private Long id;

    private String model;

    private Double price;

    public Monitor() {
    }

    public Monitor(String model, Double price) {
        this.model = model;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Monitor [id=" + id + ", model=" + model + ", price=" + price + "]";
    }
}
