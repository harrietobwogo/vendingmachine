package com.example.vm.bean;

import com.example.vm.model.Product;

import com.example.vm.model.Sale;


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
public class SaleBeanImpl extends BeanImpl<Sale> implements SaleBeanI {
    @Inject
    private Connection connection;
    @EJB
    private ProductBeanI productBeanI;

    @Override
    public boolean makeSale(Sale sale) {
        return false;
    }

    @Override
    public List<Sale> saleList() throws SQLException {
        String sql = "SELECT * FROM sale_tbl";
        ArrayList<Sale> sales = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            Sale sale = new Sale();
            sale.setId(rs.getLong("id"));
            sale.setDate(rs.getDate("sale_date"));
            Product product = new Product();
            product.setId(rs.getInt("id"));
            sale.setProduct(productBeanI.read(product));
            sale.setQuantity(rs.getInt("quantity"));
            sale.setAmount(rs.getBigDecimal("amount"));
            sales.add(sale);
        }
        return sales;

    }
}
