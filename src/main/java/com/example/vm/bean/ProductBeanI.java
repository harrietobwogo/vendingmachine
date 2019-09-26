package com.example.vm.bean;

import com.example.vm.model.Product;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Harriet on 9/23/2019.
 */
@Local
public interface ProductBeanI extends BeanInterface<Product>{
     List<Product> getProductList() throws SQLException;
}
