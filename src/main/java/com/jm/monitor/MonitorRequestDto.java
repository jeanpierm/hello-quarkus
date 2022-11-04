package com.jm.monitor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jm.config.ValidationGroups;

public class MonitorRequestDto {

    @NotNull(groups = { ValidationGroups.Post.class, ValidationGroups.Put.class })
    @Size(min = 1, max = 255)
    private String model;

    @NotNull(groups = { ValidationGroups.Post.class, ValidationGroups.Put.class })
    @Min(value = 0)
    private Double price;

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
        return "MonitorRequestDto [model=" + model + ", price=" + price + "]";
    }
}
