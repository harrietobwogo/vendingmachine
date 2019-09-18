package com.example.vm.bean;

import com.example.vm.model.Denomination;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Harriet on 9/18/2019.
 */
@Local
public interface MoneyConverterBeanI {
    BigDecimal getMoneyValueFromDenominations(Map<Denomination,Integer> money);
    Map<Denomination,Integer> getDenominationsForMoney(BigDecimal amount);
}
