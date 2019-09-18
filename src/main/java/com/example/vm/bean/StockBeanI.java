package com.example.vm.bean;

import com.example.vm.model.Product;

import javax.ejb.Local;

/**
 * Created by Harriet on 9/18/2019.
 */
@Local
public interface StockBeanI {
    int getStockBalance(Product product);
}
