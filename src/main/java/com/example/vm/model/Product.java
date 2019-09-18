package com.example.vm.model;

import java.math.BigDecimal;

/**
 * Created by Harriet on 9/18/2019.
 */
//public enum Product {
//    SODA(25.0),
//    SWEET(5.0),
//    BISCUIT(40.0),
//    CHOCOLATE(170.0);
//
//
//    private double unitPrice;
//
//    Product(double unitPrice) {
//        this.unitPrice = unitPrice;
//    }
//}
public class Product{
    private long id;
    private String name;
    private BigDecimal unitPrice;

    public Product() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}