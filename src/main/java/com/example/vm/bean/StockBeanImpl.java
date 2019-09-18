package com.example.vm.bean;

import com.example.vm.model.Product;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Created by Harriet on 9/18/2019.
 */
@Local
@Stateless
public class StockBeanImpl implements StockBeanI {
    @Override
    public int getStockBalance(Product product) {
        return 0;
    }
}
