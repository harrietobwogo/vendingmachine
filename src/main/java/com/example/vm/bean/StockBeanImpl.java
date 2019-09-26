package com.example.vm.bean;

import com.example.vm.model.Product;
import com.example.vm.model.Stock;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harriet on 9/18/2019.
 */
@Local
@Stateless
public class StockBeanImpl extends BeanImpl<Stock> implements StockBeanI {
    @Inject
    private Connection connection;
    @EJB
    private ProductBeanI productBeanI;

    @Override
    public int getStockBalance(Product product) {
        return 0;
    }

    @Override
    public List<Stock> stockList() throws SQLException {
        String sql = "SELECT * FROM stock_tbl";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Stock> stocks = new ArrayList<>();
        while (resultSet.next()) {
            Stock stock = new Stock();
            stock.setId(resultSet.getLong("id"));
            Product product = new Product();
            product.setId(resultSet.getInt("id"));
            stock.setProduct(productBeanI.read(product));
            stock.setQuantity(resultSet.getInt("quantity"));
            stocks.add(stock);


        }
        return stocks;
    }

    @Override
    public Stock getStockForProduct(Product product) throws SQLException {
        String sql = "SELECT * FROM stock_tbl where product=" + product.getId();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        Stock stock = null;
        while (rs.next()) {
            stock = new Stock();
            stock.setId(rs.getLong("id"));
            stock.setProduct(product);
            stock.setQuantity(rs.getInt("quantity"));


        }
        return stock;

    }
}
