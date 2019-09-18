package com.example.vm.bean;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Created by Harriet on 9/18/2019.
 */
@Local
@Stateless
public class BeanImpl<T> implements BeanInterface<T> {

    @Override
    public T add(T t) {
        return null;
    }

    @Override
    public T edit(T t) {
        return null;
    }

    @Override
    public T findById(long id) {
        return null;
    }

    @Override
    public boolean delete(T t) {
        return false;
    }
}
