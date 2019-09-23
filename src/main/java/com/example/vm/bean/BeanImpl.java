package com.example.vm.bean;

import com.example.vm.db.DBHandler;
import com.example.vm.model.CashDrawer;
import com.example.vm.model.Product;
import com.example.vm.model.Sale;
import com.example.vm.model.Stock;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Harriet on 9/18/2019.
 */
@Local
@Stateless
public class BeanImpl<T> implements BeanInterface<T> {
    @Inject
    private DBHandler dbHandler;
    @EJB
    private ProductBeanI productBeanI;

    @Override
    public T add(T t) {
        String sql = null;
        Statement statement = null;
        if (t instanceof CashDrawer) {
            CashDrawer cashDrawer = (CashDrawer) t;
            sql = "INSERT INTO cash_drawer_tbl (denomination,dn_count) VALUES('" + cashDrawer.getDenomination() + "','" + cashDrawer.getCount() + "');";

        } else if (t instanceof Product) {
            Product product = (Product) t;
            sql = "INSERT INTO product_tbl(name,unit_price) VALUES('" + product.getName() + "'," + product.getUnitPrice() + ");";

        } else if (t instanceof Sale) {
            Sale sale = (Sale) t;
            sql = "INSERT INTO sale_tbl(date,product,quantity,amount) VALUES('" + sale.getDate() + "'," + sale.getProduct().getId() + "," + sale.getQuantity() + "," + sale.getAmount() + "  );";

        } else if (t instanceof Stock) {
            Stock stock = (Stock) t;

            Product product=productBeanI.read(stock.getProduct());
            if(product!=null){

                sql = "INSERT INTO stock_tbl(product,quantity) VALUES(" + stock.getProduct().getId() + "," + stock.getQuantity() + ")";
            }

        }
        try {
            statement = dbHandler.getConnection().createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public T edit(T t) {
        Statement stmt = null;
        String sql = null;

        if (t instanceof CashDrawer) {
            CashDrawer cashDrawer = (CashDrawer) t;
            sql = "UPDATE  cash_drawer_tbl SET denomination='" + cashDrawer.getDenomination() + "',count=" + cashDrawer.getCount() + ");";

        } else if (t instanceof Product) {
            Product product = (Product) t;
            sql = "UPDATE  product_tbl SET name='" + product.getName() + "',unit_price=" + product.getUnitPrice() + ");";

        } else if (t instanceof Sale) {
            Sale sale = (Sale) t;
            sql = "UPDATE sale_tbl SET  date='" + sale.getDate() + "',product=" + sale.getProduct().getId() + ",quantity=" + sale.getQuantity() + ",amount=" + sale.getAmount() + "  );";

        } else if (t instanceof Stock) {
            Stock stock = (Stock) t;
            sql = "UPDATE  stock_tbl SET product=" + stock.getProduct().getId() + ",quantity=" + stock.getQuantity() + ")";

        }
        try {
            stmt = dbHandler.getConnection().createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public T read(T t) {
        String sql = null;

        if (t instanceof CashDrawer) {
            CashDrawer cashDrawer = (CashDrawer) t;
            sql = "SELECT * FROM cash_drawer_tbl WHERE id=" + cashDrawer.getId();


        } else if (t instanceof Product) {
            Product product = (Product) t;
            sql = "SELECT * FROM product_tbl WHERE id=" + product.getId();


        } else if (t instanceof Sale) {
            Sale sale = (Sale) t;
            sql = "SELECT * FROM sale_tbl WHERE id=" + sale.getId();
        } else if (t instanceof Stock) {
            Stock stock = (Stock) t;
            sql = "SELECT * FROM stock_tbl where id=" + stock.getId();
        }
        try {
            Statement statement = dbHandler.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return t;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public boolean delete(T t) {
        String sql = null;
        Statement stmt = null;
        if (t instanceof CashDrawer) {
            CashDrawer cashDrawer = (CashDrawer) t;
            sql = "DELETE FROM cash_drawer_tbl WHERE id=" + cashDrawer.getId();

        } else if (t instanceof Product) {
            Product product = (Product) t;
            sql = "DELETE  FROM product_tbl WHERE id=" + product.getId();

        } else if (t instanceof Sale) {
            Sale sale = (Sale) t;
            sql = "DELETE  FROM sale_tbl WHERE id=" + sale.getId();

        } else if (t instanceof Stock) {
            Stock stock = (Stock) t;
            sql = "DELETE  FROM stock_tbl where id=" + stock.getId();

        }
        try {
            stmt = dbHandler.getConnection().createStatement();
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    }
//
//    private T generateSqlByOperation(int operation, T t) {
//        switch (operation) {
//            case 1:
//
//
//                break;
//            case 2:
//
//                break;
//            case 3:
//
//                break;
//            case 4:
//
//                break;
//        }
//
//    }



