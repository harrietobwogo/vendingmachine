package com.example.vm.bean;

import javax.ejb.Local;

/**
 * Created by Harriet on 9/18/2019.
 */
@Local
public interface BeanInterface <T>{
    T add(T t);
    T edit(T t);
    T findById(long id);
    boolean delete(T t);
}
