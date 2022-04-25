package com.mendel.mendelchallenge.exception;

/** Exception to be thrown when an transaction not found in database. */
public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
