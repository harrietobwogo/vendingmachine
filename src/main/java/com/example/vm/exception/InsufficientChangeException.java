package com.example.vm.exception;

/**
 * Created by Harriet on 9/19/2019.
 */
public class InsufficientChangeException extends Exception {
    public InsufficientChangeException(){
        super("Insufficient Balance to refund");
    }
}
