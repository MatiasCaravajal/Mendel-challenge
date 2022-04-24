package com.mendel.mendelchallenge.exception;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
