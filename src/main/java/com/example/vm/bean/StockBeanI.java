package com.example.vm.bean;

import com.example.vm.model.Product;
import com.example.vm.model.Stock;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Harriet on 9/18/2019.
 */
@Local
public interface StockBeanI extends BeanInterface<Stock> {
    int getStockBalance(Product product);
    List<Stock> stockList()throws SQLException;
    Stock getStockForProduct(Product product) throws SQLException;
}
