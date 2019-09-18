package com.example.vm.bean;

import com.example.vm.model.CashDrawer;
import com.example.vm.model.Denomination;

import javax.ejb.Local;

/**
 * Created by Harriet on 9/18/2019.
 */
@Local
public interface CashDrawerBeanI extends BeanInterface<CashDrawer> {
    CashDrawer findByDenomination(Denomination denomination);
}
