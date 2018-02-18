package com.example.reddy.fitnessapp;

/**
 * Created by reddy on 17-02-2018.
 */

public class Product {

    String name;
    Integer value;

    Product(String name, Integer value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
