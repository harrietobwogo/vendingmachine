package com.example.vm.bean;

import com.example.vm.model.CashDrawer;
import com.example.vm.model.Denomination;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Created by Harriet on 9/18/2019.
 */
@Stateless
@Local
public class CashDrawerBeanImpl extends BeanImpl<CashDrawer> implements CashDrawerBeanI {
    @Override
    public CashDrawer findByDenomination(Denomination denomination) {
        return null;
    }
}
