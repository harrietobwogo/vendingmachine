package com.example.vm.bean;

import com.example.vm.model.Denomination;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Harriet on 9/18/2019.
 */
@Local
@Stateless
public class MoneyConverteBeanImpl implements MoneyConverterBeanI {
    @EJB
   private CashDrawerBeanI cashDrawerBeanI;
    @Override
    public BigDecimal getMoneyValueFromDenominations(Map<Denomination, Integer> money) {
        BigDecimal amount = BigDecimal.ZERO;
        for (Map.Entry m : money.entrySet()) {
            Denomination denomination = Denomination.valueOf(m.getKey().toString());
            int count = Integer.parseInt(m.getValue().toString());
            double denominationValue = denomination.getValue() * count;
            amount = amount.add(new BigDecimal(denominationValue));

        }
        return amount;
    }

    @Override
    public Map<Denomination, Integer> getDenominationsForMoney(BigDecimal amount) {
        Map<Denomination,Integer> denominations=new HashMap<>();
        List<Denomination> denominationList= Arrays.asList(Denomination.values());
        for(Denomination denomination:denominationList) {
            int denominationCount = getDenominationCount(denomination,amount);
            amount = amount.subtract(new BigDecimal(denomination.getValue() * denominationCount));
            denominations.put(denomination,denominationCount);

        }
        return denominations;
    }

    private int getDenominationCount(Denomination denomination,BigDecimal amount) {
        int denominationCount;
        if (amount.compareTo(new BigDecimal(denomination.getValue())) >= 0){
            double i=Double.parseDouble(amount.divide(new BigDecimal(denomination.getValue()))+" ");
            i=Math.floor(i);
            denominationCount=(int)i;
            int availableCount=this.getAvailableCountForDenomination(denomination);

            return availableCount>=denominationCount ? denominationCount:availableCount;
            //return Math.min(availableCount,denominationCount);
        }
        return 0;
    }
    private int getAvailableCountForDenomination(Denomination denomination){
        return cashDrawerBeanI.findByDenomination(denomination).getCount();
    }

}
