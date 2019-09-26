package com.example.vm.bean;

import com.example.vm.model.Sale;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Harriet on 9/18/2019.
 */
@Local
public interface SaleBeanI extends BeanInterface<Sale> {
    boolean makeSale(Sale sale);
    List<Sale> saleList()throws SQLException;
}
