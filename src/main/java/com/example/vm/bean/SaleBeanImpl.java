package com.example.vm.bean;

import com.example.vm.model.Sale;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Created by Harriet on 9/18/2019.
 */
@Local
@Stateless
public class SaleBeanImpl implements SaleBeanI {
    @Override
    public boolean makeSale(Sale sale) {
        return false;
    }
}
