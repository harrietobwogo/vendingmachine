package com.example.vm.exception;

/**
 * Created by Harriet on 9/18/2019.
 */
public class InsufficientProductQuantityException extends Exception {
    public InsufficientProductQuantityException(){
        super("Product sold out");
    }
}
