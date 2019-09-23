package com.example.vm.bean;

import com.example.vm.model.Denomination;
import com.example.vm.model.Product;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Harriet on 9/18/2019.
 */
@Local
public interface VendingMachineBean {
    BigDecimal calculateRequiredAmount(Product product, int quantity)throws Exception;
    boolean makeSale(Product product,int quantity,Map<Denomination,Integer> denomination)throws Exception;

}
