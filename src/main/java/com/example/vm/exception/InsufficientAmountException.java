package com.example.vm.exception;

/**
 * Created by Harriet on 9/18/2019.
 */
public class InsufficientAmountException extends Exception {
    public InsufficientAmountException(){
        super("CashDrawer provided is less than required amount");
    }
}
