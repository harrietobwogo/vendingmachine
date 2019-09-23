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
        BigDecimal amount = moneyConverterBeanI.getMoneyValueFromDenominations(denominations);
        //validate amount
        if (amount.compareTo(this.calculateRequiredAmount(product, quantity)) < 0)
            throw new InsufficientAmountException();

        //validate product availabilty
        if (stockBeanI.getStockBalance(product) < quantity) throw new InsufficientProductQuantityException();
        //check if there's change to be given
        boolean giveChange = false;
        if (amount.compareTo(this.calculateRequiredAmount(product, quantity)) > 0)
            giveChange = true;

        // Add denominations to VM's Cash Drawer
        for (Map.Entry m : denominations.entrySet()) {
            Denomination denomination = Denomination.valueOf(m.getKey().toString());
            CashDrawer cashDrawer = cashDrawerBeanI.findByDenomination(denomination);
            cashDrawer.setCount(cashDrawer.getCount() + Integer.parseInt(m.getValue() + ""));
            cashDrawerBeanI.edit(cashDrawer);
        }
        Sale sale = new Sale();
        sale.setDate(new Date());
        sale.setAmount(this.calculateRequiredAmount(product, quantity));
        sale.setProduct(product);
        sale.setQuantity(quantity);
        boolean status = saleBeanI.makeSale(sale);
        if (giveChange)
            status = status && (giveChange && !this.giveChange(amount.subtract(this.calculateRequiredAmount(product, quantity))).isEmpty());
        if (!status)
            this.refundCustomerMoney(denominations);
        return status;


    }

    //refund customer money if machine does not have enough money to give change
    private void refundCustomerMoney(Map<Denomination, Integer> map) {
        System.out.println(map);

    }

    private Map<Denomination, Integer> giveChange(BigDecimal amount) {
        Map<Denomination, Integer> m = moneyConverterBeanI.getDenominationsForMoney(amount);
        return moneyConverterBeanI.getMoneyValueFromDenominations(m).compareTo(amount) == 0 ? m : new HashMap<Denomination, Integer>();

    }

}
