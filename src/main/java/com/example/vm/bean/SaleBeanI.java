package com.example.vm.bean;

import com.example.vm.model.Sale;

import javax.ejb.Local;

/**
 * Created by Harriet on 9/18/2019.
 */
@Local
public interface SaleBeanI {
    boolean makeSale(Sale sale);
}
