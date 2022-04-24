package com.mendel.mendelchallenge.exception;

/** Exception to be thrown when an transaction id already exist in database. */
public class TransactionAlreadyExistException extends RuntimeException{
    public TransactionAlreadyExistException(String message) {
        super(message);
    }
}
