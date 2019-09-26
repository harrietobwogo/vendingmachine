package com.example.vm.bean;

import com.example.vm.exception.InsufficientAmountException;
import com.example.vm.exception.InsufficientProductQuantityException;
import com.example.vm.model.CashDrawer;
import com.example.vm.model.Denomination;
import com.example.vm.model.Product;
import com.example.vm.model.Sale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Harriet on 9/18/2019.
 */
@Singleton
@Local
@Startup
public class VendingMachineBeanImpl implements VendingMachineBean {
    @PostConstruct
    public void initialize() {


        List<Denomination> denominationList = Arrays.asList(Denomination.values());
        for (Denomination denomination : denominationList) {
            CashDrawer cashDrawer = cashDrawerBeanI.findByDenomination(denomination);
            if (cashDrawer == null) {
                cashDrawer = new CashDrawer();
                cashDrawer.setDenomination(denomination);
                cashDrawer.setCount(10);
                cashDrawerBeanI.add(cashDrawer);
            }

        }
    }

    @EJB
    private StockBeanI stockBeanI;
    @EJB
    private SaleBeanI saleBeanI;
    @EJB
    private MoneyConverterBeanI moneyConverterBeanI;
    @EJB
    private CashDrawerBeanI cashDrawerBeanI;

    @Override
    public BigDecimal calculateRequiredAmount(Product product, int quantity) {
        return product.getUnitPrice().multiply(new BigDecimal(quantity));
    }

    @Override
    public boolean makeSale(Product product, int quantity, Map<Denomination, Integer> denominations) throws InsufficientAmountException, InsufficientProductQuantityException {
        // Add denominations to VM's Cash Drawer
        for (Map.Entry m : denominations.entrySet()) {
            Denomination denomination = Denomination.valueOf(m.getKey().toString());
            CashDrawer cashDrawer = cashDrawerBeanI.findByDenomination(denomination);
            cashDrawer.setCount(cashDrawer.getCount() + Integer.parseInt(m.getValue() + ""));
            cashDrawerBeanI.edit(cashDrawer);
        }
        BigDecimal userAmount = moneyConverterBeanI.getMoneyValueFromDenominations(denominations);
        BigDecimal requiredAmount = this.calculateRequiredAmount(product, quantity);
        BigDecimal userBalance = userAmount.subtract(requiredAmount);

        //validate userAmount
        if (userAmount.compareTo(this.calculateRequiredAmount(product, quantity)) < 0) {
            refundCustomerMoney(denominations);
            throw new InsufficientAmountException();
        }
        //validate product availabilty
        if (stockBeanI.getStockBalance(product) < quantity) throw new InsufficientProductQuantityException();
        //check if there's change to be given
        boolean giveChange = false;
        if (userAmount.compareTo(this.calculateRequiredAmount(product, quantity)) > 0)
            giveChange = true;


        Sale sale = new Sale();
        sale.setDate(new Date());
        sale.setAmount(this.calculateRequiredAmount(product, quantity));
        sale.setProduct(product);
        sale.setQuantity(quantity);
        boolean status = saleBeanI.makeSale(sale);
        if (giveChange)
            status = status && (giveChange && !this.giveChange(userAmount.subtract(this.calculateRequiredAmount(product, quantity))).isEmpty());
        if (!status)
            this.refundCustomerMoney(denominations);
        return status;


    }

    //refund customer money if machine does not have enough money to give change
    private void refundCustomerMoney(Map<Denomination, Integer> map) {
        for (Map.Entry m : map.entrySet()) {
            Denomination denomination = Denomination.valueOf(m.getKey().toString());
            CashDrawer cashDrawer = cashDrawerBeanI.findByDenomination(denomination);
            cashDrawer.setCount(cashDrawer.getCount() - (Integer) m.getValue());
            //update the denomination in the VM's cashdrawer
            cashDrawerBeanI.edit(cashDrawer);
        }

    }

    //TO DO {make the give change use event model to achieve decoupling}
    private Map<Denomination, Integer> giveChange(BigDecimal userAmount) {
        Map<Denomination, Integer> m = moneyConverterBeanI.getDenominationsForMoney(userAmount);
        return moneyConverterBeanI.getMoneyValueFromDenominations(m).compareTo(userAmount) == 0 ? m : new HashMap<Denomination, Integer>();

    }

}
