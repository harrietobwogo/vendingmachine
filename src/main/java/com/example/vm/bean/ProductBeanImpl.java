package com.example.vm.bean;

import com.example.vm.db.DBHandler;
import com.example.vm.model.Product;

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
 * Created by Harriet on 9/25/2019.
 */
@Local
@Stateless
public class ProductBeanImpl extends BeanImpl<Product> implements ProductBeanI {
@Inject
private Connection connection;

    @Override
    public List<Product> getProductList() throws SQLException {
        String sql="SELECT * FROM product_tbl";
        ArrayList<Product> productList=new ArrayList<>();
        Statement statement=connection.createStatement();
        ResultSet rs=statement.executeQuery(sql);

        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setUnitPrice(rs.getBigDecimal("unit_price"));
            productList.add(product);
        }
        return productList;
    }
}
