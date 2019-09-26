package com.example.vm.bean;

import com.example.vm.db.DBHandler;
import com.example.vm.model.CashDrawer;
import com.example.vm.model.Denomination;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Harriet on 9/18/2019.
 */
@Stateless
@Local
public class CashDrawerBeanImpl extends BeanImpl<CashDrawer> implements CashDrawerBeanI {
    @Inject
   private Connection connection;
    @Override
    public CashDrawer findByDenomination(Denomination denomination) {
        String sql = "SELECT * FROM cash_drawer_tbl WHERE denomination='"+denomination+"'";
        CashDrawer cashDrawer = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                cashDrawer = new CashDrawer();
                cashDrawer.setId(rs.getLong("id"));
                cashDrawer.setDenomination(denomination);
                cashDrawer.setCount(rs.getInt("dn_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cashDrawer ;
    }
    }

